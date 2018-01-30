package uteevbkru;

import java.io.File;
import java.io.FileNotFoundException;
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
    /** Файл для чтения  Scanner'ом в тестах*/
    File file = new File("./test.txt");

    private boolean isFile;

    /** Конструктор для считывания из System.in*/
    public Controller(final ElevatorOverTheGround elevator , final BlockingQueue<Integer> queue, final AtomicBoolean isIterable) {
        this.elevator = elevator;
        this.queue = queue;
        this.isIterable = isIterable;
        scanner = new Scanner(System.in);
    }

    public Controller(final ElevatorOverTheGround elevator , final BlockingQueue<Integer> queue, final AtomicBoolean isIterable, final boolean isFile) {
        this.elevator = elevator;
        this.queue = queue;
        this.isIterable = isIterable;
        this.isFile = isFile;
    }

    /** Главная функция этого класса */
    public void run() {
        Integer currentFloor = 0;
        while (!isIterable.get()) {
            if (scanner.hasNext()) {
                String str = scanner.nextLine();
                if(isFile)
                    System.out.println(str);
                if (!check(str, currentFloor)) {
                    break;
                }
                currentFloor = getFloor(str);
                injectFloor(currentFloor);
            }
        }
        scanner.close();
    }

    protected void initReadFromFile(){
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /** Возвращает состояние переменной isIterable */
    protected boolean getIsIterable(){
        return isIterable.get();
    }

    /** Превращает строковое значение этажа в число */
    protected Integer getFloor(final String str) {
        try {
            return Integer.decode(str);
        } catch (NumberFormatException e) { }
        return 0;
    }

    /** Вставляет этаж в очередь
     * @return <code>true</code> значение вставилось
     *         <code>false</code> значение не вставилось
     */

    protected boolean injectFloor(final Integer floor) {
        if ((floor >= elevator.getMinFloor()) && (floor <= elevator.getMaxFloor())) {
            try {
                queue.put(floor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            } catch (ClassCastException e){
                e.printStackTrace();
            }
            return true;
        } else {
            System.out.println("Inputted floor is bigger than maxFloor!");
        }
        return false;
    }

    /** Останавливает потоки при вводе строки "Stop" */
    protected boolean check(final String str, final int floor) {
        if (str.equals("Stop") || str.equals("stop") || str.equals("стоп") || str.equals("Стоп")) {
            isIterable.set(true);
            injectFloor(floor); // Добавляем в очередь предыдущее значение,
            // что бы вытащий поток ElevatorOverTheGround из цикла while при введенном Stop!
            return false;
        }
        return true;
    }
}