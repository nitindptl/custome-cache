package main;
import main.service.DataAccessService;
import main.service.DataService;

public class MainCacheTest {

	public static void main(String[] args) {
	
		DataAccessService dataAccess = new DataService();
		
		System.out.println(dataAccess.get("1"));
		dataAccess.put("1", 112);
		dataAccess.put("2", 2);
		dataAccess.put("3", 3);
		dataAccess.put("4", 114);
		dataAccess.put("5", 115);
		dataAccess.put("6", 116);
		System.out.println(dataAccess.get("1"));
		System.out.println(dataAccess.get("12"));
		System.out.println(dataAccess.get("1"));
		dataAccess.clear();
		
		
	}

}
