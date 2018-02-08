package uteevbkru.clients;

import uteevbkru.floor.Floor;

import java.io.IOException;

public class MainClient3 {
    public static void main(String[] args) throws IOException, InterruptedException{
        Thread floor3 = new Thread(new Floor(3));
        floor3.start();
    }
}
