package utils;

import java.util.Arrays;

public class Utils {

	public static String join(String delimiter, String[] s){ 

		int ls = s.length; 
		switch (ls) { 
		case 0:
			return ""; 
		case 1:
			return s[0]; 
		case 2:
			return s[0].concat(delimiter).concat(s[1]); 
		default: 
			int l1 = ls / 2; 
			String[] s1 = Arrays.copyOfRange(s, 0, l1); 
			String[] s2 = Arrays.copyOfRange(s, l1, ls); 
			return join(delimiter, s1).concat(delimiter).concat(join(delimiter, s2)); 
		} 
	} 
	
}
