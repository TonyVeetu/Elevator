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
    private final static int ZERO_FLOOR = 0;
    /** @param Максимальный этаж */
    private int maxFloor;
    /** @param Высота этажа */
    private double floorHeight;

    /**
     * @param iCountOfFloors количество этажей.
     * @param iFloorHeight высота этажа
     * @throws IOException если параметры не корректны
     */
    public Porch(final int iCountOfFloors, final double iFloorHeight) throws IOException {
        if ((iCountOfFloors > 0) && (iFloorHeight > 0)) {
            this.maxFloor = iCountOfFloors;
            this.floorHeight = iFloorHeight;
        } else {
            System.out.println("Bad parameters for Porch!");
            throw new IOException();
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
