package uteevbkru;

import uteevbkru.elevator.ElevatorsServer;
import uteevbkru.porch.Porch;

import java.io.IOException;

/** Main класс. */
public class Main {

    /** Главная функция проекта.
     * @param args  1 = количество этажей
     *              2 = скорость движения лифта в м/с
     *              3 = высота этажа
     *              4 = время между открфтием и закрфтием дверей лифта
     */
    public static void main(final String[] args) {
        int countOfFloors, speed, floorHeight, gapOpenClose;
        try {
            countOfFloors = Integer.decode(args[0]);
            speed = Integer.decode(args[1]);
            floorHeight = Integer.decode(args[2]);
            gapOpenClose = Integer.decode(args[3]);
        } catch (NumberFormatException e){
            System.out.println("Input data isn't Integer.");
            return;
        }
        if (countOfFloors > 20 || countOfFloors < 5) {
            throw new IllegalArgumentException("Incorrect count of Floors!");
        }

        Porch porch = new Porch(countOfFloors, floorHeight);
        ElevatorOverTheGround elevatorOver = new ElevatorOverTheGround(porch, speed, gapOpenClose);
        Thread elevator = new Thread(elevatorOver);
        elevator.start();
        Controller controller = new Controller(elevatorOver, porch);
        controller.start();
        ElevatorsServer server = new ElevatorsServer(countOfFloors, elevatorOver);
        server.startServer();

        /** Программа закончит свою работу
         *  когда каждый установивший соединение клиент закончит свою работу
         */
    }
}
