package com.danieldhsd.framework.api.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

public class Utils {

	public static final String HOME_DIR = System.getProperty("user.home");
	public static final String FILE_SEPARATOR = System.getProperty("path.separator");
	
	
	public static final String saveFile(MultipartFile file) throws IOException  {
		String pathFile = Utils.HOME_DIR + Utils.FILE_SEPARATOR + "photos" 
							+ Utils.FILE_SEPARATOR + file.getOriginalFilename();
		OutputStream out = new FileOutputStream(pathFile);
		out.write(file.getBytes());
		out.close();
		
		return pathFile;
	}
}
