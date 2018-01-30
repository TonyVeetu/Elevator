package uteevbkru;

import org.junit.Assert;
import org.junit.Test;
import uteevbkru.elevator.Elevator;
import uteevbkru.porch.Porch;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ElevatorOverTheGroundTest {
    private ElevatorOverTheGround elevator;
    private AtomicBoolean isIterable = new AtomicBoolean(false);
    private BlockingQueue<Integer> queueOfFloors;
    private Porch porch;

    @org.junit.Before
    public void setUp() {
        int countOfFloors = 10;
        double speed = 2.0;
        double floorHeight = 2.0;
        int gapOpenClose = 2;
        int capacityOfQueue = countOfFloors;//Не может очередь быть больше количества этажей в подьезде!

        queueOfFloors = new ArrayBlockingQueue<>(capacityOfQueue);
        porch = new Porch(countOfFloors, floorHeight);
        elevator = new ElevatorOverTheGround(porch, speed, gapOpenClose, queueOfFloors, isIterable);
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTimeForOneFloorTest(){
        Assert.assertEquals(1000, elevator.getTimeForOneFloor());
    }

    @Test
    public void getCountOfFloorTest(){
        Assert.assertEquals(5, elevator.getCountOfFloors(5));
    }

    @Test
    public void getNextFloorTest(){
        queueOfFloors.add(5);
        Assert.assertEquals(5,elevator.getNextFloor());
    }

    @Test
    public void getCurrentFloor(){
        Assert.assertEquals(0, elevator.getCurrentFloor());
    }

    @Test
    public void getGapOpenCloseTest(){
        Assert.assertEquals(2, elevator.getGapOpenClose());
    }

    @Test
    public void openCloseDoorTest(){
        int delta = 100;
        long time_before = System.currentTimeMillis();
        elevator.openCloseDoors(3);
        long time_after = System.currentTimeMillis();
        long diff = time_after - time_before;
        boolean time = false;
        int gapInMs = elevator.getGapOpenClose()*1000;
        if ((gapInMs - delta <= diff) && (gapInMs + delta >= diff)){
            time = true;
        }
        Assert.assertEquals(true, time);
    }

    @Test
    public void isUpTest(){
        int floor = elevator.getCurrentFloor();
        Assert.assertEquals(true, elevator.isUp(floor + 1));
        Assert.assertEquals(false, elevator.isUp(floor - 1));
    }

    @Test
    public void badConstrictorTest(){
        int countOfFloors = 2;
        double speed = -2.0;
        double floorHeight = 2.0;
        int gapOpenClose = -2;

        Porch porch = new Porch(countOfFloors, floorHeight);
        ElevatorOverTheGround elevator = new ElevatorOverTheGround(porch , speed, gapOpenClose, queueOfFloors, isIterable);
        Assert.assertEquals(0, elevator.getGapOpenClose());
    }
}