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
    /** Время для одного преодаления одного этажа*/
    private long timeForOneFloor;

    public Elevator(final int countOfFloors, final double speed, final double floorHeight, final int gapOpenClose, final BlockingQueue<Integer> queueOfFloors, final AtomicBoolean isIterable) {
        if( !(countOfFloors <= 0 || speed <= 0 || floorHeight <= 0 || gapOpenClose <= 0) ) {
            this.countOfFloors = countOfFloors;
            this.speed = speed;
            this.floorHeight = floorHeight;
            this.gapOpenClose = gapOpenClose;
            this.queueOfFloors = queueOfFloors;
            this.isIterable = isIterable;
            getTimeForOneFloor();
        }
        else {
            System.out.println("Uncorrected input data!");//TODO tests
        }
    }

    public void run() {
        while (!isIterable.get()) {
            printCurrentFloor();
            int nextFloor = nextFloor();
            boolean up = isUp(nextFloor);
            int floors = getCountOfFloors(nextFloor);
            if (floors != 0) {
                moving(floors, up);
                doorOpenClose(nextFloor);
            }
        }
    }

    /**
     * Эмулирует открытие и закрытие дверей.
     * Выводит в терминал на каком этаже открылись и закрылись двери
     */
    private void doorOpenClose(final int floor) {
        try {
            System.out.println("Open doors on " + floor + " floor!");
            Thread.sleep(gapOpenClose * MS);
            System.out.println("Close doors on " + floor + " floor");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return - этаж куда необходимо поехать.
     */
    private int nextFloor() {
        //TODO make func shot
        int floor = 0;
        try {
            floor = queueOfFloors.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return floor;
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

    /** Выводит текущий этаж */
    private void printCurrentFloor(){
        System.out.println("Current floor: " + currentFloor);
    }

    /** @return количество этажей */
    public int getCountOfFloors(final int nextFloor) {
        return Math.abs(nextFloor - currentFloor);
    }

    /** Расчитывает время необходимое для проезда одного этажа */
    public void getTimeForOneFloor() {
        timeForOneFloor=  Math.round(floorHeight / speed) * MS;
    }

    /**
     * Эмитация движения кабины лифта
     */
    private void moving(final int countOfFloor, final boolean up) {
        for (int i = 0; i < countOfFloor; i++) {
            try {
                Thread.sleep(timeForOneFloor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            whereIsElevator();
            changeCurrentFloor(up);
        }
    }

    private void whereIsElevator( ) {
        System.out.println("\t" + "Elevator is near with "
                + currentFloor + " floor");

    }

    private void changeCurrentFloor( final boolean up){
        if (up) {
            ++currentFloor;
        } else {
            --currentFloor;
        }
    }
}