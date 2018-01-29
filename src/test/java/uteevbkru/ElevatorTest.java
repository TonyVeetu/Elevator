package uteevbkru;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class ElevatorTest {
    private Elevator elevator;
    AtomicBoolean isIterable = new AtomicBoolean(false);

    @org.junit.Before
    public void setUp() {
        int countOfFloors = 10;
        double speed = 2.0;
        double floorHeight = 2.0;
        int gapOpenClose = 2;

        int capacityOfQueue = countOfFloors;//Не может очередь быть больше количества этажей в подьезде!
        BlockingQueue<Integer> queueOfFloors = new ArrayBlockingQueue<Integer>(capacityOfQueue);

        elevator = new Elevator(countOfFloors, speed, floorHeight, gapOpenClose, queueOfFloors, isIterable);
        queueOfFloors.add(5);

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
    public void getNextFloorTest(){Assert.assertEquals(5,elevator.getNextFloor());}

}