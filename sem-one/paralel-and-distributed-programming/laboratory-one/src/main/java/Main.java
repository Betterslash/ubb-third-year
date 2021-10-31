import model.Customer;
import repository.Store;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        var prop = new Properties();
        try {
            prop.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
            var customersNumber = Integer.parseInt(prop.getProperty("customersNumber"));
            var store = new Store();
            var customers = new ArrayList<Customer>();
            for(int i = 0; i < customersNumber; i++){
                customers.add(new Customer());
            }
            customers.forEach(e -> store.sell(e.getBill()));
            try {
                store.startSelling();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            System.out.printf("Store got %d %n",store.getMoney());
            store.getBills()
                    .forEach(System.out::println);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
