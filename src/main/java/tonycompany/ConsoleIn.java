package tonycompany;

import java.util.Queue;
import java.util.Scanner;

public class ConsoleIn extends Thread{
    public ConsoleIn(Queue<Integer> queue){
        this.queue = queue;
    }

    private Queue<Integer> queue;

    public void run(){
        Scanner scanner = new Scanner(System.in);  //создаём магию ввода
        while(true){
            if(scanner.hasNext()){
                String str = scanner.nextLine();
                Integer floor = Integer.decode(str);
                if((floor >= 0) && (floor <= 10)){
                    synchronized (queue){
                        queue.add(floor);
                    }
                }
            }
        }
    }
}
