package uteevbkru;

import uteevbkru.elevator.Elevator;
import uteevbkru.porch.Porch;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс Лифт без подземных этажей.
 * @author Uteev Anton
 * @version 1.0.1
 */

public class ElevatorOverTheGround extends Elevator implements Runnable {
    /** Текущий этаж*/
    private int currentFloor = 0;
    /** Очередь этажей */
    private BlockingQueue<Integer> queueOfFloors;
    /** Показывает прерваны ли потоки */
    private AtomicBoolean isIterable;
    /** Количество миллисекуд в секунде */
    private static final int MS = 1000;
    /** Время для одного преодаления одного этажа */
    private long timeForOneFloor;
    /** Подъезд */
    private Porch porch;
    
    /** Конструктор */
    public ElevatorOverTheGround(Porch porch, final double speed, final int gapOpenClose, final BlockingQueue<Integer> queueOfFloors, final AtomicBoolean isIterable) {
        super(speed, gapOpenClose);
        this.porch = porch;
        this.queueOfFloors = queueOfFloors;
        this.isIterable = isIterable;
        findTimeForOneFloor();
    }

    /** @return промежуток между открытием и закрытием дверей */
    public int getGapOpenClose() {return super.getGapOpenClose(); }

    /** @return текущий этаж */
    public int getCurrentFloor(){return  currentFloor; }

    /** @return - следующий этаж */
    protected int getNextFloor() {
        try {
            return queueOfFloors.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** @return количество этажей */
    public int getCountOfFloors(final int nextFloor) {
        return Math.abs(nextFloor - currentFloor);
    }


    /** @retun время необходимое для проезда одного этажа */
    public long getTimeForOneFloor() {
        return timeForOneFloor;
    }

    /** Главная функция этого класса */
    public void run() {
        while (!isIterable.get()) {
            int nextFloor = getNextFloor();
            boolean up = isUp(nextFloor);
            int floors = getCountOfFloors(nextFloor);
            if (floors != 0) {
                moving(floors, up);
                openCloseDoors(nextFloor);
            }
        }
    }

    /**
     * Эмулирует открытие и закрытие дверей.
     * Печатает на каком этаже открылись и закрылись двери.
     */
    protected void openCloseDoors(final int floor) {
        try {
            System.out.println("Open doors on " + floor + " floor!");
            Thread.sleep(getGapOpenClose() * MS);
            System.out.println("Close doors on " + floor + " floor");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** @return направление движения лифта
     *      <code>true</code> если движение вверх
     *      <code>false</code> если движение вниз
     *      */
    protected boolean isUp(final int nextFloor) {
        boolean up;
        if (nextFloor > currentFloor) {
            up = true;
        } else {
            up = false;
        }
        return up;
    }

    /** Печатает текущий этаж */
    private void printCurrentFloor(){
        System.out.println("Current floor: " + currentFloor);
    }

    /** Расчитывает время необходимое для проезда одного этажа */
    public void findTimeForOneFloor() {
        timeForOneFloor = Math.round(porch.getFloorHeight() / getSpeed()) * MS;
    }

    /** Эмитация движения кабины лифта */
    private void moving(final int countOfFloors, final boolean up) {
        for (int i = 0; i < countOfFloors; i++) {
            try {
                Thread.sleep(timeForOneFloor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printCurrentFloor();
            changeCurrentFloor(up);
        }
    }

    /** Меняет текущий этаж */
    private void changeCurrentFloor( final boolean up){
        if (up) {
            ++currentFloor;
        } else {
            --currentFloor;
        }
    }

}
