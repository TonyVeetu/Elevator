package uteevbkru;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uteevbkru.porch.Porch;


public class ElevatorOverTheGroundTest {
    private ElevatorOverTheGround elevator;
    private Porch porch;

    @Before
    public void setUp() {
        int countOfFloors = 10;
        double speed = 2.0;
        double floorHeight = 2.0;
        int gapOpenClose = 2;

        porch = new Porch(countOfFloors, floorHeight);
        elevator = new ElevatorOverTheGround(porch, speed, gapOpenClose);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTimeForOneFloorTest(){
        Assert.assertEquals(1000, elevator.getTimeForOneFloor());
    }

    @Test
    public void getCountOfFloorTest(){
        Assert.assertEquals(4, elevator.getCountOfFloors(5));
    }

    @Test
    public void getNextFloorTest(){
        try {
            elevator.putInQueueForController(5);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        Assert.assertEquals(5,elevator.getNextFloor(true));
    }

    @Test
    public void getCurrentFloor(){
        Assert.assertEquals(1, elevator.getCurrentFloor());
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

    @Test(expected = IllegalArgumentException.class)
    public void badConstrictorTest() throws IllegalArgumentException {
        int countOfFloors = 2;
        double speed = -2.0;
        double floorHeight = 2.0;
        int gapOpenClose = -2;

        Porch porch = new Porch(countOfFloors, floorHeight);
        ElevatorOverTheGround elevator = new ElevatorOverTheGround(porch, speed, gapOpenClose);
        Assert.assertEquals(0, elevator.getGapOpenClose());
    }
}