package uteevbkru;

import uteevbkru.elevator.MonoClientHandler;
import uteevbkru.floor.Floor;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainSocketClient {
    public static void main(String[] args) throws IOException, InterruptedException{

        MonoClientHandler monoClientHandler = new MonoClientHandler(null);
        monoClientHandler.unpackMsg("3, 7");

//        int count = 1;
//        ExecutorService service = Executors.newFixedThreadPool( count);
//        for(int i = 0; i < count; i++){
//            service.execute(new Floor());
//            Thread.sleep(100);
//        }
    }
}
