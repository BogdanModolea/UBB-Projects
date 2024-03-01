package org.example;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class InventoryChecker {
    public final int totalInitialValue;
    public final Inventory inventory;
    public final ArrayList<Sale> sales;
    private static final ReentrantLock lock = new ReentrantLock();

    public InventoryChecker(int totalInitialValue, Inventory inventory, ArrayList<Sale> sales) {
        this.totalInitialValue = totalInitialValue;
        this.inventory = inventory;
        this.sales = sales;
    }

    public void checkInventory() {
        lock.lock();
        int difference = this.totalInitialValue - sales.stream().mapToInt(Sale::getProfit).sum() - this.inventory.computeValue();
        lock.unlock();

        if (Math.abs(difference) > 0.01) {
            System.out.println("Invalid inventory");
        } else {
            System.out.println("Inventory is ok");
        }
    }
}
