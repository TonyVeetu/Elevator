package uteevbkru.elevator;

import uteevbkru.ElevatorOverTheGround;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Базовый класс - Сервер для Лифта.
 *
 * @author Uteev Anton
 *
 * @version 1.0.1
 */

public class ElevatorsServer {
    /** Прерывать ли поток. */
    AtomicBoolean isIterable;
    /** Работа с потоками. */
    private static ExecutorService executeService;
    /** Лифт. */
    private ElevatorOverTheGround elevator;
    /** Порт. */
    public static int PORT = 4444;

    /** Конструктор.
     * @param count количество этажей клиентов
     * @param elevatorOver лифт
     */
    public ElevatorsServer(int count, final ElevatorOverTheGround elevatorOver){
        executeService = Executors.newFixedThreadPool(count);
        elevator = elevatorOver;
        isIterable = elevatorOver.getIsIterable();
    }

    /** Стартует сервер. */
    public void startServer() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (!isIterable.get()) {
                Socket client = server.accept();
                executeService.execute(new ClientsHandler(client, elevator));
                System.out.println("Connection accepted.");
            }
            executeService.shutdownNow();
            System.out.println("Server has been shutdown!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
