public class Demo {
    public static void main(String[] args) {
        TaxiCompany HoaMai = new TaxiCompany();
        for (int i = 0; i < 8 ; i++) {
            Runnable client = new Client(HoaMai);
            Thread thread = new Thread(client);
            thread.start();
        }
    }
}
