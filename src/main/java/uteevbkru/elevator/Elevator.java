package uteevbkru.elevator;

/**
 * Базовый класс - Лифт
 * @author Uteev Anton
 * @version 1.0.1
 */

public class Elevator {
    /** Скорость */
    private double speed;
    /** Время на откытие и закрытие дверей на этаже */
    private int gapOpenClose;

    /** Конструктор */
    public Elevator(double speed, int gapOpenClose){
        if( (speed > 0) && (gapOpenClose > 0) ){
            this.speed = speed;
            this.gapOpenClose = gapOpenClose;
        }
        else {
            System.out.println("Parameters for Elevator is not correct!");
        }
    }

    /** @retunr время между открытием и закрытием дверей */
    public int getGapOpenClose(){ return gapOpenClose; }

    /** @retunr скорость лифта */
    public double getSpeed(){ return speed; }
}
