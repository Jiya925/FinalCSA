
public class ComputerGenBoard {
	 int[][] board;
	    int N; // number of cols & rows.
	    int SRNum; // square root of N
	    int spots; // number of missing spots --> based on difficulty
	    // Constructor
	    public ComputerGenBoard() {
	        N = 9;
	        // find square root of N
	        SRNum = (int)(Math.sqrt(N));
	        board = new int[N][N];
	    }
	   
	    //returns the board
	    public int[][] getBoard() {
	    	return board;
	    }
	    // fill all the values of the board
	    public void fillValues() {
	        // Fill the diagonal of 3 x 3 (SRNum) board
	        fillDiagonal();
	        // Fill remaining blocks
	        fillRemaining(0, SRNum);
	    }
	    // Fill the 3 diagonal of 3 x 3 boards
	    void fillDiagonal() {
	        for (int i = 0; i < N; i = i+SRNum) {
	        	// for diagonal box, start coordinates --> i==j
	            fillBox(i, i);
	        }
	    }
	    // Returns false if given 3 x 3 block contains num.
	    boolean unUsedInBox(int rowStart, int colStart, int num) {
	        for (int i = 0; i<SRNum; i++) {
	            for (int j = 0; j<SRNum; j++) {
	                if (board[rowStart+i][colStart+j]==num) {
	                    return false;
	                }
	            }
	        }
	        return true;
	    }
	    // Fill a 3 x 3 spot.
	    void fillBox(int row, int col) {
	        int num;
	        for (int i = 0; i < SRNum; i++) {
	            for (int j = 0; j < SRNum; j++) {
	            	num = randomGenerator(N);
	                while (!unUsedInBox(row, col, num)) {
	                	num = randomGenerator(N);
	                }
	                board[row+i][col+j] = num;
	            }
	        }
	    }
	    // Random generator
	    int randomGenerator(int num) {
	        return (int) Math.floor((Math.random()*num+1));
	    }
	    // Check if safe to put in cell
	    boolean CheckIfSafe(int i, int j, int num) {
	        return (unUsedInRow(i, num) &&
	                unUsedInCol(j, num) &&
	                unUsedInBox(i-i%SRNum, j-j%SRNum, num));
	    }
	    // check in the row for existence
	    boolean unUsedInRow(int i, int num) {
	        for (int j = 0; j < N; j++) {
	           if (board[i][j] == num) {
	                return false;
	           }
	        }
	        return true;
	    }
	    // check in the row for existence
	    boolean unUsedInCol(int j, int num) {
	        for (int i = 0; i < N; i++) {
	            if (board[i][j] == num) {
	                return false;
	            }
	        }
	        return true;
	    }
	    // A recursive function to fill remaining board
	    boolean fillRemaining(int i, int j) {
	        //  System.out.println(i+" "+j);
	        if (j >= N && i < N-1) {
	            i = i + 1;
	            j = 0;
	        }
	        if (i >= N && j >= N) {
	            return true;
	        }
	        if (i < SRNum) {
	            if (j < SRNum) {
	                j = SRNum;
	            }
	        }
	        else if (i < N-SRNum) {
	            if (j == (int)(i / SRNum) * SRNum) {
	                j =  j + SRNum;
	            }
	        }
	        else {
	            if (j == N-SRNum) {
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
	    // Remove the spots no. of digits to
	    // complete game
	    public void removespotsDigits(int k) {
	    	
	    	if (k == 1) { //easy board
	    		spots = (int)((Math.random()*10) + 25);
	    	}
	    	else if (k == 2) { //medium board
	    		spots = (int)((Math.random()*10) + 35);
	    	}
	    	else if (k == 3) {  //hard board
	    		spots = (int)((Math.random()*10) + 45);
	    	}
	    	
	        int count = spots;
	        while (count != 0) {
	            int cellId = randomGenerator(N*N)-1;
	            // System.out.println(cellId);
	            // extract coordinates i  and j
	            int i = (cellId / N);
	            int j = cellId % N;
	            // System.out.println(i+" "+j);
	            if (board[i][j] != 0) {
	                count--;
	                board[i][j] = 0;
	            }
	        }
	    }
	    // Print sudoku
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
