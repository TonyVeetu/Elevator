package uteevbkru.floor;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Floor implements Runnable{
    /** Показывает прерваны ли потоки. */
    private static AtomicBoolean isIterable = new AtomicBoolean(false);
    /** Для считывания данных с консоли. */
    private Scanner scanner;

    private final String regex = ", ";

    //TODO передавать эти переменные!
    //Нужны для проверки правильного создания клиента-этажа!

    private static int MIN_FLOOR = 0;
    private static int MAX_FLOOR = 5;
    private int clientFloor = -1;
    private static Socket socket;

    /**
     * Конструктор для создания клиента-этажа!
     * @param clientFloor - этаж который будет эмитировать клиент!
     */
    public Floor(int clientFloor) {
        if (clientFloor > MIN_FLOOR && clientFloor <= MAX_FLOOR) {
            this.clientFloor = clientFloor;
            if (setUpConnection()) {
                scanner = new Scanner(System.in);
            }
        }
        else {
            System.out.println("The threshold has been exceeded by the number of clients!");
        }
        System.out.println(this.clientFloor + " clientFloor was been created!");
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
                        } else {
                            int floor = checkInput(str);
                            if (floor != -1) {
                                boolean direction = findDirection(floor);
                                System.out.println(packMsg(direction));
                                oos.writeUTF(packMsg(direction));
                                oos.flush(); //TODO разобраться!
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String packMsg(boolean direction){
        return clientFloor + regex + direction;
    }

    protected boolean checkForStop(final String iStr) {
        if (iStr.equals("Stop") || iStr.equals("stop")
                || iStr.equals("стоп") || iStr.equals("Стоп")) {
            isIterable.set(true);
            return false;
        }
        return true;
    }

    /**
     * Определяет какое число было введено.
     * @param str - введенное число String
     * @return число int
     */
    protected int checkInput(final String str) {
        int number = -1;
        try {
            number = Integer.decode(str);
        } catch (NumberFormatException e) {
            System.out.println("It isn't a number!");
        }
        return number;
    }

    /**
     * Определяет вверх или вниз поедет пользователь.
     * В консоль вводиться 0 или 1
     */
    protected boolean findDirection(int floor){
        return floor >= 1 ? true : false;
    }

    /**
     * Устанавливает соединение с серверным сокетом.
     * @return true - установлено, false - не установлено
     */

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

