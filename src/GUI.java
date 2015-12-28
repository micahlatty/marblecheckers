import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The view for this project.
 * @author micahlatty
 *
 */
public class GUI extends JFrame implements ActionListener {
	
	/** The game */
	Game game;
	
	/** A panel for the game board */
	JPanel boardP;
	
	/** An array of buttons, corresponding to each Spot on the Console game board */
	JButton [][] board;
	
	/** A boolean to keep track of whether a click is a first or second selection */
	boolean firstClick;
	
	/** The row from which the user wants to move */
	int rowFrom;
	
	/** The row to which the user wants to move */
	int rowTo;
	
	/** The column from which the user wants to move */
	int columnFrom;
	
	/** The column to which the user wants to move */
	int columnTo;
	
	/** A button to reset the game */
	JButton resetB;
	
	/** A text field to present errors to the user (along with a notification if the game is won or lost) */
	JTextField errorTF;
	
	/** 
	 * Constructor
	 */
	public GUI() {
		setTitle("Marble Checkers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game = new Game();
		
		JPanel mainPanel = new JPanel();
		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		setPreferredSize(new Dimension(800, 400));
		
		boardP = new JPanel();
		boardP.setLayout(new GridLayout(7, 7));
		mainPanel.add(boardP, BorderLayout.CENTER);
		
		board = new JButton [7][7];
		
		for ( int i = 0; i < board.length; i++ ) {
			for ( int j = 0; j < board[0].length; j++ ) {
				JPanel panel = new JPanel();
				board[i][j] = new JButton();
				panel.add(board[i][j]);
				board[i][j].addActionListener(this);
				board[i][j].setOpaque(true);
				board[i][j].setBorderPainted(false); 
	
				boardP.add(panel);
			}
		}
		setColors();
		
		// Set up settings panel (with reset option and error/won-lost textfield)
		JPanel settingsP = new JPanel();
		resetB = new JButton("Reset game!");
		resetB.addActionListener(this);
		errorTF = new JTextField("", 50);
		settingsP.add(errorTF);
		settingsP.add(resetB);
		mainPanel.add(settingsP, BorderLayout.SOUTH);
		
		// Initialize firstClick boolean, along with rowFrom, ColumnFrom, etc., ints
		firstClick = true;
		rowFrom = -1;
		rowTo = -1;
		columnFrom = -1;
		columnTo = -1;
		
		
		pack();
		setVisible(true);
	}

	/** Method to handle action events (the controller) */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Reset game
		if ( e.getSource().equals(resetB) ) {
			game.resetBoard();
			setColors();
		}
		
		// Make move based on two consecutive button selections
		for ( int i = 0; i < board.length; i++ ) {
			for ( int j = 0; j < board[0].length; j++ ) {
				if ( e.getSource() == board[i][j] ) {
					if ( firstClick ) {
						errorTF.setText("");
						board[i][j].setBackground(Color.YELLOW);
						rowFrom = i;
						columnFrom = j;
						firstClick = false;
					} else {
						rowTo = i;
						columnTo = j;
						firstClick = true;
						try {
							game.move(rowFrom, columnFrom, rowTo, columnTo);
							setColors();
							// Check whether game won or lost on move
							if ( !game.movesLeft() ) {
								if ( game.won() )
									errorTF.setText("You win!");
								else
									errorTF.setText("Oops you lose!");
							}
						} catch (Exception e1) {
							errorTF.setText("Error: " + e1);
							setColors();
						}
					}
				}
			}
		}		
	}
	
	/**
	 * Sets the colors of the GUI board to match the status of the spots on the game board
	 */
	public void setColors() {
		for ( int i = 0; i < game.getBoard().length; i++ ) {
			for ( int j = 0; j < game.getBoard()[0].length; j++ ) {
				if ( game.getBoard()[i][j].getStatus() == Spot.Status.E ) {
					board[i][j].setBackground(Color.BLUE);
				} else if ( game.getBoard()[i][j].getStatus() == Spot.Status.I ) {
					board[i][j].setBackground(Color.BLACK);
				} else {
					board[i][j].setBackground(Color.RED);
				}
			}
		}
	}

	/** The main */
	public static void main(String [] args) {
		GUI newGUI = new GUI();
	}
}

