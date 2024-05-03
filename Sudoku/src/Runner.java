import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Runner {
	
	static HashMap fileBoard = new HashMap<Integer, int[][]>();
	static int[][] b1;
	static int[][] b2;
	static int[][] b3;
	static int[][] b4;
	static int[][] b5;
	static int[][] b6;
	static int[][] b7;
	static int[][] b8;
	static int[][] b9;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("run again");
		readFile(new File("easyBoard1.txt"));
		fileBoard.put(1, b1);
		fileBoard.put(2, b2);
		fileBoard.put(3, b3);
		fileBoard.put(4, b4);
		fileBoard.put(5, b5);
		fileBoard.put(6, b6);
		fileBoard.put(7, b7);
		fileBoard.put(8, b8);
		fileBoard.put(9, b9);
		print(b1);
		print(b5);
		print(b9);
		
		ComputerGenBoard c = new ComputerGenBoard(0);
		c.fillValues();
		c.printSudoku();
		
		SolverLogic solve = new SolverLogic(fileBoard);

		
		

	}
	
	public static void readFile(File f) {
		try {
		    File file = f;
		    Scanner s1 = new Scanner(file);
		    b1 = make3x3(s1,0);
		    b4 = make3x3(s1,0);
		    b7 = make3x3(s1,0);
		    Scanner s2 = new Scanner(file);
		    b2 = make3x3(s2,3);
		    b5 = make3x3(s2,3);
		    b8 = make3x3(s2,3);
		    Scanner s3 = new Scanner(file);
		    b3 = make3x3(s3,6);
		    b6 = make3x3(s3,6);
		    b9 = make3x3(s3,6);
		    
		    
		} catch (FileNotFoundException e) {
		    System.err.println(e.getMessage());
		}
	}
	
	public static int[][] make3x3(Scanner scanner, int start){
		int[][] b = new int[3][3];
		for(int i = 0; i<3&& scanner.hasNextLine(); i++) {
	    	String line = scanner.nextLine().substring(start);
		    for(int j = 0; j<3; j++) {
		    	b[i][j] = Character.getNumericValue(line.charAt(j));
		    }
	    }
		return b;
	}
	
	public static void print(int[][] b) {
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(b[i][j] + " ");
            }
            System.out.println();
        }
	}

}
