package uteevbkru.elevator;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientsHandler implements Runnable {

    private static Socket clientDialog;
    private int fromWho;
    private int requiredFloor;
    private final String regex = ", ";
    private BlockingQueue<Integer> queueOfFloors;
    public ClientsHandler(Socket client, BlockingQueue<Integer> queue) {
        ClientsHandler.clientDialog = client;
        queueOfFloors = queue;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            while (!clientDialog.isClosed()) {
                String entry = in.readUTF();
                if (entry.equalsIgnoreCase("stop")) {
                    break;
                } else {
                    unpackMsg(entry);
                    System.out.println("READ from client " + fromWho + ": requiredFloor - " + requiredFloor);
                    injectFloors();
                }
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

    //TODO написать красиво!! Учитывая конечную точку лифта!
    private void injectFloors(){
        queueOfFloors.add(fromWho);
        queueOfFloors.add(requiredFloor);
    }

    //!!!!!!!!!!
    //TODO мне не нравиться что каждый этаж добавляет следующий этаж в очередь лифта!
    //!!!!!!!!!!
}