package uteevbkru;

import uteevbkru.elevator.ElevatorMultiServer;
import uteevbkru.floor.Floor;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainSocketServer {
    public static void main(String[] args) throws IOException, InterruptedException{
        ElevatorMultiServer elevatorMultiServer = new ElevatorMultiServer();
        elevatorMultiServer.startServer();

    }
}
