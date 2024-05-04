import java.util.HashMap;
import java.util.Map;

public class SolverLogic {
    int[][] board;
    
    //turn hashmap into a 9x9
    public SolverLogic(int[][] userInput) {
        //initialize a new 9x9 board
        this.board = userInput;

        System.out.println("Initial Board:");
        printSudoku(board);

        System.out.println("Solving...");
        
        //olve the sudoku
        if (solveSudoku(board, 0, 0)) {
            System.out.println("Sudoku solved!");
        } 
        else {
            System.out.println("Sudoku puzzle could not be solved.");
        }

        System.out.println("Filled Board:");
        printSudoku(board);
    }



    //backtracking algorithm
    public boolean solveSudoku(int[][] board, int row, int col) {
    	//filled board
        if (row == 9) {
            return true;
        }

        //if we have reached the end of a row, move to the next row
        if (col == 9) {
            return solveSudoku(board, row + 1, 0);
        }
        //if the cell is already filled, move to the next cell
        if (board[row][col] != 0) {
            return solveSudoku(board, row, col + 1);
        }

        for (int num = 1; num <= 9; num++) {
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
        return false;
    }


    
    //print filled board
    private void printSudoku(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
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
 
        for (int y = 0; y <= 8; y++) {
            if (board[y][col] == num) {
                return false;
            }
        }
 
        int startRow = row - row % 3;
        int startCol = col - col % 3;
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
