package rps.config;

import rps.ui.Cli;

public class Validate {
	
	private int[][] shape_matrix;
	private String[] shape_names;
	private Cli ui;
	
	public Validate(Cli ui, int[][] shape_matrix, String[] shape_names){
		this.shape_matrix = shape_matrix;
		this.shape_names = shape_names;
		this.ui = ui;
	}

	private boolean checkElements(int i, int j){	
	
		if (i == j) {
			if (shape_matrix[i][j] != -1){
				ui.PrintError("Element [" + i + "][" + j + "]" + " expected"
						+ "empty, got " + shape_matrix[j][i]);
				return true;
			}
			else {
				return false;
			}
		} else {
			if (shape_matrix[i][j] == shape_matrix[j][i]){
				if (shape_matrix[i][j] == 0) {
					ui.PrintError("Shape Matrix, element [" + i + "][" + j
							+ "]" + " expected 1 got 0. "
							+ "The " + shape_names[i] +
							" cannot be defeated by the " + shape_names[j] + " and the "
							+ shape_names[j] + " cannot be defeated by the "
							+ shape_names[i]);
					return true;
				} else if (shape_matrix[i][j] == 1) {
					ui.PrintError("Shape Matrix, element [" + i + "][" + j
							+ "]" + " expected 0 got 1. "
							+ "The " + shape_names[i] +
							" cannot be beat the " + shape_names[j] +
							" and the " + shape_names[j] +
							" cannot beat the " + shape_names[i]);
					return true;
				}
			}
			else {
				if (shape_matrix[i][j] == 0){
					ui.PrintLog("The " + shape_names[i] + " is defeated by the "
							+ shape_names[j]) ;
					return false;
				}
				else if (shape_matrix[i][j] == 1) {
					ui.PrintLog("The " + shape_names[i] + " beats the "
							+ shape_names[j]);
					return false;
				}
			}
		}
	
		// This point should be never reached.
		return true;
	}
	
	protected void validateShapeMatrix(){
	
		boolean error = false;
		
		ui.PrintLog("Beginning Matrix validation...");
		
		for (int i=0; i< shape_matrix.length; i++){
			for (int j=0; j< shape_matrix.length; j++){
				if (checkElements(i,j)) error = true ;
			}
		}
		
		if (error) {
			ui.PrintError("Matrix validation failed."
					+ " Please fix the shape matrix and try again.");
			System.exit(1);
		}
		
		ui.PrintLog("Matrix validation completed!");
		
	}
		
}
