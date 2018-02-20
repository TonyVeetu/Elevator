package uteevbkru;

import org.junit.Assert;
import org.junit.Test;
import uteevbkru.elevator.Elevator;

import java.io.IOException;

public class ElevatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void badConstructorTest() throws IllegalArgumentException {
        Elevator  elevator = new Elevator(0, -2);
        Assert.assertEquals(0, elevator.getGapOpenClose());
    }
}