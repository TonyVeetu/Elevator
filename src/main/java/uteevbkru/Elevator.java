package uteevbkru;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс Лифт без подземных этажей.
 * @author Uteev Anton
 * @version 1.0.1
 */
public class Elevator extends Thread {
    /** Количество этажей */
    private int countOfFloors;
    /** Скорость */
    private double speed;
    /** Высота этажа */
    private double floorHeight;
    /** Время на откытие и закрытие дверей на этаже */
    private int gapOpenClose;
    /** Текущий этаж*/
    private int currentFloor = 0;
    /** Очередь этажей */
    private BlockingQueue<Integer> queueOfFloors;
    /** Прерван ли поток*/
    private AtomicBoolean isIterable;
    /** Количество миллисекуд в секунде */
    private static final int MS = 1000;
    /** Время для одного преодаления одного этажа */
    private long timeForOneFloor;

    public Elevator(final int countOfFloors, final double speed, final double floorHeight, final int gapOpenClose, final BlockingQueue<Integer> queueOfFloors, final AtomicBoolean isIterable) {
        if( !(countOfFloors <= 0 || speed <= 0 || floorHeight <= 0 || gapOpenClose <= 0) ) {
            this.countOfFloors = countOfFloors;
            this.speed = speed;
            this.floorHeight = floorHeight;
            this.gapOpenClose = gapOpenClose;
            this.queueOfFloors = queueOfFloors;
            this.isIterable = isIterable;
            findTimeForOneFloor();
        }
        else {
            System.out.println("Uncorrected input data!");//TODO tests
        }
    }

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
            Thread.sleep(gapOpenClose * MS);
            System.out.println("Close doors on " + floor + " floor");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** @return - следующий этаж */
    protected int getNextFloor() {
        try {
            return queueOfFloors.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** @return направление движения лифта */
    private boolean isUp(final int nextFloor) {
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

    /** @return количество этажей */
    public int getCountOfFloors(final int nextFloor) {
        return Math.abs(nextFloor - currentFloor);
    }

    /** Расчитывает время необходимое для проезда одного этажа */
    public void findTimeForOneFloor() {
        timeForOneFloor = Math.round(floorHeight / speed) * MS;
    }

    /** @retun время необходимое для проезда одного этажа */
    public long getTimeForOneFloor() {
        return timeForOneFloor;
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
