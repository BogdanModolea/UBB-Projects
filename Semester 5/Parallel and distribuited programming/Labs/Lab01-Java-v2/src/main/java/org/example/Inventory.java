package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Inventory {
    private HashMap<Product, Integer> products;
    private static final ReentrantLock lock = new ReentrantLock();

    public Inventory() {
        this.products = new HashMap<>();
    }

    public Collection<Product> getProducts() {
        lock.lock();
        Collection<Product> products = this.products.keySet();
        lock.unlock();
        return products;
    }

    public boolean containsProduct(Product product) {
        lock.lock();
        boolean result = this.products.containsKey(product);
        lock.unlock();
        return result;
    }

    public int getQuantityOfProduct(Product product) {
        lock.lock();
        int quantity = this.products.get(product);
        lock.unlock();
        return quantity;
    }

    public int computeValue() {
        lock.lock();
        int totalPrice = 0;
        for (Map.Entry<Product, Integer> productQuantityPair : this.products.entrySet()) {
            totalPrice += productQuantityPair.getKey().getPrice() * productQuantityPair.getValue();
        }
        lock.unlock();
        return totalPrice;
    }

    public void addProduct(Product product, int quantity) {
        lock.lock();
        this.products.putIfAbsent(product, quantity);
        lock.unlock();
    }

    public void removeProduct(Product product, int quantity) {
        lock.lock();

        if (!this.products.containsKey(product)) {
            throw new RuntimeException("Not enough products in the inventory");
        }

        int previousQuantity = this.products.get(product);
        if (previousQuantity < quantity) {
            throw new RuntimeException("Product does not exist in the inventory");
        }

        if (previousQuantity == quantity) {
            this.products.remove(product);
        } else {
            this.products.replace(product, previousQuantity - quantity);
        }

        lock.unlock();
    }
}
