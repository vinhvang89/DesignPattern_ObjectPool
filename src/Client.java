import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable {
    private TaxiCompany center;
    public Client(TaxiCompany center){
        this.center = center;
    }

    @Override
    public void run(){
        takeTaxi();
    }
    private void takeTaxi(){
        try{
            System.out.println("New client : " + Thread.currentThread().getName());
            Taxi taxi = center.getTaxi();
            TimeUnit.MILLISECONDS.sleep(randInt(1000,1500));
            center.release(taxi);
            System.out.println("Served the client: " + Thread.currentThread().getName());
        } catch (Exception e){
            System.out.println("Rejected client : "+ Thread.currentThread().getName());
        }
    }
    public static int randInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
