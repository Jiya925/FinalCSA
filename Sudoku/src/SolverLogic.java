import java.util.HashMap;
import java.util.Map;

public class SolverLogic {
    private int[][] board;

    public SolverLogic(HashMap<Integer, int[][]> fileBoard) {
        //initialize a new 9x9 board
        board = new int[9][9];
        
        //iterate through each 3x3 array in fileBoard
        for (Map.Entry<Integer, int[][]> entry : fileBoard.entrySet()) {
            int key = entry.getKey();
            int[][] threeByThree = entry.getValue();
            
            //calculate starting row and column in the 9x9 grid based on the key
            int rowStart = (key / 3) * 3;
            int colStart = (key % 3) * 3;
            
            //place the 3x3 array into the 9x9 grid at the calculated starting position
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[rowStart + i][colStart + j] = threeByThree[i][j];
                }
            }
        } 
    }

    //backtracking algorithm
    public int[][] solveSudoku(int grid[][], int row, int col) {
        //check if board has been solved completely
    	if (row == 8 && col == 9)
            return grid;

        if (col == 9) {
            row++;
            col = 0;
        }

        //check if there's already a number in that position
        if (grid[row][col] != 0)
            return solveSudoku(grid, row, col + 1);

        for (int num = 1; num <= 9; num++) {
            if (isValid(row, col, num)) {
                grid[row][col] = num;
                if (solveSudoku(grid, row, col + 1) != null)
                    return grid;
            }
            grid[row][col] = 0; //backtrack and try the next num
        }
        return null;
    }
    
    //print filled board
    public void printSolution() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                System.out.print(board[r][c]);
            }
            System.out.println();
        }
    }
    
    private boolean isValid(int row, int col, int num) {
        //check if num is already present in the row/column/subgrid
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num || board[row - row % 3 + i / 3][col - col % 3 + i % 3] == num) {
                return false;
            }
        }
        return true;
    }
}
