package uteevbkru.floor;

import java.io.*;
import java.net.Socket;

public class Floor implements Runnable{
    private static int count = 0;
    private int currentFloor;
    static Socket socket;

    public Floor(){
        count++;
        currentFloor = count;
        System.out.println("new Floor = "+currentFloor);
        try {
            socket = new Socket("localhost", 4444);
            System.out.println("Client connected to socket");
        } catch (Exception e) {
            System.out.println("отказано в подключении!!");
            e.printStackTrace();
        }
    }

    public void run() {
        try ( DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
              DataInputStream ois = new DataInputStream(socket.getInputStream())) {
            System.out.println("Client oos & ois initialized");
            int i = 0;
            while (i < 5) {
                oos.writeUTF(i + ", floor:" + currentFloor);
                // проталкиваем сообщение из буфера сетевых сообщений в канал
                oos.flush();

                // ждём чтобы сервер успел прочесть сообщение из сокета и
                // ответить
                Thread.sleep(100);
                System.out.println("Client wrote & start waiting for data from server...");

                // забираем ответ из канала сервера в сокете
                // клиента и сохраняем её в ois переменную, печатаем на
                // консоль
                //System.out.println("reading...");
                //String in = ois.readUTF();
                //System.out.println(in);
                i++;
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

