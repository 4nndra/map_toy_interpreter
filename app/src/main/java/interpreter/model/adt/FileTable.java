package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.util.Map;

/** Stores the readable files currently open by a program. */
public class FileTable implements IDictionary<StringValue, BufferedReader> {
    private final IDictionary<StringValue, BufferedReader> entries = new MyDictionary<>();

    /** Checks whether a file name is already associated with an open reader. */
    @Override
    // Checks whether an entry exists under the given key.
    public boolean isDefined(StringValue key) {
        return entries.isDefined(key);
    }

    /** Returns the open reader for a file name. */
    @Override
    // Returns the value associated with the given key.
    public BufferedReader lookup(StringValue key) throws ADTException {
        return entries.lookup(key);
    }

    /** Registers an open file reader. */
    @Override
    // Adds or replaces an entry in the table.
    public void put(StringValue key, BufferedReader value) {
        entries.put(key, value);
    }

    /** Updates the reader registered under a file name. */
    @Override
    // Updates the value of an existing entry.
    public void update(StringValue key, BufferedReader value) throws ADTException {
        entries.update(key, value);
    }

    /** Removes a closed file from the table. */
    @Override
    // Removes an entry from the table.
    public void remove(StringValue key) throws ADTException {
        entries.remove(key);
    }

    /** Returns an immutable snapshot used for logging and inspection. */
    @Override
    // Returns an immutable snapshot of the table contents.
    public Map<StringValue, BufferedReader> toMap() {
        return entries.toMap();
    }

    /** Returns the underlying mutable table. */
    @Override
    // Returns the underlying mutable map.
    public Map<StringValue, BufferedReader> getContent() {
        return entries.getContent();
    }

    @Override
    // Returns a readable representation of this object.
    public String toString() {
        return entries.toString();
    }
}
