package uteevbkru.clients;

import uteevbkru.floor.FloorClient;

import java.io.IOException;

public class MainClient {
    public static void main(String[] args) throws IOException, InterruptedException{
        int floor;
        try {
            floor = Integer.decode(args[0]);
        } catch (NumberFormatException e){
            System.out.println("Input data isn't Integer.");
            return;
        }
        if (floor > 1 && floor <= 20) {
            Thread floor1 = new Thread(new FloorClient(floor));
            floor1.start();
        } else {
            System.out.println("Inputted floor is to big!");
        }
    }
}