package com.ini;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class IniReader {

	private Map<String,String> mMap = new HashMap<String,String>(); 
	
	public IniReader(File iniFile){
			
		FileInputStream fis = null;
		ByteArrayInputStream bais = null;
		BufferedReader br = null;
		
		try
		{
			fis = new FileInputStream(iniFile);
			
			int len = fis.available();
			byte[] content = new byte[len];			
			fis.read(content);
			
			bais = new ByteArrayInputStream(content, 0, content.length);
			
			br = new BufferedReader(new InputStreamReader(bais,"utf-8"));
			
			String line = null;
			String[] keyValue = null;
			
			while((line = br.readLine()) != null){
				keyValue = line.split("=");
				mMap.put(keyValue[0], keyValue[1]);
			}
		}
		catch(Exception e)
		{
			return;
		}
		
		finally
		{
			try{
				
				if(fis != null){
					fis.close();
				}
				
				if(bais != null){
					bais.close();
				}
				
				if(br != null){
					br.close();
				}
			}catch(Exception e){}
		}
	}
	
	public String getValue(String key){
		return mMap.get(key);
	}
}
