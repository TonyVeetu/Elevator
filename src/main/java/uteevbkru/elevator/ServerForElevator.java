package uteevbkru.elevator;

import uteevbkru.ElevatorOverTheGround;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerForElevator {

    private static ExecutorService executeService;
    private ElevatorOverTheGround elevator;
    public ServerForElevator(int count, final ElevatorOverTheGround elevatorOver){
        executeService = Executors.newFixedThreadPool(count);
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
                executeService.execute(new ClientsHandler(client, elevator));
                System.out.println("Connection accepted.");
            }
            executeService.shutdown();
            System.out.println("Server has been shutdown!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
