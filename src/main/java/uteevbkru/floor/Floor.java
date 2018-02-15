package uteevbkru.floor;

import uteevbkru.elevator.ElevatorsServer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Что бы вызвать лифт необходимо ввести в консоль 1 или 0.
 * Значение орпеделяет направление желаемого движения
 * Вверх или вниз. Если вы нажали 1,
 * то лифт откроет двери тогда когда будет ехать вверх.
 */

public class Floor implements Runnable{
    /** Показывает прерваны ли потоки. */
    private static AtomicBoolean isIterable = new AtomicBoolean(false);
    /** Для считывания данных из консоли. */
    private Scanner scanner;

    private final String regex = ", ";

    private int clientFloor;
    //private static Socket socket;

    /**
     * Конструктор для создания клиента-этажа!
     * @param clientFloor - номер этажа-клиента!
     *
     * Если нет соединения с сервером, то поток должен завершит свою работу
     */
    public Floor(int clientFloor) {
        this.clientFloor = clientFloor;
        scanner = new Scanner(System.in);
        System.out.println(this.clientFloor + " clientFloor has been created!");
    }

    public void run() {
        Socket socket = setUpConnection();
        if (socket != null) {
            workWithScannerAndOutputStream(socket);
        }
    }

    private void workWithScannerAndOutputStream(Socket socket) {
        try (DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            while(!isIterable.get()) {
                if (scanner.hasNext()) {
                    String str = scanner.nextLine();
                    int direction = checkInput(str);
                    if (direction != -1) {
                        boolean dir = findDirection(direction);
                        System.out.println(packMsg(dir));
                        outputStream.writeUTF(packMsg(dir));
                        outputStream.flush();
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

    /**
     * Декодирует String в int.
     * @param str - введенный String
     * @return - число int
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
     * Определяет вверх или вниз хочет поехать пользователь.
     * В консоль вводиться 0 или 1
     */
    protected boolean findDirection(int floor){
        return floor >= 1;
    }

    /**
     * Устанавливает соединение с серверным сокетом.
     * @return true - установлено, false - не установлено
     */
    private Socket setUpConnection(){
        try {
            return new Socket("localhost", ElevatorsServer.PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

