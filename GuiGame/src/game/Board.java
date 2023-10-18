package game;
import java.lang.Math;

public class Board {
	private static boolean[][] board;
	private static int size;
	private static int currentLevel;
	private static int totalLevels;
	private static int moves;
	
	
	//constructors
	public Board() {
		size = 5;
		board  = new boolean[size][size];
		totalLevels = 5;
		currentLevel = 0;
		moves = 0;
		
		fillBoard();
		nextLevel();
		
	}
	
	//setters
	private static void fillBoard() {
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				board[row][col] = true;
			}
		}
		
	}
	
	public static void updateBoard(int row, int col) {
		moves ++;
		
		if(row == 0 && col == 0) {
			//top left corner
			
			//same spot
			board[row][col] = !board[row][col];
			//down
			board[row+1][col] = !board[row+1][col];
			//right
			board[row][col+1] = !board[row][col+1];
			
		}else if(row == 0 && col == size -1) {
			//top right corner
			
			//same spot
			board[row][col] = !board[row][col];
			//down
			board[row+1][col] = !board[row+1][col];
			//left
			board[row][col-1] = !board[row][col-1];
			
		}else if (row == size - 1 && col == 0) {
			//bottom left corner
			
			//same spot
			board[row][col] = !board[row][col];
			//up
			board[row-1][col] = !board[row-1][col];
			//right
			board[row][col+1] = !board[row][col+1];
			
		}else if(row == size - 1 && col == size - 1) {
			//bottom right corner
			
			//same spot
			board[row][col] = !board[row][col];
			//up
			board[row-1][col] = !board[row-1][col];
			//left
			board[row][col-1] = !board[row][col-1];
		
		
		}else if(row == 0) {
			//top row
			
			//same spot
			board[row][col] = !board[row][col];
			//down
			board[row+1][col] = !board[row+1][col];
			//left
			board[row][col-1] = !board[row][col-1];
			//right
			board[row][col+1] = !board[row][col+1];
		}else if(row == size - 1) {
			//bottom row
			
			//same spot
			board[row][col] = !board[row][col];
			//up
			board[row-1][col] = !board[row-1][col];
			//left
			board[row][col-1] = !board[row][col-1];
			//right
			board[row][col+1] = !board[row][col+1];

		}else if(col == 0) {
			//left col
			
			//same spot
			board[row][col] = !board[row][col];
			//up
			board[row-1][col] = !board[row-1][col];
			//down
			board[row+1][col] = !board[row+1][col];
			//right
			board[row][col+1] = !board[row][col+1];
		}else if (col == size -1) {
			//right col
			
			//same spot
			board[row][col] = !board[row][col];
			//up
			board[row-1][col] = !board[row-1][col];
			//down
			board[row+1][col] = !board[row+1][col];
			//left
			board[row][col-1] = !board[row][col-1];
		}else {
			//not on a side or corner
			
			//same spot
			board[row][col] = !board[row][col];
			//up
			board[row-1][col] = !board[row-1][col];
			//down
			board[row+1][col] = !board[row+1][col];
			//left
			board[row][col-1] = !board[row][col-1];
			//right
			board[row][col+1] = !board[row][col+1];
			
		}
		if(allOut())
			nextLevel();
	}

	//getters
	public boolean[][] getBoard(){
		return board;
	}
	
	private static boolean allOut() {
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				if(!board[row][col])
					return false;
			}
		}
		moves = 0;
		return true;
	}
	
	//used to print board to console in console test
	public void printBoard() {
		System.out.println("Moves: "+ moves);
		for(int row = 0; row < size; row++) {
			System.out.println();
			for(int col = 0; col < size; col++) {
				if(board[row][col])
					System.out.print("- ");
				else
					System.out.print("o ");
			}
		}
		System.out.println();
	}
	
	private static void nextLevel() {
		currentLevel++;
		
		if(currentLevel <= totalLevels) {
			//pre-set levels
			if(currentLevel == 1){
				//level 1
				board[0][1] = false;
				board[1][0] = false;
				board[1][1] = false;
				board[1][2] = false;
				board[2][1] = false;
				
				board[2][3] = false;
				board[3][2] = false;
				board[3][3] = false;
				board[3][4] = false;
				board[4][3] = false;
				
			}else if(currentLevel == 2) {
				//level 2
				board[3][2] = false;
				board[3][3] = false;
				board[3][4] = false;
				board[4][3] = false;
				
				board[4][1] = false;
			}else if(currentLevel == 3) {
				//level 3
				board[2][0] = false;
				
				board[2][2] = false;
				
				board[2][4] = false;
			}else if(currentLevel == 4) {
				//level 4
				board[0][0] = false;
				board[1][0] = false;
				
				board[0][2] = false;
				board[1][2] = false;
				
				board[0][4] = false;
				board[1][4] = false;
				
				board[3][0] = false;
				board[4][0] = false;

				board[3][2] = false;
				board[4][2] = false;
				
				board[3][4] = false;
				board[4][4] = false;
			}else if(currentLevel == 5) {
				//level 5
				board[0][1] = false;
				board[1][1] = false;
				board[1][0] = false;
				board[2][0] = false;
				board[2][1] = false;
				board[3][0] = false;
				board[3][1] = false;
				board[4][1] = false;
				
				board[0][3] = false;
				board[1][3] = false;
				board[1][4] = false;
				board[2][3] = false;
				board[2][4] = false;
				board[3][3] = false;
				board[3][4] = false;
				board[4][3] = false;
				
			}
		}else {
			//randomizes board after pre-set levels are completed
			for(int i = 0; i < 100; i++) {
				int randRow = (int)(Math.random()*size);
				int randCol = (int)(Math.random()*size);
				updateBoard(randRow,randCol);
				}
			moves = 0;
		}
	}

	protected int getLevel() {
		return currentLevel;
	}
	
	protected int getMoves() {
		return moves;
	}
}