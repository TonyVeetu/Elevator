package uteevbkru.porch;

import java.io.IOException;

/**
 * Класс Подъезд.
 * @author Uteev Anton
 * @version 1.0.1
 *
 */

public class Porch {
    /** @param Минимальный этаж*/
    private final static int ZERO_FLOOR = 1;
    /** @param Максимальный этаж */
    private int maxFloor;
    /** @param Высота этажа */
    private double floorHeight;

    /**
     * @param iCountOfFloors количество этажей.
     * @param iFloorHeight высота этажа
     * @throws IllegalArgumentException если параметры не корректны
     */
    public Porch(final int iCountOfFloors, final double iFloorHeight) {
        if ((iCountOfFloors > 0) && (iFloorHeight > 0)) {
            this.maxFloor = iCountOfFloors;
            this.floorHeight = iFloorHeight;
        } else {
            throw new IllegalArgumentException("Porch: input parameters isn't correct.");
        }
    }

    /** @return минимальный этаж */
    public final int getMinFloor() {
        return ZERO_FLOOR;
    }

    /** @return  максимальный этаж*/
    public final int getMaxFloor() {
        return maxFloor;
    }

    /** @return высоту этажа*/
    public final double getFloorHeight() {
        return floorHeight;
    }

}
