package uteevbkru;

import uteevbkru.elevator.Elevator;
import uteevbkru.porch.Porch;

import java.io.IOException;
import java.sql.Time;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс Лифт без подземных этажей.
 *
 * @author Uteev Anton
 *
 * @version 1.0.1
 *
 */

public class ElevatorOverTheGround extends Elevator implements Runnable {
    /** Текущий этаж. */
    private AtomicInteger currentFloor = new AtomicInteger(1);
    /** Требуемый этаж. */
    private AtomicInteger requiredFloor = new AtomicInteger(0);
    /** Очередь этажей. */
    private LinkedBlockingDeque<Integer> queueOfFloors;
    /** Показывает прерваны ли потоки. */
    private AtomicBoolean isIterable;
    /** Количество миллисекунд в секунде. */
    private static final int MS = 1000;
    /** Время для преодаления одного этажа. */
    private long timeForOneFloor;
    /** Подъезд. */
    private Porch porch;
    /** Коэффициент запаса. */
    private static int Q = 10;
    /** Направление движения лифта */
    private AtomicBoolean upTrip = new AtomicBoolean(true);

    /** Конструктор. */
    public ElevatorOverTheGround(Porch porch, final double speed, final int gapOpenClose) {
        super(speed, gapOpenClose);
        this.porch = porch;
        queueOfFloors = new LinkedBlockingDeque<>(porch.getMaxFloor()*Q);
        isIterable = new AtomicBoolean(false);
        findTimeForOneFloor();
    }

    /** Позволяет Controller вставить этаж в очередь. */
    public boolean putInQueueForController(Integer floor) throws InterruptedException {
        if (checkFloor(floor)) {
            queueOfFloors.put(floor);
            return true;
        } else {
            return false;
        }
    }

    /** Проверка вводимого этажа
     * @param floor необходимый этаж
     * @return <code>true</code>  проверка пройдена
     *
     */
    private boolean checkFloor(Integer floor) {
        if (floor >= porch.getMinFloor() && floor <= porch.getMaxFloor()) {
            return true;
        } else {
            return false;
        }
    }

    /** Позволяет клиентам вставить этаж в очередь.
     *  @param fromWho - от какого этажа последовал вызов
     *  @param direction - избыточен сейчас, но может потребоваться при дальнейшем усовершенствовании алгоритма
     */
    public void  putInQueueForClient(final Integer fromWho, final boolean direction) throws InterruptedException{
        if (checkFloor(fromWho)) {
            queueOfFloors.put(fromWho);
        }
    }

    public AtomicBoolean getIsIterable() {
        return isIterable;
    }

    /** Расчитывает время необходимое для проезда одного этажа. */
    public void findTimeForOneFloor() {
        timeForOneFloor = Math.round(porch.getFloorHeight() / getSpeed()) * MS;
    }

    /** @return промежуток между открытием и закрытием дверей. */
    public final int getGapOpenClose() {
        return super.getGapOpenClose();
    }

    /** @return текущий этаж. */
    public int getCurrentFloor() {
        return currentFloor.get();
    }

    /** @return <code>upTrip</code> */
    public boolean getUpTrip() { return upTrip.get(); }

    /** Главная функция этого класса. */
    @Override
    public void run() {
        while (!isIterable.get()) {
            sortQueue();
            requiredFloor.set(getNextFloor(upTrip.get()));
            if (!catchChangeUpTrip(requiredFloor.get())) {
                System.out.println("Elevator has changed the direction of moving!");
                queueOfFloors.add(requiredFloor.get());
                sortQueue();
                requiredFloor.set(getNextFloor(upTrip.get()));
            }
    if (requiredFloor.get() > 0) {
        System.out.println("\t\t"+"requiredFloor = "+requiredFloor);
    }
            int floors = getCountOfFloors(requiredFloor.get());
            if (floors != 0) {
                moving(floors);
                openCloseDoors(requiredFloor.get());
            }
        }
    }

    /**
     * Сортировка очереди по возрастанию.
     */
    public void sortQueue(){
        int size = queueOfFloors.size();
        if (size > 1) {
            Integer array1[] = new Integer[size];
            queueOfFloors.toArray(array1);
            for (int i = 0; i < size; i++) {
                queueOfFloors.remove(array1[i]);
            }
            Arrays.sort(array1, (Integer x, Integer y) -> (x < y) ? -1 : ((x == y) ? 0 : 1));
            for(int i = 0; i < size; i++) {
                queueOfFloors.add(array1[i]);
                System.out.print(array1[i]+"\t");
            }
            System.out.println();
        }
    }

    /** @return - следующий этаж.
     * @param upTrip направление движения лифта
     */
    protected int getNextFloor(boolean upTrip) {
        try {
            if (upTrip) {
                return queueOfFloors.takeFirst();
            } else {
                return queueOfFloors.takeLast();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** Определяет меняет ли лифт направление движения.
     * @return true - если направление не изменилось
     *         false - если направление изменилось
     * @param nextFloor - следующий этаж
     */

    private boolean catchChangeUpTrip(final int nextFloor) {
        boolean currentUpTrip = upTrip.get();
        return currentUpTrip == checkUpTrip(nextFloor);
    }

    /** @return направление движения лифта.
     *      <code>true</code> если движение вверх
     *      <code>false</code> если движение вниз
     */
    private boolean checkUpTrip(final int nextFloor) {
        upTrip.set(nextFloor >= currentFloor.get());
        return upTrip.get();
    }

    /** @return количество этажей. */
    public int getCountOfFloors(final int nextFloor) {
        return Math.abs(nextFloor - currentFloor.get());
    }

    /** Эмитация движения кабины лифта. */
    private void moving(final int countOfFloors) {
        for (int i = 0; i < countOfFloors; i++) {
            try {
                Thread.sleep(timeForOneFloor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printCurrentFloor();
            changeCurrentFloor();
        }
    }

    /** @return время необходимое для проезда одного этажа. */
    public long getTimeForOneFloor() {
        return timeForOneFloor;
    }

    /**
     * Эмулирует открытие и закрытие дверей.
     * Печатает на каком этаже открылись и закрылись двери
     */
    protected void openCloseDoors(final int floor) {
        try {
            System.out.println("\t"+"Open doors on "+floor+ " floor!");
            Thread.sleep(getGapOpenClose() * MS);
            System.out.println("\t"+"Close doors on "+floor+" floor");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** Печатает текущий этаж. */
    private void printCurrentFloor() {
        System.out.println("Current floor: " + currentFloor);
    }

    /** Меняет текущий этаж. */
    private void changeCurrentFloor() {
        if (upTrip.get()) {
            currentFloor.incrementAndGet();
        } else {
            currentFloor.decrementAndGet();
        }
    }

}
