package uteevbkru.clients;

import uteevbkru.floor.FloorClient;

import java.io.IOException;

public class MainClient1 {
    public static void main(String[] args) throws IOException, InterruptedException{
        Thread floor1 = new Thread(new FloorClient(1));
        floor1.start();
    }
}
