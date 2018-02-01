package uteevbkru;

import uteevbkru.porch.Porch;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/** Main класс. */
public class Main {

    /** Главная функция проекта.
     * @param args  1 = количество этажей
     *              2 = скорость движения лифта в м/с
     *              3 = высота этажа
     *              4 = время между открфтием и закрфтием дверей лифта
     */
    public static void main(final String[] args) {
        //        int countOfFloors = Integer.decode(args[0]);
        //        int speed = Integer.decode(args[1]);
        //        int floorHeight = Integer.decode(args[2]);
        //        int gapOpenClose = Integer.decode(args[3]);

        int countOfFloors = 10;
        double speed = 2.3;
        double floorHeight = 2.0;
        int gapOpenClose = 2;

        /**
         Не может очередь быть больше количества этажей в подьезде!
         */
        int capacityOfQueue = countOfFloors;
        BlockingQueue<Integer> queueOfFloors = new ArrayBlockingQueue<Integer>(capacityOfQueue);
        AtomicBoolean isIterable = new AtomicBoolean(false);

        Porch porch;
        ElevatorOverTheGround elevatorOver;
        try {
            porch = new Porch(countOfFloors, floorHeight);
            elevatorOver = new ElevatorOverTheGround(porch, speed, gapOpenClose, queueOfFloors, isIterable);
        } catch (IOException e) {
            return;
        }

        Thread elevator = new Thread(elevatorOver);
        elevator.start();
        Controller controller = new Controller(elevatorOver, porch, queueOfFloors, isIterable);
        controller.start();
    }
}
