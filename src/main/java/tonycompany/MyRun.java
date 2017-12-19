public class MyRun extends Thread {

    private boolean isInter = false;

    public void run(){
        while(true) {
            if(!isInter) {
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

    public void setInter(){
        isInter = true;
    }
}
