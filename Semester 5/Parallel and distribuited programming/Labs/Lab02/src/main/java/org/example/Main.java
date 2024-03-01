package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static int partialResult = -1;
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition readyToSendProduct = lock.newCondition();
    private static final Condition readyToReceiveProduct = lock.newCondition();
    private static final ArrayList<Integer> product = new ArrayList<>();

    public static void main(String[] args) {
        ArrayList<Integer> v = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Integer> u = new ArrayList<>(Arrays.asList(2, 3, 4));

        // Expected result: 1 * 2 + 2 * 3 + 3 * 4 = 20

        Thread producer = new Thread(() -> {
            for (int i = 0; i < v.size(); i++) {
                lock.lock();
                try {
                    while (!product.isEmpty()) {
                        readyToSendProduct.await();
                    }
                    partialResult = v.get(i) * u.get(i);
                    product.add(partialResult);
                    readyToReceiveProduct.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            int ans = 0;
//            for (int i = 0; i < v.size(); i++) {
              while (true) {
                lock.lock();
                try {
                    while (product.isEmpty()) {
                        readyToReceiveProduct.await();
                    }
                    while (!product.isEmpty()) {
                        ans += product.get(0);
                        product.remove(0);
                        readyToSendProduct.signal();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                System.out.println("Total sum is " + ans);
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}