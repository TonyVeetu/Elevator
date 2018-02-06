package uteevbkru;

import uteevbkru.floor.Floor;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainSocketClient {
    public static void main(String[] args) throws IOException, InterruptedException{
        ExecutorService service = Executors.newFixedThreadPool( 2);
        for(int i = 0; i < 2; i++){
            service.execute(new Floor());
            Thread.sleep(100);
        }
    }
}
