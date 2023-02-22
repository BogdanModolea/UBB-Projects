package model.ADT;

import model.exceptions.ADTException;
import java.util.HashMap;
import java.util.Map;

public class CustomMap<K, V> implements ICustomMap<K, V> {
    private Map<K, V> map;

    public CustomMap() {
        map = new HashMap<>();
    }

    @Override
    public void add(K key, V value) throws ADTException {
        if (map.containsKey(key))
            throw new ADTException("Existing element");

        map.put(key, value);
    }

    @Override
    public void remove(K key) throws ADTException {
        if (!map.containsKey(key))
            throw new ADTException("Not existing element");

        map.remove(key);
    }

    @Override
    public V lookup(K key) {
        return map.get(key);
    }

    @Override
    public void update(K key, V value) {
        map.put(key, value);
    }

    @Override
    public boolean isHere(K key) {
        return map.containsKey(key);
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Map.Entry<K, V> element : map.entrySet())
            string.append(element.getKey()).append(": ").append(element.getValue()).append(" ");

        return string.toString();
    }
}
