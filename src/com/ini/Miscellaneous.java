package com.ini;

public class Miscellaneous 
{
	private static final String HEXES = "0123456789abcdef";
	public static String encodeHex(byte[] data)
	{
		if(data == null)
		{
			return null;
		}
		
		int len = data.length;
		StringBuilder hex = new StringBuilder(len * 2);
		
		for(int i = 0; i < len; ++i) 
		{
			hex.append(HEXES.charAt((data[i] & 0xF0) >>> 4));
			hex.append(HEXES.charAt((data[i] & 0x0F)));
		}
		
		return hex.toString();
	}
	
	public static boolean isEmpty(String string)
	{
		return (string == null || string.length() <= 0);
	}
	
	public static boolean wildcardMatch(String pattern, String string)
	{
		if(isEmpty(pattern) && isEmpty(string))
		{
			return true;
		}
		else if(isEmpty(pattern) || isEmpty(string))
		{
			return false;
		}
		return wildcardInternal(pattern, 0, pattern.length(), string, 0, string.length());
	}
	
	private static boolean wildcardInternal(String pat, int patPos, int patLen, String str, int strPos, int strLen)
	{
		while(strPos < strLen && patPos < patLen)
		{
			char patChar = pat.charAt(patPos);
			if(patChar == '?')
			{
				if(wildcardInternal(pat, patPos + 1, patLen, str, strPos, strLen)) 
				{
					return true;				
				}
				++patPos;
				++strPos;
			}
			else if(patChar == '*')
			{
				while(++patPos < patLen)
				{
					patChar = pat.charAt(patPos);
					if(patChar != '*' && patChar != '?')
					{
						break;
					}
				}
				if(patPos >= patLen)
				{
					return true;
				}
				
				while(strPos < strLen)
				{
					if(wildcardInternal(pat, patPos, patLen, str, strPos, strLen))
					{
						return true;
					}
					++strPos;
				}
				return false;
			}
			else
			{
				if(patChar != str.charAt(strPos))
				{
					return false;
				}
				++patPos;
				++strPos;
			}
		}
		
		if(strPos < strLen)
		{
			return false;
		}
		else
		{
			while(patPos < patLen)
			{
				char patChar = pat.charAt(patPos);
				if(patChar != '*' && patChar != '?')
				{
					break;
				}
				
				++patPos;
			}
			return (patPos >= patLen);
		}
	}
}
