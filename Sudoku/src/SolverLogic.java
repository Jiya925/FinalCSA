import java.util.HashMap;
import java.util.Map;

public class SolverLogic {
    int[][] board;
    public int N = 9;
    
    //turn hashmap into a 9x9
    public SolverLogic(HashMap<Integer, int[][]> fileBoard) {
        //initialize a new 9x9 board
        board = new int[9][9];
        
        //iterate through each 3x3 array in fileBoard
        for (Map.Entry<Integer, int[][]> entry : fileBoard.entrySet()) {
            int key = entry.getKey();
            int[][] threeByThree = entry.getValue();
            
            //calculate starting row and column in the 9x9 grid based on the key
            int rowStart = ((key-1) / 3) * 3;
            int colStart = ((key-1) % 3) * 3;

            
            //place the 3x3 array into the 9x9 grid at the calculated starting position
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                	if (rowStart + i < 9 && colStart + j < 9) {
                        board[rowStart + i][colStart + j] = threeByThree[i][j];
                    }
                }
            }
        }
        System.out.println("Initial Board:");
        for (int[] row : board) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }

        //solve the puzzle
        solveSudoku();
        
        // Print the initial board
        printSolution();

        // Solve the puzzle
        boolean solved = solveSudoku();

        // Print the solved board if the puzzle was solved
        if (solved) {
            printSolution();
        } else {
            System.out.println("Sudoku puzzle could not be solved.");
        }
    }

    //backtracking algorithm
    public boolean solveSudoku() {
        return solveSudoku(board, 0, 0);
    }
    
    public boolean solveSudoku(int board[][], int row, int col) {
        // Check if we have reached the end of the board
        if (row == N - 1 && col == N) {
            return true;
        }

        //if we have reached the end of a row, move to the next row
        if (col == N) {
            row++;
            col = 0;
        }

        //if the cell is already filled, move to the next cell
        if (board[row][col] != 0) {
            return solveSudoku(board, row, col + 1);
        }

        //try each number from 1 to 9
        for (int num = 1; num < 10; num++) {
            if (isValid(board, row, col, num)) {
                //if the number is valid, place it in the cell
                board[row][col] = num;
                //recursively solve for the next cell
                if (solveSudoku(board, row, col + 1)) {
                    return true;
                }
                //if the solution fails, backtrack and try the next number
                board[row][col] = 0;
            }
        }
        //if no number works, backtrack
        return false;
    }
    
    //print filled board
    public void printSolution() {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                System.out.print(board[r][c]);
            }
            System.out.println();
        }
    }
    
    public boolean isValid(int[][] board, int row, int col, int num) {
        //check if num is already present in the row/column/subgrid
    	for (int x = 0; x <= 8; x++) {
            if (board[row][x] == num) {
                return false;
            }
    	}
 
        for (int x = 0; x <= 8; x++) {
            if (board[x][col] == num) {
                return false;
            }
        }
 
        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
    		}
    	}
 
        return true;
	}
}
