package ics.core.util;

import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertyUtil{
	
	public static String getProperty(String propertyName) {
		return getProperty(propertyName, null);
	}

	public static String getProperty(String propertyName, String defaultValue) {
		String value = defaultValue;
		
		ApplicationContext applicationContext = ApplicationContextServe.getApplicationContext();
		
		if (applicationContext.getEnvironment().getProperty(propertyName) == null) {
			log.warn(propertyName + " properties was not loaded.");
		} else {
			value = applicationContext.getEnvironment().getProperty(propertyName).toString();
		}
		return value;
	}
		

}
