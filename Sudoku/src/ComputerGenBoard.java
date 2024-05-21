
public class ComputerGenBoard {
	 	private int[][] board;
	    private int N;      //number of cols & rows.
	    private int root;   //square root of N
	    private int spots;  //number of missing spots --> based on difficulty
	    
	    public ComputerGenBoard() {
	        N = 9;
	        //square root of N
	        root = (int)(Math.sqrt(N));
	        board = new int[N][N];
	    }
	   
	    //returns the board
	    public int[][] getBoard() {
	    	return board;
	    }
	    
	    // fills all the values of the board - mother method and others are helpers
	    public void fillValues() {
	        // fills the diagonal of 3x3 (root) board
	        fillDiagonal();
	        // fills remaining blocks
	        fillRemaining(0, root);
	    }
	    
	    //fills the 3 diagonal of 3x3 boards
	    public void fillDiagonal() {
	        for (int i = 0; i < N; i = i+root) {
	        	// for diagonal box, start coordinates --> i==j
	            fillBox(i, i);
	        }
	    }
	    
	    //returns false if given 3x3 block contains num.
	    boolean unUsedInBox(int rowStart, int colStart, int num) {
	        for (int i = 0; i<root; i++) {
	            for (int j = 0; j<root; j++) {
	                if (board[rowStart+i][colStart+j]==num) {
	                    return false;
	                }
	            }
	        }
	        return true;
	    }
	    
	    //fills a 3x3 spot
	    //overall a helper method to fill in a 3x3 box
	    public void fillBox(int row, int col) {
	        int num;
	        for (int i = 0; i < root; i++) {
	            for (int j = 0; j < root; j++) {
	            	num = randomGenerator(N);
	            	//keeps creating as long until random is not in the box already 
	                while (!unUsedInBox(row, col, num)) {
	                	num = randomGenerator(N);
	                }
	                board[row+i][col+j] = num;
	            }
	        }
	    }
	    
	    //random generator of number 
	    public int randomGenerator(int num) {
	    	//floor returns largest possible of the random 
	        return (int) Math.floor((Math.random()*num+1));
	    }
	    
	    //check if safe to put in cell
	    public boolean CheckIfSafe(int i, int j, int num) {
	        return (unUsedInRow(i, num) && unUsedInCol(j, num) && unUsedInBox(i-i%root, j-j%root, num));
	    }
	    
	    //check in the row for existence of num (i is tracker)
	    public boolean unUsedInRow(int i, int num) {
	        for (int j = 0; j < N; j++) {
	           if (board[i][j] == num) {
	                return false;
	           }
	        }
	        return true;
	    }
	    
	    //check in the col for existence of num (j is tracker)
	    public boolean unUsedInCol(int j, int num) {
	        for (int i = 0; i < N; i++) {
	            if (board[i][j] == num) {
	                return false;
	            }
	        }
	        return true;
	    }
	    
	    //recursive function that fills the remaining board
	    public boolean fillRemaining(int i, int j) {
	        //  System.out.println(i+" "+j);
	        if (j >= N && i < N-1) {
	            i = i + 1;
	            j = 0;
	        }
	        if (i >= N && j >= N) {
	            return true;
	        }
	        if (i < root) {
	            if (j < root) {
	                j = root;
	            }
	        }
	        else if (i < N-root) {
	            if (j == (int)(i / root) * root) {
	                j =  j + root;
	            }
	        }
	        else {
	            if (j == N-root) {
	                i = i + 1;
	                j = 0;
	                if (i >= N) {
	                    return true;
	                }
	            }
	        }
	        for (int num = 1; num <= N; num++) {
	            if (CheckIfSafe(i, j, num)) {
	                board[i][j] = num;
	                if (fillRemaining(i, j+1)) {
	                    return true;
	                }
	                board[i][j] = 0;
	            }
	        }
	        return false;
	    }
	    
	    //remove the spots number of digits to complete game
	    public void removespotsDigits(int k) {
	    	
	    	if (k == 1) { //easy board
	    		spots = (int)((Math.random()*10) + 22); //removes 22 to 32 spots
	    	}
	    	else if (k == 2) { //medium board
	    		spots = (int)((Math.random()*11) + 34); //removes 34 to 45 spots
	    	}
	    	else if (k == 3) {  //hard board
	    		spots = (int)((Math.random()*12) + 45); //removes 45 to 57 spots
	    	}
	    	
	        int count = spots;
	        while (count != 0) {
	            int cellId = randomGenerator(N*N)-1;
	            // System.out.println(cellId);
	            //get coordinates i and j
	            int i = (cellId / N);
	            int j = cellId % N;
	            // System.out.println(i+" "+j);
	            if (board[i][j] != 0) {
	                count--;
	                board[i][j] = 0; //sets empty spots to 0
	            }
	        }
	    }
	    
	    // Print sudoku board
	    public void printSudoku() {
	        for (int i = 0; i < N; i++) {
	            for (int j = 0; j < N; j++) {
	                System.out.print(board[i][j] + " ");
	            }
	            System.out.println();
	        }
	        System.out.println();
	    }
}
