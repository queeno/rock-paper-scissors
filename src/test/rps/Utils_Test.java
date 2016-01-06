package test.rps;

import static org.junit.Assert.*;

import rps.utils.Utils;

import org.junit.Before;
import org.junit.Test;

public class Utils_Test {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void joinEmptyString() {
		
		String[] str_array = new String[]{""};
		
		String result = Utils.join("", str_array);
		
		assertEquals(result, "");
	}
	
	@Test
	public void joinOneCharString(){
		
		String[] str_array = new String[]{"s"};
		
		String result = Utils.join(",", str_array);
		
		assertEquals(result, "s");
	}
	
	@Test
	public void joinTwoStrings(){
		
		String[] str_array = new String[]{"simon","mark"};
		
		String result = Utils.join(", ", str_array);
		
		assertEquals(result, "simon, mark");
	}
	
	@Test
	public void joinManyStrings(){
		
		String[] str_array = new String[]{"a","b","c","d","e","f","g"};
		
		String result = Utils.join("?", str_array);
		
		assertEquals(result, "a?b?c?d?e?f?g");
	}
	

}
