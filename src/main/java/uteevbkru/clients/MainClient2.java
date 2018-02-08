package uteevbkru.clients;

import uteevbkru.floor.Floor;

import java.io.IOException;

public class MainClient2 {
    public static void main(String[] args) throws IOException, InterruptedException{
        Thread floor2 = new Thread(new Floor(2));
        floor2.start();
    }
}
