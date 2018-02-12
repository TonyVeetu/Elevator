package uteevbkru;

import uteevbkru.elevator.Elevator;
import uteevbkru.porch.Porch;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
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
    private AtomicInteger currentFloor = new AtomicInteger(0);

    private AtomicInteger targetFloor = new AtomicInteger(0);
    /** Очередь этажей. */
    private BlockingQueue<Integer> queueOfFloors;

    private BlockingQueue<Integer> queueUp;

    private BlockingQueue<Integer> queueDown;


    /** Показывает прерваны ли потоки.*/
    private AtomicBoolean isIterable;
    /** Количество миллисекуд в секунде. */
    private static final int MS = 1000;
    /** Время для одного преодаления одного этажа. */
    private long timeForOneFloor;
    /** Подъезд. */
    private Porch porch;

    private static int Q = 10;//TODO think about capacity!

    private boolean upTrip = true;
    
    /** Конструктор. */
    public ElevatorOverTheGround(Porch porch, final double speed, final int gapOpenClose) throws IOException {
        super(speed, gapOpenClose);
        this.porch = porch;
        queueOfFloors = new PriorityBlockingQueue<>(porch.getMaxFloor()*Q);
        queueUp = new ArrayBlockingQueue<>(porch.getMaxFloor()*Q, true);
        queueDown = new ArrayBlockingQueue<>(porch.getMaxFloor()*Q, true);
        isIterable = new AtomicBoolean(false);
        findTimeForOneFloor();
    }


    public boolean putInQueueForController(Integer floor) {
        try {
            if (floor >= porch.getMinFloor() && floor <= porch.getMaxFloor() ) {
                queueOfFloors.put(floor);
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO why not put??
    //TODO take it easy!!!
    /** Позволяет клиентам вставить этаж в очередь.
     *
     * Нужно использовать следующий этаж при вставке в очередь этажей!
     *  */

    public void  putInQueueForClient(final Integer fromWho, final boolean direction) {
        //fromWho не может прийти неправильным!
        boolean delta = (targetFloor.get() < fromWho) ? true : false;
        if (upTrip) {
            if (direction) {
                if (delta) {
                    queueOfFloors.add(fromWho);
                } else {
                    queueUp.add(fromWho);
                }
            } else {
                if(delta) {
                    queueDown.add(fromWho);
                } else {
                    queueDown.add(fromWho);
                }
            }
        } else {
            if (direction) {
                if (delta) {
                    queueUp.add(fromWho);
                } else {
                    queueUp.add(fromWho);
                }
            } else {
                if (delta){
                    queueDown.add(fromWho);
                } else {
                    queueOfFloors.add(fromWho);
                }
            }
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
        return  currentFloor.get();
    }

    public boolean getUpTrip(){ return upTrip; }

    /** Главная функция этого класса. */
    public void run() {
        while (!isIterable.get()) {
            //TODO sort queueFloors!!
            //TODO sort queueFloors!!
            targetFloor.set(getNextFloor());
            setUpTrip(targetFloor.get());
            int floors = getCountOfFloors(targetFloor.get());
            if (floors != 0) {
                moving(floors);
                openCloseDoors(targetFloor.get());
            } else {
                System.out.println("The Elevator on this floor yet!!");
            }
        }
    }

    /** @return - следующий этаж. */
    protected int getNextFloor() {
//---------
        //System.out.print("getNextFloor: "+"\t"); Object[] array = queueOfFloors.toArray();
        //for(int i = 0; i < array.length; i++) { System.out.print(array[i]+"\t");}
//-----------
        try {
            return queueOfFloors.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** @return направление движения лифта.
     *      <code>true</code> если движение вверх
     *      <code>false</code> если движение вниз
     *      */
    private boolean setUpTrip(final int nextFloor) {
        upTrip = (nextFloor > currentFloor.get()) ? true : false;
        return upTrip;
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

    /** @retun время необходимое для проезда одного этажа. */
    public long getTimeForOneFloor() {
        return timeForOneFloor;
    }

    /**
     * Эмулирует открытие и закрытие дверей.
     * Печатает на каком этаже открылись и закрылись двери
     */
    protected void openCloseDoors(final int floor) {
        try {
            System.out.println("Open doors on " + floor + " floor!");
            Thread.sleep(getGapOpenClose() * MS);
            System.out.println("Close doors on " + floor + " floor");
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
        if (upTrip) {
            currentFloor.incrementAndGet();
        } else {
            currentFloor.decrementAndGet();
        }
    }

}
