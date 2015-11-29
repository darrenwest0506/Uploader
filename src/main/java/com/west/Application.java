package com.west;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class Application {
	
	/**
	 * Starts the spring application and loads. Scans packages for REST marked endpoints.
	 * @param args
	 * @throws IOException 
	 * @throws JAXBException 
	 * @throws MalformedURLException 
	 * @throws XMLStreamException 
	 */
	public static void main(String[] args){	
		SpringApplication.run(Application.class);
	}
	
}
