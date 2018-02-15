package uteevbkru.floor;

import uteevbkru.elevator.ElevatorsServer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


/**
 * Класс Этаж-клиент.
 *
 * @author Uteev Anton
 *
 * @version 1.0.1
 *
 * Этот класс эмитируте вызов лифта на этаж.
 * Пользователю предоставляется две кнопки вызова лифта на этаж.
 * Одна кнопка стрелка вверх другая кнопка стрелка вниз.
 * Что бы проимитировать нажатие кнопки вверх, нужно ввести в консоль число >= 1.
 * Ввод в консоль числа = 0, будет приравнен к нажатию кнопки вниз!
 * При нажатии на кнопку вверх лифт не должен остановиться на этом этаже, если он двигается вниз!
 * И наоборот!
 *
 ***В версии 1.0.1 реализация алгоритма обработки запросов лифта, желаемое направление не учитывает.
 ***Но в реализации этого класса заложен двухкнопочный функционал на случай развития алгоритма лифта!
 */

public class FloorClient implements Runnable{
    /** Для считывания данных из консоли. */
    private Scanner scanner;
    /** Символ для формирования сообщения к сереверу. */
    private final String regex = ", ";
    /** Номер этажа-клиента*/
    private int clientFloor;

    /**
     * Конструктор.
     *
     * @param clientFloor - номер этажа-клиента!
     */
    public FloorClient(int clientFloor) {
        this.clientFloor = clientFloor;
        scanner = new Scanner(System.in);
        System.out.println(this.clientFloor + " clientFloor has been created!");
    }

    /** Если нет соединения с сервером, то поток завершит свою работу. */
    @Override
    public void run() {
        Socket socket = setUpConnection();
        if (socket != null) {
            workWithOutputStream(socket);
        }
    }

    /** Создает поток вывода данных */
    private void workWithOutputStream(Socket socket) {
        try (DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            while (true) {
                workWithScanner(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Декодирует значение из консоли,
     * проверяет введенное число,
     * формирует сообщение и записывает его в OutputStream.
     * @param outputStream - поток вывода данных
     */
    private void workWithScanner(DataOutputStream outputStream) throws IOException{
        if (scanner.hasNext()) {
            String str = scanner.nextLine();
            int direction = checkInput(str);
            System.out.println(direction);
            if (direction != -Integer.MAX_VALUE) {
                boolean dir = findDirection(direction);
                System.out.println(packMsg(dir));
                outputStream.writeUTF(packMsg(dir));
                outputStream.flush();
            }
        }
    }

    /** Формирует сообщение серверу. */
    private String packMsg(boolean direction){
        return clientFloor + regex + direction;
    }

    /**
     * Декодирует String в int.
     * @param str - введенный String
     * @return - число int
     */
    protected int checkInput(final String str) {
        int number = -Integer.MAX_VALUE;
        try {
            number = Integer.decode(str);
        } catch (NumberFormatException e) {
            System.out.println("It isn't a number!");
        }
        return number;
    }

    /** Определяет вверх или вниз хочет поехать пользователь. */
    protected boolean findDirection(final int floor){
        return floor >= 1;
    }

    /** Устанавливает соединение с серверным сокетом. */
    private Socket setUpConnection(){
        try {
            return new Socket("localhost", ElevatorsServer.PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

