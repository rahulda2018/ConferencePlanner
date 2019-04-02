package com.conference.management.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Logger Utility, This class helps to load the logging.properties and override the default Java logging properties 
 *
 */
public class AppLogger {

	private static Logger LOGGER = null; 
	
	static {
		initializeLogger();
	}
	
	public static Logger getLogger(String className) {
		LOGGER = Logger.getLogger(className);
		return LOGGER;
	}

	/**
	 * Loads the logging.properties and override the default Java logging properties
	 */
	public static synchronized void initializeLogger() {
		
	   try {
		   String workingDir = System.getProperty("user.dir");
		   String fileName = workingDir + File.separator + "config" +  File.separator + "logging.properties";
		   
		   FileInputStream fileInputStream = new FileInputStream(fileName);
		   LogManager.getLogManager().readConfiguration(fileInputStream);
		   
	   } catch (IOException ex) {
		  System.err.println("Failed to load logging.properlies, please check the file and the path:" + ex.getMessage());  
	      ex.printStackTrace();
	      System.exit(0);
	   }
	}
	
}
