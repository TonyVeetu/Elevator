package uteevbkru;

import uteevbkru.porch.Porch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
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
    /** Лифт. */
    private ElevatorOverTheGround elevatorOver;
    /** Файл для чтения Scanner'ом в тестах. */
    private File file = new File("./test.txt");
    /** Переменная характеризующая чтение из файла. */
    private boolean isFile;
    /** Подъезд. */
    private Porch porch;

    /** Конструктор для считывания из System.in. */
    public Controller(final ElevatorOverTheGround elevatorOver, Porch porch) {
        this.porch = porch;
        this.elevatorOver = elevatorOver;
        this.isIterable = elevatorOver.getIsIterable();
        scanner = new Scanner(System.in);
    }

    /** Конструктор для считывания из файла. */
    public Controller(final ElevatorOverTheGround elevatorOver, Porch porch, final boolean isFile) {
        this.porch = porch;
        this.elevatorOver = elevatorOver;
        this.isIterable = elevatorOver.getIsIterable();
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
                currentFloor = decodeFloor(str);
                if (checkLimits(currentFloor)) {
                    injectFloor(currentFloor);
                } else {
                    System.out.println("Error in input!");
                }
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
    
    /** Останавливает потоки при вводе строки "Stop".
     * @param iStr входная строка
     * @param iFloor этаж
     * @return <code>true</code> если не нужно остановить потоки
     */
    protected boolean checkForStop(final String iStr, final int iFloor) {
        if (iStr.equals("Stop") || iStr.equals("stop") || iStr.equals("стоп") || iStr.equals("Стоп")) {
            isIterable.set(true);
            injectFloor(iFloor); // Добавляем в очередь предыдущее значение,
            // что бы вытащий поток ElevatorOverTheGround из цикла while при введенном Stop!
            return false;
        }
        return true;
    }

    /** Превращает строковое значение этажа в число. */
    protected Integer decodeFloor(final String str) {
        try {
            return Integer.decode(str);
        } catch (NumberFormatException e) { }
        return 0;
    }

    /** Проверяет Лимиты.
     * @return <code>true</code> если лимиты не привышены
     *         <code>false</code> в противном случаи
     */
    protected boolean checkLimits(final Integer floor){
        if ((floor >= porch.getMinFloor()) && (floor <= porch.getMaxFloor())) {
            return true;
        } else {
            System.out.println("Inputted floor isn't correct, : " + floor);
            return false;
        }
    }

    /** Вставляет этаж "введенный пользовтелем в лифте" в очередь.
     * @return <code>true</code> значение вставилось
     *         <code>false</code> значение не вставилось
     *
     *  Не учитывается направление движения лифта!
     */

    protected boolean injectFloor(final Integer floor) {
         return elevatorOver.putInQueueForController(floor);
    }
}