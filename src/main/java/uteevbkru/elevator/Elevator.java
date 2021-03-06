package uteevbkru.elevator;

/**Базовый класс - Лифт
 * @author Uteev Anton
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
     * @throws IllegalArgumentException если параметры не корректные
     *
     */
    public Elevator(final double iSpeed, final int iGapOpenClose) throws IllegalArgumentException {
        if ((iSpeed > 0) && (iGapOpenClose > 0)) {
            this.speed = iSpeed;
            this.gapOpenClose = iGapOpenClose;
        } else {
            throw new IllegalArgumentException("Elevator: input parameters isn't correct!");
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
