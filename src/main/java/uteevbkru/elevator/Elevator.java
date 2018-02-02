package uteevbkru.elevator;

import java.io.IOException;

/**
 * Базовый класс - Лифт.
 *
 * @author Uteev Anton
 *
 * @version 1.0.1
 */

public class Elevator {
    /** @param скорость движения лифта */
    private final double speed;
    /** @param Время на открытие и закрытие дверей */
    private final int gapOpenClose;

    /**
     * @param iSpeed скорость движения лифта
     * @param iGapOpenClose Время на открытие и закрытие дверей
     * @throws IOException  если параметры не корректные
     *
     */
    public Elevator(final double iSpeed, final int iGapOpenClose) throws IOException {
        if ((iSpeed > 0) && (iGapOpenClose > 0)) {
            this.speed = iSpeed;
            this.gapOpenClose = iGapOpenClose;
        } else {
            System.out.println("Parameters for Elevator is not correct!");
            throw new IOException();
        }
    }

    /** @return время между открытием и закрытием дверей. */
    public int getGapOpenClose() {
        return gapOpenClose;
    }

    /** @return скорость лифта. */
    public final double getSpeed() {
        return speed;
    }
}
