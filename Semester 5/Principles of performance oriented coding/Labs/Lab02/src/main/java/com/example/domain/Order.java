package com.example.domain;

import java.util.Objects;

public class Order implements Comparable<Order> {
    private final int id;
    private final int price;
    private final int quantity;

    public Order(int id, int price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && price == order.price && quantity == order.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, quantity);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public int compareTo(Order o) {
        return Integer.compare(this.price, o.price);
    }
}
