package uteevbkru;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerTest {
    private ElevatorOverTheGround elevator;
    private Controller controller;
    private AtomicBoolean isIterable;
    private BlockingQueue<Integer> queueOfFloors;

    @Before
    public void setUp(){
        int countOfFloors = 10;
        double speed = 2.0;
        double floorHeight = 2.0;
        int gapOpenClose = 2;

        int capacityOfQueue = countOfFloors;//Не может очередь быть больше количества этажей в подьезде!
        queueOfFloors = new ArrayBlockingQueue<>(capacityOfQueue);
        elevator = new ElevatorOverTheGround(countOfFloors, speed, floorHeight, gapOpenClose, queueOfFloors, isIterable);
        isIterable = new AtomicBoolean();
        controller = new Controller(elevator, queueOfFloors, isIterable);
    }

    @Test
    public void getFloorTest() {
        String strFloor = "3";
        int floor = controller.getFloor(strFloor);
        Assert.assertEquals(3, floor);
    }

    @Test
    public void getFloorErrorTest(){
        String strFloor = "a";
        int floor = controller.getFloor(strFloor);
        Assert.assertEquals(0, floor);
    }

}