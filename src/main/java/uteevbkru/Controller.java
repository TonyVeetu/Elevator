package uteevbkru;

import uteevbkru.porch.Porch;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**Класс обрабатывает введенное число пользователем.
 * @author Uteev Anton
 * @version 1.0.1
 */
public class Controller extends Thread {
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
        scanner = new Scanner(System.in);
    }

    /** Конструктор для считывания из файла. */
    public Controller(final ElevatorOverTheGround elevatorOver, Porch porch, final boolean isFile) {
        this.porch = porch;
        this.elevatorOver = elevatorOver;
        this.isFile = isFile;
    }

    /** Главная функция этого класса. */
    @Override
    public void run() {
        Integer currentFloor;
        while (true) {
            if (scanner.hasNext()) {
                String str = scanner.nextLine();
                if (isFile) {
                    System.out.println(str);
                }
                currentFloor = decodeFloor(str);
                if (checkLimits(currentFloor)) {
                    injectFloor(currentFloor);
                } else {
                    System.out.println("Error in input!");
                }
            }
        }
    }

    /** Инициализирует Scanner для чтения из файла. */
    protected void initReadFromFile() {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** Превращает строковое значение этажа в число. */
    Integer decodeFloor(final String str) {
        try {
            return Integer.decode(str);
        } catch (NumberFormatException e) { }
        return 0;
    }

    /** Проверяет Лимиты.
     * @return <code>true</code> если лимиты не привышены
     *         <code>false</code> в противном случаи
     */
    private boolean checkLimits(final Integer floor){
        if (floor >= Porch.ZERO_FLOOR && floor <= porch.getMaxFloor()) {
            return true;
        } else {
            System.out.println("Inputted floor isn't correct, : " + floor);
            return false;
        }
    }

    /** Вставляет этаж "введенный пользовтелем в лифте" в очередь.
     * @return <code>true</code> значение вставилось
     *         <code>false</code> значение не вставилось
     */
    boolean injectFloor(final Integer floor) {
        try {
            return elevatorOver.putInQueueForController(floor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}