package edu.hendrix.csci250.csci250proj3;

import java.io.File;
import java.nio.file.Files;

public class FileOps {
	public static boolean detectDBFile(String fileName) {
		File databaseFile = new File(fileName);
		if (databaseFile.exists()) {
			return true;
		}
		return false;
	}

	public static void backupDB() throws Exception {
		Files.copy(new File("epdb.db").toPath(), new File("epdb.db.bak").toPath());
	}
	
	public static void restoreDB() {
		File databaseFile = new File("epdb.db");
		File databaseFileBackup = new File("epdb.db.bak");
		if (databaseFile.exists()) {
			databaseFile.delete();
		}
		databaseFileBackup.renameTo(databaseFile);
	}

	public static void deleteDBBackup() {
		File databaseFile = new File("epdb.db.bak");
		if (databaseFile.exists()) {
			databaseFile.delete();
		}
	}

}
