package com.algaworks.algafood.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.ResourceUtils;

public class MetodoReadJson {
	
	public static String readJson(String path) {
		
		try {
			InputStream stream = ResourceUtils.class
					.getResourceAsStream(path);
			return org.springframework.util.StreamUtils
					.copyToString(stream, Charset.forName("UTF-8"));
			
					
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
				
		
	}

}
