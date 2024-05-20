import java.util.Arrays;

public class SolverLogic {
    private int[][] board;
    private int[][] solvedBoard;

    public SolverLogic(int[][] userInput) {
        // Initialize a new 9x9 board with a deep copy of userInput
        this.board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            this.board[i] = Arrays.copyOf(userInput[i], 9);
        }

        System.out.println("Initial Board:");
        printSudoku(this.board);

        System.out.println("Solving...");

        // Solve the Sudoku
        if (solveSudoku(this.board, 0, 0)) {
            System.out.println("Sudoku solved!");
            this.solvedBoard = new int[9][9];
            for (int i = 0; i < 9; i++) {
                this.solvedBoard[i] = Arrays.copyOf(this.board[i], 9);
            }
        } else {
            System.out.println("Sudoku puzzle could not be solved.");
        }

        System.out.println("Filled Board:");
        printSudoku(this.board);
    }

    // Backtracking algorithm
    public boolean solveSudoku(int[][] board, int row, int col) {
        // Filled board
        if (row == 9) {
            return true;
        }

        // If we have reached the end of a row, move to the next row
        if (col == 9) {
            return solveSudoku(board, row + 1, 0);
        }

        // If the cell is already filled, move to the next cell
        if (board[row][col] != 0) {
            return solveSudoku(board, row, col + 1);
        }

        for (int num = 1; num <= 9; num++) {
            if (isValid(board, row, col, num)) {
                // If the number is valid, place it in the cell
                board[row][col] = num;
                // Recursively solve for the next cell
                if (solveSudoku(board, row, col + 1)) {
                    return true;
                }
                // If the solution fails, backtrack and try the next number
                board[row][col] = 0;
            }
        }
        return false;
    }

    // Print filled board
    private void printSudoku(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isValid(int[][] board, int row, int col, int num) {
        // Check if num is already present in the row/column/subgrid
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

    // Method to get the solved board
    public int[][] getSolvedBoard() {
        return solvedBoard;
    }
}
