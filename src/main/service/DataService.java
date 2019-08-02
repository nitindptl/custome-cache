package main.service;

import main.cache.FileCache;
import main.cache.InMemoryCahce;
import main.config.Config;
import main.db.DataBase;


/**
 * This class is data access service.used to get and store record
 *
 */
public class DataService implements DataAccessService{
	
	private InMemoryCahce inMemory;
	private DataBase db;
	
	public DataService() {
		inMemory = new InMemoryCahce(Config.IN_MEMORY_CACHE_SIZE);
		db = new DataBase();
	}
	
	/* This method use to fetch record using key.
	 * If Level 1 cache capacity is full,then fetch from level 2 cache.
	 * If records not exist in level 1&2 cache,fetch from DB
	 * @see service.CacheService#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		
		//TODO:Here extra functional code required,if existing records updated need to update/clear both cache.
		//Otherwise this will leads to data consistency problem.
		Object obj = null;
		//Fetch from Level 1 cache
		if(inMemory.get(key) != null) {
			obj= inMemory.get(key);
		}else {
			//If record not exist in level 1 cache then fetch from Level 2 cache
			FileCache fileCache = new FileCache();
			obj = fileCache.load(key);
			//If record not exist in cache fetch from DB
			if(obj == null) {
				obj = db.get(key);
				if(obj != null) {
					//When record first fetch from DB,update both cache with fetched record.
					//So that subsequent request will fetch from cache
					updateCahce(key, obj);
				}
			}
		}
		return obj;
	}

	/* This method used to store record
	 * @see service.CacheService#put(java.lang.String, java.lang.Object)
	 */
	@Override
	public void put(String key, Object value) {
		db.put(key, value);
	}
	
	/** This method store record fetched from DB into Level 1,2 cache 
	 * @param key
	 * @param value
	 */
	public void updateCahce(String key, Object value) {
		inMemory.put(key, value);
		FileCache fileCache = new FileCache();
		fileCache.store(key, value);
	}

	/* This method clears both level 1 & 2 cache
	 * @see service.DataAccessService#clear()
	 */
	@Override
	public void clear() {
		InMemoryCahce inMemory = new InMemoryCahce();
		inMemory.clear();
		FileCache fileCache = new FileCache();
		fileCache.clearFile();
	}
}
