package model.ADT;

import model.exceptions.ADTException;

import java.util.Map;

public interface ICustomMap<K, V> {
    void add(K key, V value) throws ADTException;

    public void remove(K key) throws ADTException;

    public V lookup(K key);

    public void update(K key, V value);

    boolean isHere(K key);

    public Map<K, V> getContent();
}