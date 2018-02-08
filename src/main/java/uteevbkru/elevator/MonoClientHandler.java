package uteevbkru.elevator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class MonoClientHandler implements Runnable {

    private static Socket clientDialog;

    private int fromWho;
    private int requiredFloor;
    private final String regex = ", ";
    public MonoClientHandler(Socket client) {
        MonoClientHandler.clientDialog = client;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            while (!clientDialog.isClosed()) {
                String entry = in.readUTF();

                unpackMsg(entry);
                System.out.println("READ from client " + fromWho + ": requiredFloor - " + requiredFloor);
                //TODO
                //вставить сообщение в очередь лифта!!


                // TODO Сейчас quit не пошлеться!!
//                if (entry.equalsIgnoreCase("quit")) {
//                    System.out.println("Client initialize connections suicide ...");
//                    break;
//                } else {
//
//                }

            }
            in.close();
            clientDialog.close();
            System.out.println("Closing connections & channels - DONE.");
        } catch (EOFException e ){
            System.out.println("EOFException!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unpackMsg(String str){
        try {
            String prom[] = str.split(regex);
            String fromW = prom[0];
            String requiredF = prom[1];
            fromWho = Integer.decode(fromW);
            requiredFloor = Integer.decode(requiredF);
        } catch (NumberFormatException e) {
            System.out.println("Error!");
        }
    }
}