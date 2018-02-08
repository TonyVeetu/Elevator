package uteevbkru.floor;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Floor implements Runnable{
    /** Показывает прерваны ли потоки. */
    private static AtomicBoolean isIterable = new AtomicBoolean(false);
    /** Для считывания данных с консоли. */
    private Scanner scanner;

    private final String regex = ", ";
    //TODO передавать эти переменные!
    private static int MIN_FLOOR = 0;
    private static int MAX_FLOOR = 5;

    private static AtomicInteger COUNTER = new AtomicInteger();
    private int currentFloor;
    private static Socket socket;

    public Floor(int floor) {
        if (floor > MIN_FLOOR && floor <= MAX_FLOOR) {
            currentFloor = floor;
//        if (COUNTER.get() < MAX_FLOOR) {
//            System.out.println(COUNTER  +  " after");
//            currentFloor = COUNTER.getAndAdd(1);
//            System.out.println(COUNTER.get()  +  " before");
//            System.out.println(currentFloor);
            if (setUpConnection()) {
                scanner = new Scanner(System.in);
            }
        }
        else {
            System.out.println("The threshold has been exceeded by the number of clients!");
        }
        System.out.println(currentFloor + " floor was been created!");
    }

    public void run() {
        try ( DataOutputStream oos = new DataOutputStream(socket.getOutputStream())) {
            while (!(isIterable.get())) {
                if (scanner != null) {
                    if (scanner.hasNext()) {
                        String str = scanner.nextLine();
                        if (!checkForStop(str)) {
                            oos.writeUTF("stop");
                            oos.flush();
                            break;
                        }
                        if (checkFloor(str)) {
                            oos.writeUTF(packMsg(str));
                            //TODO разобраться!
                            oos.flush();// проталкиваем сообщение из буфера сетевых сообщений в канал
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String packMsg(String floor){
        return currentFloor + regex + floor;
    }

    protected boolean checkForStop(final String iStr) {
        if (iStr.equals("Stop") || iStr.equals("stop")
                || iStr.equals("стоп") || iStr.equals("Стоп")) {
            isIterable.set(true);
            return false;
        }
        return true;
    }

    /** Превращает строковое значение этажа в число. */
    protected boolean checkFloor(final String str) {
        try {
            int floor = Integer.decode(str);
            if (floor >= MIN_FLOOR && floor <= MAX_FLOOR) {
                return true;
            } else {
                System.out.println("Uncorrected floor");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Uncorrected floor");
        }
        return false;
    }

    private boolean setUpConnection(){
        try {
            socket = new Socket("localhost", 4444);
            return true;
        } catch (Exception e) {
            System.out.println("Connection refused!");
            e.printStackTrace();
        }
        return false;
    }
}

