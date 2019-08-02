package main.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileCache {

	private String FILE_NAME = getClass().getClassLoader().getResource("fileCache.txt").getFile();

	private File getFile() {
		File file = new File(FILE_NAME);
		if (!file.exists()) {
			try {
				File directory = new File(file.getParent());
				if (!directory.exists()) {
					directory.mkdirs();
				}
				file.createNewFile();
			} catch (IOException e) {
			}
		}
		return file;
	}

	// Save to file Utility
	private void writeToFile(String key, Object object) {
		try {
			Map<String, Object> map = readFromFile();
			FileOutputStream fileOut = new FileOutputStream(getFile());
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			map.put(key, object);
			out.writeObject(map);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			System.out.println("Got an error while saving data to file " + e.toString());
		}
	}

	// Save to file Utility
	public void clearFile() {
		try {
			Map<String, Object> map = readFromFile();
			FileOutputStream fileOut = new FileOutputStream(getFile());
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(null);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			System.out.println("Got an error while saving data to file " + e.toString());
		}
	}

	// Read From File Utility
	public HashMap<String, Object> readFromFile() {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = getFile();
		if (!file.exists())
			System.out.println("File doesn't exist");
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			if (file.length() != 0) {
				map = (HashMap) in.readObject();
				if(map == null)
					map = new HashMap<String, Object>();
			}

		} catch (Exception e) {
			System.out.println("error load cache from file " + e.toString());
		}
		return (HashMap<String, Object>) map;
	}

	public Object getObject(String key) {
		HashMap<String, Object> map = readFromFile();
		try {
			if(map == null) {
				return null;
			}
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getKey().equals(key))
					return entry.getValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public Object load(String key) {
		return getObject(key);
	}

	public void store(String key, Object value) {
		writeToFile(key, value);
	}
}
