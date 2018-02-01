package uteevbkru;

import org.junit.Assert;
import org.junit.Test;
import uteevbkru.elevator.Elevator;

import java.io.IOException;

public class ElevatorTest {

    @Test
    public void badConstructorTest(){
        Elevator elevator;
        try {
            elevator = new Elevator(0, -2);
        } catch (IOException e){
            return;
        }
        Assert.assertEquals(0, elevator.getGapOpenClose());
    }
}
