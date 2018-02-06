package uteevbkru.elevator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorMultiServer {

    private static ExecutorService executeIt = Executors.newFixedThreadPool(2);

    public void startServer() {
        try (ServerSocket server = new ServerSocket(4444);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
        {
            while (!server.isClosed()) {
                if (br.ready()) {
                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")) {
                        System.out.println("Main Server initiate exiting...");
                        server.close();
                        break;
                    }
                }
                Socket client = server.accept();
                executeIt.execute(new MonoClientHandler(client));
                System.out.println("Connection accepted.");
            }
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
