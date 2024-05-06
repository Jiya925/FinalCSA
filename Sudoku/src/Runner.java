import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public class Runner {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("run again");
		HashMap<Integer, int[][]> easyBoard = readFile(new File("easyBoard1.txt"));
		HashMap<Integer, int[][]> mediumBoard = readFile(new File("mediumBoard1.txt"));
		HashMap<Integer, int[][]> hardBoard = readFile(new File("hardBoard1.txt"));

		GUI gui = new GUI(easyBoard, mediumBoard, hardBoard);
        gui.setVisible(true);


	}
	
	public static HashMap<Integer, int[][]> readFile(File f) {
		try {
			HashMap<Integer, int[][]> fileBoard = new HashMap<Integer, int[][]>();
			
		    File file = f;
		    Scanner s1 = new Scanner(file);
		    int[][] b1 = make3x3(s1,0);
		    int[][] b4 = make3x3(s1,0);
		    int[][] b7 = make3x3(s1,0);
		    Scanner s2 = new Scanner(file);
		    int[][] b2 = make3x3(s2,3);
		    int[][] b5 = make3x3(s2,3);
		    int[][] b8 = make3x3(s2,3);
		    Scanner s3 = new Scanner(file);
		    int[][] b3 = make3x3(s3,6);
		    int[][] b6 = make3x3(s3,6);
		    int[][] b9 = make3x3(s3,6);
		    
		    fileBoard.put(1, b1);
			fileBoard.put(2, b2);
			fileBoard.put(3, b3);
			fileBoard.put(4, b4);
			fileBoard.put(5, b5);
			fileBoard.put(6, b6);
			fileBoard.put(7, b7);
			fileBoard.put(8, b8);
			fileBoard.put(9, b9);
			
			return fileBoard;
		    
		} catch (FileNotFoundException e) {
		    System.err.println(e.getMessage());
		}
		return null;
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




