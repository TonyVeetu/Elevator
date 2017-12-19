import java.util.*;

public class Elevator implements Move{
    private int countOfFloors;
    private int speed;
    private int floorHeight;
    private int gapOpenClose;

    private int currentFloor = 0;

    private Queue<Integer> queueOfFloors;


    public Elevator(int countOfFloors, int speed, int floorHeight, int gapOpenClose){
        this.countOfFloors = countOfFloors;
        this.speed = speed;
        this.floorHeight = floorHeight;
        this.gapOpenClose = gapOpenClose;
    }

    private void init(){
        queueOfFloors = new LinkedList<Integer>();
        queueOfFloors.add(8);
        queueOfFloors.add(4);
        queueOfFloors.add(10);
    }

    public void start(){
        MyRun myRun = new MyRun();
        myRun.start();
        while(!queueOfFloors.isEmpty()){
            int floor = queueOfFloors.poll();
            System.out.println("Current floor: " + currentFloor);
            //TODO правильно поделить!!
            long timeForMove = (Math.abs(floor - currentFloor)/speed);
            System.out.println(timeForMove);
            try {
                Thread.sleep(timeForMove*1000);
            }
            catch (InterruptedException e ){
                e.printStackTrace();
            }
            openAndClose(floor);
            currentFloor = floor;

        }
        myRun.setInter();
    }

    public void up(){

    }

    public void down(){

    }

    public void openAndClose(int floor){
        System.out.println("Open doors on " + floor + " floor!");
        try {
            Thread.sleep(gapOpenClose * 1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Close doors on " + floor + " floor");
    }


    public static void main(String[] args){

        Elevator elevator = new Elevator(10, 1,2, 1);
        elevator.init();
        elevator.start();

    }
}
