package uteevbkru.elevator;

import uteevbkru.ElevatorOverTheGround;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerForElevator {

    private static ExecutorService executeIt;
    private BlockingQueue<Integer> queueOfFloors;
    private ElevatorOverTheGround elevator;
    public ServerForElevator(int count, final BlockingQueue<Integer> queue, final ElevatorOverTheGround elevatorOver){
        queueOfFloors = queue;
        executeIt = Executors.newFixedThreadPool(count);
        elevator = elevatorOver;
    }

    public void startServer() {
        try (ServerSocket server = new ServerSocket(4444)) {
             /*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));*/
             /*Scanner scanner = new Scanner(System.in))*/
            while (!server.isClosed()) {
                //TODO разобраться с BufferedReader!! как с его помощью считать данные!
                //if (br.ready()) {
                //if (scanner.hasNext()){
                    //String serverCommand = br.readLine();
                    //String serverCommand = scanner.nextLine();
                    //System.out.println("ServerCommand " + serverCommand);
                    //if (serverCommand.equalsIgnoreCase("stop")) {
                    //    System.out.println("Main Server initiate exiting...");
                    //    server.close();
                    //    break;
                    //}
                //}
                Socket client = server.accept();
                executeIt.execute(new ClientsHandler(client, queueOfFloors, elevator));
                System.out.println("Connection accepted.");
            }
            executeIt.shutdown();
            System.out.println("Server has been shutdown!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
