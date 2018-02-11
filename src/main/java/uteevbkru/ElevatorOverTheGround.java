package uteevbkru;

import uteevbkru.elevator.Elevator;
import uteevbkru.porch.Porch;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс Лифт без подземных этажей.
 *
 * @author Uteev Anton
 *
 * @version 1.0.1
 *
 */

public class ElevatorOverTheGround extends Elevator implements Runnable {
    /** Текущий этаж. */
    private AtomicInteger currentFloor = new AtomicInteger(0);
    /** Очередь этажей. */
    private BlockingQueue<Integer> queueOfFloors;
    /** Показывает прерваны ли потоки.*/
    private AtomicBoolean isIterable;
    /** Количество миллисекуд в секунде. */
    private static final int MS = 1000;
    /** Время для одного преодаления одного этажа. */
    private long timeForOneFloor;
    /** Подъезд. */
    private Porch porch;

    private boolean upTrip = false;
    
    /** Конструктор. */
    public ElevatorOverTheGround(Porch porch, final double speed, final int gapOpenClose, final BlockingQueue<Integer> queueOfFloors, final AtomicBoolean isIterable) throws IOException {
        super(speed, gapOpenClose);
        this.porch = porch;
        this.queueOfFloors = queueOfFloors;
        this.isIterable = isIterable;
        findTimeForOneFloor();
    }

    /** Расчитывает время необходимое для проезда одного этажа. */
    public void findTimeForOneFloor() {
        timeForOneFloor = Math.round(porch.getFloorHeight() / getSpeed()) * MS;
    }

    /** @return промежуток между открытием и закрытием дверей. */
    public final int getGapOpenClose() {
        return super.getGapOpenClose();
    }

    /** @return текущий этаж. */
    public int getCurrentFloor() {
        return  currentFloor.get();
    }

    public boolean getUpTrip(){ return upTrip; }

    /** Главная функция этого класса. */
    public void run() {
        while (!isIterable.get()) {
            int nextFloor = getNextFloor();
            setUpTrip(nextFloor);
            int floors = getCountOfFloors(nextFloor);
            if (floors != 0) {
                moving(floors);
                openCloseDoors(nextFloor);
            }
        }
    }

    /** @return - следующий этаж. */
    protected int getNextFloor() {
//---------
        System.out.print("getNextFloor: "+"\t"); Object[] array = queueOfFloors.toArray();
        for(int i = 0; i < array.length; i++) { System.out.print(array[i]+"\t");}
//-----------
        try {
            return queueOfFloors.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** @return направление движения лифта.
     *      <code>true</code> если движение вверх
     *      <code>false</code> если движение вниз
     *      */
    protected void setUpTrip(final int nextFloor) {
        if (nextFloor > currentFloor.get()) {
            upTrip = true;
        } else {
            upTrip = false;
        }
    }

    /** @return количество этажей. */
    public int getCountOfFloors(final int nextFloor) {
        return Math.abs(nextFloor - currentFloor.get());
    }

    /** Эмитация движения кабины лифта. */
    private void moving(final int countOfFloors) {
        for (int i = 0; i < countOfFloors; i++) {
            try {
                Thread.sleep(timeForOneFloor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printCurrentFloor();
            changeCurrentFloor();
        }
    }

    /** @retun время необходимое для проезда одного этажа. */
    public long getTimeForOneFloor() {
        return timeForOneFloor;
    }

    /**
     * Эмулирует открытие и закрытие дверей.
     * Печатает на каком этаже открылись и закрылись двери
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

    /** Печатает текущий этаж. */
    private void printCurrentFloor() {
        System.out.println("Current floor: " + currentFloor);
    }

    /** Меняет текущий этаж. */
    private void changeCurrentFloor() {
        if (upTrip) {
            currentFloor.incrementAndGet();
        } else {
            currentFloor.decrementAndGet();
        }
    }

}
