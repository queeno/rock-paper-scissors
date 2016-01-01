package rps.ui;

public class Cli implements Ui {

	private boolean printoutlog;
	
	public Cli (){
		printoutlog = false;
	}
	
	public void SetPrintOutLog(boolean value) {
		printoutlog = value;
	}
	
	public void PrintOutput(String message){
		System.out.println(message);
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
