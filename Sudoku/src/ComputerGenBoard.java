import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ComputerGenBoard {
	
	HashMap ans =  new HashMap<Integer, int[][]>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public ComputerGenBoard() {
		int num = 1;
		
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = 1; i <= 9; i++) {
			int random = (int)((Math.random()*9)+1);
			//make a new arrayList to hold values 
			while (temp.contains(random)) {
				random = (int)((Math.random()*9)+1);
			}
			temp.add(random);
		}
		
		int[][] t = new int[3][3];
		int i = 0;
		for(int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				t[r][c] = temp.get(i);
				i++;
			}
		}
		ans.put(num, t);
		
		num++;
	}

}
