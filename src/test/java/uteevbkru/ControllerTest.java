package uteevbkru;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uteevbkru.porch.Porch;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerTest {
    private ElevatorOverTheGround elevator;
    private Controller controller;
    private AtomicBoolean isIterable;
    private BlockingQueue<Integer> queueOfFloors;
    private Scanner scanner;
    private Porch porch;

    @Before
    public void setUp(){
        int countOfFloors = 10;
        double speed = 2.0;
        double floorHeight = 2.0;
        int gapOpenClose = 2;

        int capacityOfQueue = countOfFloors;//Не может очередь быть больше количества этажей в подьезде!
        queueOfFloors = new ArrayBlockingQueue<>(capacityOfQueue);
        porch = new Porch(countOfFloors, floorHeight);
        elevator = new ElevatorOverTheGround(porch ,speed, gapOpenClose, queueOfFloors, isIterable);
        isIterable = new AtomicBoolean();
        controller = new Controller(elevator, porch, queueOfFloors, isIterable, true);
        scanner = new Scanner(System.in);
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

    @Test
    public void checkTest(){
        Assert.assertEquals(false, controller.check("Stop", 5));
    }

    @Test
    public void isIterableTest(){
        Assert.assertEquals(false, controller.getIsIterable());
        controller.check("Stop", 5);
        Assert.assertEquals(true, controller.getIsIterable());
    }

    @Test
    public void injectFloorTest(){
        Assert.assertEquals(true, controller.injectFloor(5));
    }

    @Test
    public void injectFloorErrorTest(){
        Assert.assertEquals(false, controller.injectFloor(55));
    }

    @Test
    public void runTest(){
        controller.initReadFromFile();
        controller.start();
        while (!controller.getIsIterable()){

        }
    }

}