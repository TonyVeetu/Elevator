package uteevbkru.elevator;

import uteevbkru.ElevatorOverTheGround;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**Базовый класс - Сервер для Лифта.
 * @author Uteev Anton
 * @version 1.0.1
 */

public class ElevatorsServer {
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
    }

    /** Создание сокета и запуск в новом потоке обработчика клиента-этажа. */
    public void startUp() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Socket client = server.accept();
                executeService.execute(new ClientsHandler(client, elevator));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Закрытие всех потоков. */
    public void stop() {
        executeService.shutdownNow();
    }
}
