package uteevbkru.elevator;

import uteevbkru.ElevatorOverTheGround;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientsHandler implements Runnable {

    private static Socket clientDialog;
    private int fromWho;
    private boolean direction;
    private final String regex = ", ";
    private BlockingQueue<Integer> queueOfFloors;
    private ElevatorOverTheGround elevator;

    public ClientsHandler(Socket client, BlockingQueue<Integer> queue, ElevatorOverTheGround elevatorOverTheGround) {
        ClientsHandler.clientDialog = client;
        queueOfFloors = queue;
        elevator = elevatorOverTheGround;
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
                    System.out.println("READ from client " + fromWho + ": direction - " + direction);
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
            String directionF = prom[1];
            fromWho = Integer.decode(fromW);
            direction = Boolean.parseBoolean(directionF);
        } catch (NumberFormatException e) {
            System.out.println("Error in unpackMsg()!");
        }
    }

    //TODO вставлять этаж в очередь лифта нужно если текущее вставляемое значение по пути!
    // if(direction)//движение вверх
    //      if(current_floor < fromW)
    //            inject_floor();
    //else//движение вниз
    //      if(current_floor > fromW)
    //          inject_floor();

    private void injectFloors(){
        boolean direction = elevator.getUpTrip();
        int currentFloor = elevator.getCurrentFloor();
        if (direction) {
            if (currentFloor < fromWho) {
                queueOfFloors.add(fromWho);
            } else {
                //TODO записать в вспомогательную очередь, которую лифт просмотрить когда поменяет направление движения
            }
        } else {
            if (currentFloor > fromWho) {
                queueOfFloors.add(fromWho);
            } else {
                //TODO записать в вспомогательную очередь, которую лифт просмотрить когда поменяет направление движения
            }
        }
        queueOfFloors.add(fromWho);
        //queueOfFloors.add(direction);
    }

    //!!!!!!!!!!
    //TODO мне не нравиться что каждый этаж добавляет следующий этаж в очередь лифта!
    //!!!!!!!!!!
}