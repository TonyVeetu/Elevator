package uteevbkru;

import org.junit.Assert;
import org.junit.Test;
import uteevbkru.elevator.Elevator;

public class ElevatorTest {

    @Test
    public void badConstructorTest(){
        Elevator elevator = new Elevator(0, -2);
        Assert.assertEquals(0, elevator.getGapOpenClose());
    }
}
