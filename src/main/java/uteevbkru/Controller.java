package uteevbkru;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс обрабатывает введенное число пользователем.
 * @author Uteev Anton
 * @version 1.0.1
 */
public class Controller extends Thread {
    /** Показывает прерваны ли потоки */
    private AtomicBoolean isIterable;
    /** Для считывания данных с консоли */
    private Scanner scanner;
    /** Очередь этажей */
    private BlockingQueue<Integer> queue;
    /** Лифт */
    private ElevatorOverTheGround elevator;

    /** Конструктор */
    public Controller(final ElevatorOverTheGround elevator , final BlockingQueue<Integer> queue, final AtomicBoolean isIterable) {
        this.elevator = elevator;
        this.queue = queue;
        this.isIterable = isIterable;
        scanner = new Scanner(System.in);
    }

    /** Главная функция этого класса */
    public void run() {
        Integer currentFloor = 0;
        while (!isIterable.get()) {
            if (scanner.hasNext()) {
                String str = scanner.nextLine();
                if (!check(str, currentFloor)) {
                    break;
                }
                currentFloor = getFloor(str);
                injectFloor(currentFloor);
            }
        }
        scanner.close();
    }

    /** Превращает строковое значение этажа в число */
    protected Integer getFloor(final String str) {
        try {
            return Integer.decode(str);
        } catch (NumberFormatException e) { }
        return 0;
    }

    /** Вставляет этаж в очередь */
    protected void injectFloor(final Integer floor) {
        if ((floor >= elevator.getMinFloor()) && (floor <= elevator.getMaxFloor())) {
            try {
                queue.put(floor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Input unreal floor!");
        }
    }

    /** Останавливает потоки при вводе строки "Stop" */
    private boolean check(final String str, final int floor) {
        if (str.equals("Stop") || str.equals("stop") || str.equals("стоп") || str.equals("Стоп")) {
            isIterable.set(true);
            injectFloor(floor); // Добавляем в очередь предыдущее значение,
            // что бы вытащий поток ElevatorOverTheGround из цикла while при введенном Stop!
            return false;
        }
        return true;
    }
}