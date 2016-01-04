package rps.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cli implements Ui {

	private boolean printoutlog;
	
	public Cli (){
		printoutlog = false;
	}
	
	public String TakeInput(){
		BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		
		try {
			input = scan.readLine();
			//scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return input;
	}
	
	public void SetPrintOutLog(boolean value) {
		printoutlog = value;
	}
	
	public void PrintOutput(String message){
		System.out.println(message);
	}
	
	public void PrintOutputNoReturn(String message){
		System.out.print(message);
	}
	
	public void PrintLog(String message) {
		if (printoutlog) {
			System.out.println(message);
		}
	}
	
	public void PrintError(String message) {
		System.err.println(message);
	}
}
