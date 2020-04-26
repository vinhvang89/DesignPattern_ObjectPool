import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TaxiCompany {
    private static final long EXPIRED_TIME_IN_MS = 10000;
    private static final int NUMBER_OF_TAXI = 5;
    private final List<Taxi> available = Collections.synchronizedList(new ArrayList<>());
    private final List<Taxi> onWorking = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicBoolean wait = new AtomicBoolean(false);
    public synchronized Taxi getTaxi() throws InterruptedException {
        if(!available.isEmpty()) {
            Taxi taxi = available.remove(0);
            onWorking.add(taxi);
            return taxi;
        }
        if(count.get() == NUMBER_OF_TAXI){
            this.waitingUntilStaffAvailable();
            return this.getTaxi();
        }
        Taxi taxi = this.creatTaxi();
        onWorking.add(taxi);
        return taxi;

    }
    public synchronized void release(Taxi taxi){
        onWorking.remove(taxi);
        available.add(taxi);
        System.out.println(taxi.getName() + " is free");
    }
    private Taxi creatTaxi(){
        waiting(300);
        return new Taxi("Taxi " + count.incrementAndGet());
    }
    private void waitingUntilStaffAvailable() throws InterruptedException {
        if(wait.get()){
            wait.set(false);
            throw new InterruptedException("ALo");
        }
        wait.set(true);
        waiting(EXPIRED_TIME_IN_MS);
    }
    private void waiting(long numberOfSecond){
        try {
            TimeUnit.MILLISECONDS.sleep(numberOfSecond);
        }catch (Exception e){
            System.out.println("Trash Guest");
            Thread.currentThread().interrupt();
        }
    }

}
