package uteevbkru.elevator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MonoClientHandler implements Runnable {

    private static Socket clientDialog;

    public MonoClientHandler(Socket client) {
        MonoClientHandler.clientDialog = client;
    }

    @Override
    public void run() {
        try {
            //DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            while (!clientDialog.isClosed()) {
                //TODO побороть EOFException!!


                String entry = in.readUTF();
                System.out.println("READ from client - " + entry);

                if (entry.equalsIgnoreCase("quit")) {
                    System.out.println("Client initialize connections suicide ...");
                    //out.writeUTF("Server reply - " + entry + " - OK");
                    Thread.sleep(3000);
                    break;
                }
                //out.writeUTF("Server reply - " + entry + " - OK");
                //System.out.println("Server Wrote message to clientDialog.");
                //out.flush();
            }
            in.close();
            //out.close();
            clientDialog.close();
            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}