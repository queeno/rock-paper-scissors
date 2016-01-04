package rps.ui;

public interface Ui {

	public void PrintLog(String message);
	public void PrintOutput(String message);
	public void PrintError(String message);
	public String TakeInput();
	public void PrintOutputNoReturn(String message);
	
}
