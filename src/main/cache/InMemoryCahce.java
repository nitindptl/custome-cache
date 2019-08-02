package main.cache;

import java.util.concurrent.ConcurrentHashMap;


/** This class is used as Level 1,InMemory cache
 *
 */
public class InMemoryCahce {
	
	public static Integer IN_MEMORY_DEFAULT_CACHE_SIZE = 5;
	 
    private ConcurrentHashMap<String, Object> cache;
 
    public InMemoryCahce() {
    	//Creating in-memory object with default size
    	cache = new ConcurrentHashMap<String, Object>(IN_MEMORY_DEFAULT_CACHE_SIZE);
    }
    
    public InMemoryCahce(int size) {
    	IN_MEMORY_DEFAULT_CACHE_SIZE = size;
    	cache = new ConcurrentHashMap<String, Object>(size);
    }
 
    
    /** This method used to fetch record
     * @param key
     * @return Object
     */
    public Object get(String key) {
        return cache.get(key);
    }
    
    /** This method used to store record
     * @param key
     * @param value
     * @return boolean
     */
    public boolean put(String key, Object value) {
        if (key == null) {
            return false;
        }
        //If excceds size return,don not store object
        if (cache.size() == IN_MEMORY_DEFAULT_CACHE_SIZE) {
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