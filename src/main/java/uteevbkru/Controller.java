package uteevbkru;

import uteevbkru.porch.Porch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс обрабатывает введенное число пользователем.
 *
 * @author Uteev Anton
 *
 * @version 1.0.1
 *
 */
public class Controller extends Thread {
    /** Показывает прерваны ли потоки. */
    private AtomicBoolean isIterable;
    /** Для считывания данных с консоли. */
    private Scanner scanner;
    /** Очередь этажей. */
    private BlockingQueue<Integer> queue;
    /** Лифт. */
    private ElevatorOverTheGround elevatorOver;
    /** Файл для чтения Scanner'ом в тестах. */
    private File file = new File("./test.txt");
    /** Переменная характеризующая чтение из файла. */
    private boolean isFile;
    /** Подъезд. */
    private Porch porch;

    /** Конструктор для считывания из System.in. */
    public Controller(final ElevatorOverTheGround elevatorOver, Porch porch, final BlockingQueue<Integer> queue, final AtomicBoolean isIterable) {
        this.porch = porch;
        this.elevatorOver = elevatorOver;
        this.queue = queue;
        this.isIterable = isIterable;
        scanner = new Scanner(System.in);
    }

    /** Конструктор для считывания из файла. */
    public Controller(final ElevatorOverTheGround elevatorOver, Porch porch, final BlockingQueue<Integer> queue, final AtomicBoolean isIterable, final boolean isFile) {
        this.porch = porch;
        this.elevatorOver = elevatorOver;
        this.queue = queue;
        this.isIterable = isIterable;
        this.isFile = isFile;
    }

    /** Главная функция этого класса. */
    public void run() {
        Integer currentFloor = 0;
        while (!isIterable.get()) {
            if (scanner.hasNext()) {
                String str = scanner.nextLine();
                if (isFile) {
                    System.out.println(str);
                }
                if (!checkForStop(str, currentFloor)) {
                    break;
                }
                currentFloor = getFloor(str);
                injectFloor(currentFloor);
            }
        }
        scanner.close();
    }

    /** Инициализирует Scanner для чтения из файла. */
    protected void initReadFromFile() {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** Возвращает состояние переменной isIterable. */
    protected boolean getIsIterable() {
        return isIterable.get();
    }

    /** Превращает строковое значение этажа в число. */
    protected Integer getFloor(final String str) {
        try {
            return Integer.decode(str);
        } catch (NumberFormatException e) { }
        return 0;
    }

    /** Вставляет этаж в очередь.
     * @return <code>true</code> значение вставилось
     *         <code>false</code> значение не вставилось
     */

    protected boolean injectFloor(final Integer floor) {
        if ((floor >= porch.getMinFloor()) && (floor <= porch.getMaxFloor())) {
            try {
                queue.put(floor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            System.out.println("Inputted floor is bigger than maxFloor!");
        }
        return false;
    }

    /** Останавливает потоки при вводе строки "Stop".
     * @param iStr входная строка
     * @param iFloor этаж
     * @return <code>true</code> если не нужно остановить потоки
     */
    protected boolean checkForStop(final String iStr, final int iFloor) {
        if (iStr.equals("Stop") || iStr.equals("checkForStop") || iStr.equals("стоп") || iStr.equals("Стоп")) {
            isIterable.set(true);
            injectFloor(iFloor); // Добавляем в очередь предыдущее значение,
            // что бы вытащий поток ElevatorOverTheGround из цикла while при введенном Stop!
            return false;
        }
        return true;
    }
}