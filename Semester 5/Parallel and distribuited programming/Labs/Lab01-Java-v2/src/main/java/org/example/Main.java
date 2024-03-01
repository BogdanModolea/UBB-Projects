package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static String generateRandomPassword(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    private static Inventory loadInventory() {
        int PRODUCTS_NUMBER = 1000;


        Inventory inventory = new Inventory();

        for (int i = 0; i < PRODUCTS_NUMBER; i++) {
            String name = generateRandomPassword(10);
            int price = (int) (Math.random() * 91 + 10);
            int quantity = (int) (Math.random() * 100000 + 50000);
            inventory.addProduct(new Product(name, price), quantity);
        }
        return inventory;

    }

    private static Inventory generateInventorySubsets(Inventory inventory) {
        Random random = new Random();
        int productCount = random.nextInt(99) + 1;
        ArrayList<Product> productsAsArray = new ArrayList<>(inventory.getProducts());
        Inventory inventorySubset = new Inventory();

        for (int i = 0; i < productCount; i++) {
            boolean foundNewProduct = false;
            do {
                Product selectedProduct = productsAsArray.get(random.nextInt(productsAsArray.size() - 1));
                if (!inventorySubset.containsProduct(selectedProduct)) {
                    foundNewProduct = true;
                    int quantity = random.nextInt(99) + 1;
                    inventorySubset.addProduct(selectedProduct, quantity);
                }
            }
            while (!foundNewProduct);
        }

        return inventorySubset;
    }

    private static void run() {
        final int THREAD_COUNT = 10;
        Inventory inventory = loadInventory();
        int totalInitialValue = inventory.computeValue();

        ArrayList<Sale> sales = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            sales.add(new Sale(inventory, generateInventorySubsets(inventory)));
        }

        InventoryChecker inventoryChecker = new InventoryChecker(totalInitialValue, inventory, sales);

        ArrayList<Thread> threads = new ArrayList<>();
        sales.forEach(sale -> threads.add(new Thread(sale)));
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("All sales have finished. Result of the final check - ");
        inventoryChecker.checkInventory();
    }

    public static void main(String[] args) {
        run();
    }
}