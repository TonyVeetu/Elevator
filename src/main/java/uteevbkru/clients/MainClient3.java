package uteevbkru.clients;

import uteevbkru.floor.FloorClient;

import java.io.IOException;

public class MainClient3 {
    public static void main(String[] args) throws IOException, InterruptedException{
        Thread floor3 = new Thread(new FloorClient(3));
        floor3.start();
    }
}
