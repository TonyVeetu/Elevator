package uteevbkru.elevator;

import uteevbkru.ElevatorOverTheGround;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientsHandler implements Runnable {

    private Socket clientDialog;
    private int fromWho;
    /** Вверх или вниз хочет поехать пользователь. */
    private boolean direction;
    private final String regex = ", ";
    private ElevatorOverTheGround elevator;

    public ClientsHandler(Socket client, ElevatorOverTheGround elevatorOverTheGround) {
        this.clientDialog = client;
        elevator = elevatorOverTheGround;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            while (!clientDialog.isClosed()) {
                String entry = in.readUTF();
                unpackMsg(entry);
                System.out.println("READ from client " + fromWho + ": direction - " + direction);
                injectFloor();
            }
            in.close();
            clientDialog.close();
        } catch (EOFException e ){
            System.out.println("EOFException!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unpackMsg(String str){
        try {
            String prom[] = str.split(regex);
            String fromW = prom[0];
            String directionF = prom[1];
            fromWho = Integer.decode(fromW);
            direction = Boolean.parseBoolean(directionF);
        } catch (NumberFormatException e) {
            System.out.println("Error in unpackMsg()!");
        }
    }

    private void injectFloor() {
        try {
            elevator.putInQueueForClient(fromWho, direction);
        } catch (InterruptedException e) {
            System.out.println("Injection FloorClient for Client: " + fromWho + ", has been failed!");
        }
    }
}