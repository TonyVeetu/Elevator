package uteevbkru.porch;

/**
 * Класс Подъезд
 * @author Uteev Anton
 * @version 1.0.1
 */

public class Porch {
    /** Минимальный этаж*/
    private final static int minFloor = 0;
    /** Максимальный этаж */
    private int maxFloor;
    /** Высота этажа */
    private double floorHeight;

    /** Конструктор создания подьезда */
    public Porch(final int countOfFloors, final double floorHeight ){
        if ( (countOfFloors > 0) && (floorHeight > 0) ){
            this.maxFloor = countOfFloors;
            this.floorHeight = floorHeight;
        }
        else {
            System.out.println("Bad parameters for Porch!");
        }
    }

    /** @return минимальный этаж */
    public int getMinFloor(){
        return minFloor;
    }

    /** @return  максимальный этаж*/
    public int getMaxFloor(){
        return maxFloor;
    }

    /** @return высоту этажа*/
    public double getFloorHeight(){return floorHeight;}

}
