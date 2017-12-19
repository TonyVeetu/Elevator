package tonycompany;

public class SecondCounter extends Thread {

    private boolean isIterable = false;

    public void run(){
        while(true) {
            if(!isIterable) {
                System.out.println("\t\t" + " dzin! " + 1 + " second!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else
                break;
        }
    }

    public void setIsIterable(){
        isIterable = true;
    }
}
