package net.noxumbrarum.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
	public static String loadFileAsString(String fileName) {
		StringBuilder fileContent = new StringBuilder();
		
		try(BufferedReader reader = Files.newBufferedReader(Paths.get("resources", fileName))) {
			String line;
			while((line = reader.readLine()) != null) {
				fileContent.append(line);
				fileContent.append("\n");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return fileContent.toString();
	}
}
