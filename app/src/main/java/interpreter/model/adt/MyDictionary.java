package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.Map;
import java.util.HashMap;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> dictionary = new HashMap<>();

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        if (!dictionary.containsKey(key)) {
            throw new ADTException("key not found: " + key);
        }
        return dictionary.get(key);
    }

    @Override
    public void put(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (!dictionary.containsKey(key)) {
            throw new ADTException("can't update missing key: " + key);
        }
        dictionary.put(key, value);
    }

    @Override
    public Map<K, V> getContent() {
        return dictionary;
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }
}
