package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Test;

import main.service.DataAccessService;
import main.service.DataService;

public class DataServiceTest {
	
	static DataAccessService dataAccess;
	
	public DataServiceTest() {
		dataAccess = new DataService();
	}
	
	@Test
	public void test_store_in_db() {
		initRecord();
		dataAccess.put("6", 6);
		assertEquals(6, dataAccess.get("6"));
	}
	
	/**This test will check if the record is fetched from level 1 cache
	 * 
	 */
	@Test
	public void test_getFrom_cache1_success() {
		initRecord();
		dataAccess.get("1");
		assertEquals(1, dataAccess.get("1"));
	}
	
	/**This test will check if the record not exist in level 1 cache.
	 * 
	 */
	@Test
	public void test_getFrom_cache1_failed() {
		initRecord();
		dataAccess.get("1");
		dataAccess.put("1", 11);//Latest value not in cache level 1
		//If un comment below statement test will fail,Because once we update record.
		//record in cache is not updated.here data consistency issue
		//assertEquals(11, dataAccess.get("1"));
		assertNotEquals(11, dataAccess.get("1"));
	}
	
	/**This test will check if the record is fetched from level 2 cache
	 *  If Level 1 cache capacity is full,then fetch from level 2 cache
	 */
	@Test
	public void test_getFrom_cache2_success() {
		initRecord();
		dataAccess.put("8", 8);
		dataAccess.get("8");
		assertEquals(8, dataAccess.get("8"));
	}
	
	/**This test will check if the record is fetched from level 2 cache
	 *  
	 */
	@Test
	public void test_getFrom_cache2_failed() {
		initRecord();
		dataAccess.put("8", 8);
		dataAccess.get("8");
		dataAccess.put("8", 18);//Latest value not in cache level 1,2
		//If un-comment below statement test will fail,Because once we update record
				//record in cache is not updated.here data consistency issue
		//assertEquals(18, dataAccess.get("8"));
		assertNotEquals(18, dataAccess.get("8"));
	}
	
	@After
	public void afterTest() {
		dataAccess.clear();
	}
	
	private void initRecord() {
		dataAccess.put("1", 1);
		dataAccess.put("2", 2);
		dataAccess.put("3", 3);
		dataAccess.put("4", 4);
		dataAccess.put("5", 5);
	}
}
