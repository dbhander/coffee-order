package edu.iu.habahram.coffeeorder.repository;

import edu.iu.habahram.coffeeorder.model.*;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Repository
public class OrderRepository {

    private final Set<Integer> usedIds = new HashSet<>();

    public int generateUniqueId(){
        Random rand=new Random();
        int uniqueId;
        do{
            uniqueId=rand.nextInt(Integer.MAX_VALUE);
        }while(usedIds.contains(uniqueId));
        usedIds.add(uniqueId);
        return uniqueId;
    }
    public Receipt add(OrderData order) throws Exception {
        Beverage beverage;
        switch (order.beverage().toLowerCase()) {
            case "dark roast" -> beverage = new DarkRoast();
            case "espresso" -> beverage = new Espresso();
            case "decaf" -> beverage = new Decaf();
            case "house blend" -> beverage = new HouseBlend();
            default -> throw new Exception("Beverage type '%s' is not valid!".formatted(order.beverage()));
        }

        for (String condiment : order.condiments()) {
            switch (condiment.toLowerCase()) {
                case "milk" -> beverage = new Milk(beverage);
                case "mocha" -> beverage = new Mocha(beverage);
                case "soy" -> beverage = new Soy(beverage);
                case "whip" -> beverage = new Whip(beverage);
                default -> throw new Exception("Condiment type '%s' is not valid!".formatted(condiment));
            }
        }

        int id = generateUniqueId();
        Receipt receipt = new Receipt(id, beverage.getDescription(), beverage.cost());
        saveDataToFile(receipt);
        return receipt;
    }


    private void saveDataToFile(Receipt receipt) throws IOException {
        try(FileWriter writer=new FileWriter("db.txt",true)){
            writer.write("%d, %.2f, %s%n".formatted(
                    receipt.id(),receipt.cost(),receipt.description()
            ));
        }

    }
}
