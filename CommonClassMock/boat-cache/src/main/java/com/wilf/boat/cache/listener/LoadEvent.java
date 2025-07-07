package com.wilf.boat.cache.listener;


import java.util.Set;

/**
 * @author wfhstart
 * @create 2024-12-25
 */
public class LoadEvent<K> {
    private String name;

    private Set<K> keys;

    public LoadEvent(String name, Set<K> keys) {
        this.name = name;
        this.keys = keys;
    }

    public Set<K> getKeys() {
        return keys;
    }

    public void setKeys(Set<K> keys) {
        this.keys = keys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
