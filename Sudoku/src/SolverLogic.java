
public class SolverLogic {
	private int[][] board;
	
	public SolverLogic(int[][] initialBoard) {
        board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = initialBoard[i][j];
            }
        }
    }
	
	//backtracking algorithm
	public int[][] solveSudoku(int grid[][], int row, int col){
		
	}
	
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
