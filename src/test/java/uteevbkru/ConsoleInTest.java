package uteevbkru;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsoleInTest {
    private Controller console;
    private BlockingQueue<Integer> queue;
    private AtomicBoolean isIterable;
    private int maxFloors = 10;

    @Before
    public void setUp(){

        queue = new ArrayBlockingQueue<>(maxFloors);
        isIterable = new AtomicBoolean();
        console = new Controller(queue, isIterable, maxFloors);
    }

    @Test
    public void getFloorTest() {
        String strFloor = "3";
        int floor = console.getFloor(strFloor);
        Assert.assertEquals(3, floor);
    }

    @Test
    public void getFloorErrorTest(){
        String strFloor = "a";
        int floor = console.getFloor(strFloor);
        Assert.assertEquals(0, floor);
    }

}