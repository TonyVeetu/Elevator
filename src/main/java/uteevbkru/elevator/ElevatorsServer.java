package uteevbkru.elevator;

import uteevbkru.ElevatorOverTheGround;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ElevatorsServer {

    AtomicBoolean isIterable;
    private static ExecutorService executeService;
    private ElevatorOverTheGround elevator;
    public static int PORT = 4444;

    public ElevatorsServer(int count, final ElevatorOverTheGround elevatorOver){
        executeService = Executors.newFixedThreadPool(count);
        elevator = elevatorOver;
        isIterable = elevatorOver.getIsIterable();
    }

    public void startServer() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (!isIterable.get()) {
                Socket client = server.accept();
                executeService.execute(new ClientsHandler(client, elevator));
                System.out.println("Connection accepted.");
            }
            executeService.shutdownNow();
            System.out.println("Server has been shutdown!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
