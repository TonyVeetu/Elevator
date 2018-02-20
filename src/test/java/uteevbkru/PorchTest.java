package uteevbkru;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uteevbkru.porch.Porch;
import java.io.IOException;

public class PorchTest {
    private Porch porch;

    @Before
    public void setUp() throws IOException{
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
        Assert.assertEquals(1, porch.getMinFloor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void badConstructorTest() throws IllegalArgumentException{
        porch = new Porch(-2, 3);
    }

}
