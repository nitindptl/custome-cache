package main.db;

import java.util.concurrent.ConcurrentHashMap;

public class DataBase {
	
	private static ConcurrentHashMap<String, Object> cache;
	 
    public DataBase() {
    	cache = new ConcurrentHashMap<String, Object>();
    }
    
 
    public Object get(String key) {
        return cache.get(key);
    }
    
    public boolean put(String key, Object value) {
        if (key == null) {
            return false;
        }
        
        if (value == null) {
            cache.remove(key);
        } else {
            cache.put(key, value);
        }
        return true;
    }
 
    
    public void remove(String key) {
        cache.remove(key);
    }
 
    public void clear() {
        cache.clear();
    }
 
    public long size() {
        return cache.entrySet().stream().count();
    }

}
