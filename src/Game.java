/**
 * The model for the marble checkers game
 * @author micahlatty
 * @version 12/27/15
 *
 */
public class Game {
	
	/** The gameboard */
	private Spot [][] board = new Spot [7][7];

	/** The number of marbles */
	private int numberOfMarbles = 0;
	
	/** 
	 * Constructor 
	 */
	public Game() {
		for ( int i = 0; i < board.length; i++ ) {
			for ( int j = 0; j < board[0].length; j++ ) {
				board[i][j] = new Spot();
			}
		}
		resetBoard();
	}
	
	/** 
	 * Reset board to original state
	 */
	public void resetBoard() {
		for ( int i = 0; i < board.length; i++ ) {
			for ( int j = 0; j < board[0].length; j++ ) {
				if ( ( i > 1 && i < 5 ) || ( j > 1 && j < 5 ) )
					board[i][j].setStatus(Spot.Status.O);
				else
					board[i][j].setStatus(Spot.Status.I);
			}
		}
		board[3][3].setStatus(Spot.Status.E);
		numberOfMarbles = 32;
	}
	
	/**
	 * Make a move
	 * @throws Exception if move is not legal
	 */
	public void move(int rowFrom, int columnFrom, int rowTo, int columnTo) throws Exception {
		try {
			moveLegal(rowFrom, columnFrom, rowTo, columnTo);
			board[ rowFrom ][ columnFrom ].setStatus(Spot.Status.E);
			board[ rowTo ][ columnTo ].setStatus(Spot.Status.O);
			board[ (rowFrom + rowTo) / 2 ][ (columnFrom + columnTo) / 2 ].setStatus(Spot.Status.E);
			numberOfMarbles--;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Method to check whether a given move is legal
	 * @throws Exception for various illegal moves
	 */
	public void moveLegal(int rowFrom, int columnFrom, int rowTo, int columnTo) throws Exception {
		if ( board[rowFrom][columnFrom].getStatus() != Spot.Status.O ) 
			throw new Exception("The spot you wish to move from doesn't contain a marble.");
		else if ( board[rowTo][columnTo].getStatus() != Spot.Status.E )
			throw new Exception("You can only move to spots that are both valid and vacant.");
		else if ( !( ( Math.abs(rowTo - rowFrom) == 2 && columnFrom == columnTo ) 
				|| ( Math.abs(columnTo - columnFrom) == 2 && rowFrom == rowTo ) ) )
			throw new Exception("You can only move two places vertically or horizontally.");
		
		Exception noMarbleException = new Exception("You can only move by jumping over another marble.");
		if ( board[ (rowFrom + rowTo) / 2 ][ (columnFrom + columnTo) / 2 ].getStatus() != Spot.Status.O ) {
			throw noMarbleException;
		}
	}
	
	/**
	 * Method to check whether there are any legal moves left
	 * @return true if there are moves left, false otherwise
	 */
	public boolean movesLeft() {
		for ( int i = 0; i < board.length; i++ ) {
			for ( int j = 0; j < board[0].length - 2; j++ ) {
				Spot [] threeInARow = { board[i][j], board[i][j + 1], board[i][j + 2] };
				if ( ( threeInARow[0].getStatus().equals(Spot.Status.O) 
						&& threeInARow[1].getStatus().equals(Spot.Status.O) 
						&& threeInARow[2].getStatus().equals(Spot.Status.E) )
						|| ( threeInARow[0].getStatus().equals(Spot.Status.E) 
								&& threeInARow[1].getStatus().equals(Spot.Status.O) 
								&& threeInARow[2].getStatus().equals(Spot.Status.O) ) ) {
					return true;
				}
			}
		}
		for ( int i = 0; i < board.length - 2; i++ ) {
			for ( int j = 0; j < board[0].length; j++ ) {
				Spot [] threeInARow = { board[i][j], board[i + 1][j], board[i + 2][j] };
				if ( ( threeInARow[0].getStatus().equals(Spot.Status.O) 
						&& threeInARow[1].getStatus().equals(Spot.Status.O) 
						&& threeInARow[2].getStatus().equals(Spot.Status.E) )
						|| ( threeInARow[0].getStatus().equals(Spot.Status.E) 
								&& threeInARow[1].getStatus().equals(Spot.Status.O) 
								&& threeInARow[2].getStatus().equals(Spot.Status.O) ) ) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method to determine result of game
	 * @return true is user has won, false otherwise
	 */
	public boolean won() {
		if ( numberOfMarbles == 1 ) {
			return true;
		}
		return false;
	}
	
	/** Getter for board */
	public Spot[][] getBoard() {
		return board;
	}

	/** Setter for board */
	public void setBoard(Spot[][] board) {
		this.board = board;
	}
}



