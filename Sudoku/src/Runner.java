import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Runner {
	
	static int[][] board;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("run again");
		readFile(new File("easyBoard1.txt"));

	}
	
	public static void readFile(File f) {
		try {
		    File file = f;
		    Scanner scanner = new Scanner(file);
		    
		    
		    
		} catch (FileNotFoundException e) {
		    System.err.println(e.getMessage());
		}
	}

}
