package uteevbkru;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uteevbkru.porch.Porch;

public class PorchTest {
    private Porch porch;

    @Before
    public void setUp(){
        int countOfFloors = 10;
        double floorHeight = 2.0;
        porch = new Porch(countOfFloors, floorHeight);
    }

    @Test
    public void getMaxFloorTest(){
        Assert.assertEquals(10, porch.getMaxFloor());
    }

    @Test
    public void getMinFloorTest(){
        Assert.assertEquals(0, porch.getMinFloor());
    }

//    @Test
//    public void getFloorHeightTest(){
//        Assert.assertEquals(2.0, porch.getFloorHeight());
//    }

    @Test
    public void badConstructorTest(){
        Porch p = new Porch(-2, -3);
        Assert.assertEquals(0, p.getMaxFloor());
    }
}
