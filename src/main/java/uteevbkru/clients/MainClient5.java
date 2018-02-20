package uteevbkru.clients;

import uteevbkru.floor.FloorClient;

import java.io.IOException;

public class MainClient5 {
    public static void main(String[] args) throws IOException, InterruptedException{
        Thread floor5 = new Thread(new FloorClient(5));
        floor5.start();
    }
}
