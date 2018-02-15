package uteevbkru.clients;

import uteevbkru.floor.FloorClient;

import java.io.IOException;

public class MainClient4 {
    public static void main(String[] args) throws IOException, InterruptedException{
        Thread floor4 = new Thread(new FloorClient(4));
        floor4.start();
    }
}
