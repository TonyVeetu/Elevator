package uteevbkru;

import org.junit.Assert;
import org.junit.Test;
import uteevbkru.porch.Porch;

import java.io.IOException;

public class ElevatorOverTheGroundTest {
    private ElevatorOverTheGround elevator;
    private Porch porch;

    @org.junit.Before
    public void setUp() {
        int countOfFloors = 10;
        double speed = 2.0;
        double floorHeight = 2.0;
        int gapOpenClose = 2;

        try {
            porch = new Porch(countOfFloors, floorHeight);
            elevator = new ElevatorOverTheGround(porch, speed, gapOpenClose);
        } catch (IOException e ){
            return;
        }
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
        elevator.putInQueueForController(5);
        Assert.assertEquals(5,elevator.getNextFloor(true));
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
    public void badConstrictorTest(){
        int countOfFloors = 2;
        double speed = -2.0;
        double floorHeight = 2.0;
        int gapOpenClose = -2;

        Porch porch;
        ElevatorOverTheGround elevator;
        try {
            porch = new Porch(countOfFloors, floorHeight);
            elevator = new ElevatorOverTheGround(porch, speed, gapOpenClose);
        } catch (IOException e ){
            return;
        }
        Assert.assertEquals(0, elevator.getGapOpenClose());
    }
}