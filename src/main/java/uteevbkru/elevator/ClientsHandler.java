package uteevbkru.elevator;

import uteevbkru.ElevatorOverTheGround;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

/**Базовый класс - Обработчик клиентов.
 * @author Uteev Anton
 * @version 1.0.1
 */

public class ClientsHandler implements Runnable {
    /** Сокет для соединения с сервером. */
    private Socket clientDialog;
    /** На какой этаж вызывают лифт. */
    private int fromWho;
    /** Лифт. */
    private ElevatorOverTheGround elevator;

    /** Конструктор.
     * @param client сокет для соединения с лифтом
     * @param elevatorOverTheGround лифт
     */
    public ClientsHandler(Socket client, ElevatorOverTheGround elevatorOverTheGround) {
        this.clientDialog = client;
        elevator = elevatorOverTheGround;
    }

    /** Создание входного потока,
     * чтение сообщения,
     * распаковка сообщения,
     * передача параметров в лифт.
     */
    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            while (!clientDialog.isClosed()) {
                String entry = in.readUTF();
                unpackMsg(entry);
                putIntoTheElevator();
            }
            in.close();
            clientDialog.close();
        } catch (EOFException e ){
            System.out.println("Сlient has closed the connection!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Распаковка сообщения.
     * @param str - считанный с входного потока данных String
     */
    private void unpackMsg(String str){
        try {
            fromWho = Integer.decode(str);
        } catch (NumberFormatException e) {
            System.out.println("Error in function unpackMsg()!");
        }
    }

    /** Передает в Лифт параметры (от кого и направление движения). */
    private void putIntoTheElevator() {
        try {
            elevator.putInQueueForClient(fromWho);
        } catch (InterruptedException e) {
            System.out.println("Injection FloorClient for Client: " + fromWho + ", has been failed!");
        }
    }
}