package uteevbkru.floor;

import uteevbkru.elevator.ElevatorsServer;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**Класс Этаж-клиент
 * @author Uteev Anton
 * @version 1.0.1
 *
 * Этот класс эмитируте вызов лифта на этаж.
 * Для вызова на этаж нужно ввести в консоль любое число в пределах [-Integer.MAX_VALUE+1, Integer.MAX_VALUE].
 */

public class FloorClient implements Runnable{
    /** Для считывания данных из консоли. */
    private Scanner scanner;
    /** Номер этажа-клиента*/
    private int clientFloor;

    /**Конструктор.
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
            if (direction != -Integer.MAX_VALUE) {
                outputStream.writeUTF(packMsg());
                outputStream.flush();
            }
        }
    }

    /** Формирует сообщение серверу. */
    private String packMsg(){
        return Integer.toString(clientFloor);
    }

    /**
     * Декодирует String в int.
     * @param str - введенный String
     * @return - число int
     */
    private int checkInput(final String str) {
        int number = -Integer.MAX_VALUE;
        try {
            number = Integer.decode(str);
        } catch (NumberFormatException e) {
            System.out.println("It isn't a number!");
        }
        return number;
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

