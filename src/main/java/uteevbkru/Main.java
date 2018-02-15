package uteevbkru;

import uteevbkru.elevator.ElevatorsServer;
import uteevbkru.porch.Porch;

import java.io.IOException;

/** Main класс. */
public class Main {

    private static Porch porch;
    private static ElevatorOverTheGround elevatorOver;
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

        int countOfFloors = 5;
        double speed = 2.3;
        double floorHeight = 2.0;
        int gapOpenClose = 5;

        try {
            porch = new Porch(countOfFloors, floorHeight);
            elevatorOver = new ElevatorOverTheGround(porch, speed, gapOpenClose);
        } catch (IOException e) {
            return;
        }
        Thread elevator = new Thread(elevatorOver);
        elevator.start();
        Controller controller = new Controller(elevatorOver, porch);
        controller.start();
        ElevatorsServer server = new ElevatorsServer(countOfFloors, elevatorOver);
        server.startServer();
    }
}
