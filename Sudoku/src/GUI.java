import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GUI extends JFrame {

	private int level;
	private boolean r;
	private JButton currButton;
	private int[][] solvedBoard = new int[9][9];
	private int[][] grid = new int[9][9];
    private JTextField[][] sudokuCells;
    private Color textColor;
    private JComboBox<String> themeCB;
    private String selectedTheme;
    private HashMap<Integer, int[][]> fileBoard;
    private SolverLogic solver;
   	private HashMap<Integer, int[][]> rB = new HashMap<Integer, int[][]>();
   	int[][] w;
   	
   	SimpleAudioPlayer winnerMusic = new SimpleAudioPlayer("winnerMusic.wav", false);
   	SimpleAudioPlayer loserMusic = new SimpleAudioPlayer("loserMusic.wav", false);


    public GUI(HashMap<Integer, int[][]> eB, HashMap<Integer, int[][]> mB, HashMap<Integer, int[][]> hB) {
    	fileBoard = eB;
    	
    	//create 800x800 window
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        JPanel sudokuPanel = new JPanel(); //panel for 9x9 board
        sudokuPanel.setLayout(new GridLayout(3, 3)); //layout represents 3x3 subgrids
        sudokuCells = new JTextField[9][9];
        
        setBoard(sudokuPanel);
        
        JPanel topPanel = new JPanel(); //top panel
        //buttons for top
        JButton easyLevelButton = new JButton("Easy Level");
        JButton mediumLevelButton = new JButton("Medium Level");
        JButton hardLevelButton = new JButton("Hard Level");
        JButton randomLevelButton = new JButton("Random");
                
        //drop down stuff
        JLabel drop = new JLabel("Pick Theme:");
        drop.setVisible(true);
        topPanel.add(drop);
        String[] choices = { "basic","spring", "winter", "fall", "summer" };
        themeCB = new JComboBox<String>(choices);
        themeCB.setVisible(true);
        themeCB.addActionListener(new ThemeComboBoxListener());
        topPanel.add(themeCB);
        
        //add each button to top
        topPanel.add(easyLevelButton);
        topPanel.add(mediumLevelButton);
        topPanel.add(hardLevelButton);
        topPanel.add(randomLevelButton);
        
        JPanel bottemPanel = new JPanel(); //bottom panel
        //buttons for bottom
        JButton checkButton = new JButton("Check");
        JButton clearButton = new JButton("Clear Guesses");
        JButton hintButton = new JButton("Hint"); //added
        bottemPanel.add(checkButton);
        bottemPanel.add(clearButton);
        bottemPanel.add(hintButton); //added
        
        //put each panel together
        getContentPane().add(BorderLayout.CENTER, sudokuPanel);
        getContentPane().add(BorderLayout.NORTH, topPanel);
        getContentPane().add(BorderLayout.SOUTH, bottemPanel);
        
        currButton = easyLevelButton;
        easyLevelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mediumLevelButton.setBorder(null);
        hardLevelButton.setBorder(null);
        
        setTheme();
        
        isSolvedCorrectly(getUserInput());
        
        //code for what happens when each button is clicked
        easyLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//change currButton
            	currButton.setBorder(null);
            	currButton = easyLevelButton;
            	easyLevelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            	//update fileBoard for easy level
                level = 1;
                r = false;
                setFileBoard(eB, mB, hB, rB);
                setBoard(sudokuPanel);
                setTheme();
            }
        });

        mediumLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//change currButton
            	currButton.setBorder(null);
            	currButton = mediumLevelButton;
            	mediumLevelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            	//update fileBoard for medium level
            	level = 2;
            	r = false;
            	setFileBoard(eB, mB, hB, rB);
            	setBoard(sudokuPanel);
            	setTheme();
            }
        });

        hardLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//change currButton
            	currButton.setBorder(null);
            	currButton = hardLevelButton;
            	hardLevelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            	//update fileBoard for hard level
            	level = 3;
            	r = false;
            	setFileBoard(eB, mB, hB, rB);
            	setBoard(sudokuPanel);
            	setTheme();
            }
        });

        randomLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {        		
            	//update fileBoard for random board
            	r = true;
            	setFileBoard(eB, mB, hB, rB);
            	setBoard(sudokuPanel);
            	setTheme();
            }
        });

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check answer
            	//make curr board into 9x9
            	//call solver method
            	//compare curr w solved
            	// Retrieve user input
                int[][] userInput = getUserInput();
                
                // Solve Sudoku based on user input
                SolverLogic solver = new SolverLogic(userInput);
                if (solver.solveSudoku(userInput, 0, 0)) {
                    System.out.println("Sudoku solved!");
                    // Now the solved board is in userInput
                    // Compare userInput with the original board
                    if (isSolvedCorrectly(userInput)) {
                        System.out.println("Correct!");
                        winnerMusic.play();
                        JOptionPane.showMessageDialog(GUI.this, "Correct!", "Winner", JOptionPane.INFORMATION_MESSAGE);
                        clear(sudokuPanel);
                        r = true;
                    	setFileBoard(eB, mB, hB, rB);
                    	setBoard(sudokuPanel);
                    	setTheme();
                    } else {
                        System.out.println("Incorrect :(");
                        loserMusic.play();
                        JOptionPane.showMessageDialog(GUI.this, "Incorrect :(", "Try Again", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    System.out.println("Sudoku puzzle could not be solved.");
                    loserMusic.play();
                    JOptionPane.showMessageDialog(GUI.this, "Incorrect :(", "Try Again", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clear guesses
            	clear(sudokuPanel);
            }
        });
        
        //need to figure out how to add just one spot --> go through code maybe 
        //understand how it all works.  
        hintButton.addActionListener(new ActionListener() {
            @Override
            //fills in a random square which is empty and fills it with the number that goes in that spot
            public void actionPerformed(ActionEvent e) {
            	int row = (int)(Math.random()*9);
            	int col = (int)(Math.random()*9);
            	//check if the box is not empty then run until finds an empty box 
            	if (!(sudokuCells[row][col].getText().isEmpty())) {
            		while (!(sudokuCells[row][col].getText().isEmpty())) {
            			row = (int)(Math.random()*9);
            			col = (int)(Math.random()*9);
            		}
            		//updates grid 
            		grid[row][col] = solvedBoard[row][col];
            		sudokuCells[row][col].setText("" + solvedBoard[row][col]);
            	}
            }            
        });
        
    }
    
    public void setBoard(JPanel sudokuPanel) {
    	// Clear existing board
        sudokuPanel.removeAll();
        sudokuPanel.revalidate();
        sudokuPanel.repaint();
        
    	int[][] board = new int[9][9];
        
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

        //make each subgrid have 3x3 layout and add border
        for(int subgridRow = 0; subgridRow < 3; subgridRow++) {
            for(int subgridCol = 0; subgridCol < 3; subgridCol++) {
                JPanel subgridPanel = new JPanel(); // panel for each 3x3
                subgridPanel.setLayout(new GridLayout(3, 3));
                subgridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                //calculate the starting row and column index
                int rowStart = subgridRow * 3;
                int colStart = subgridCol * 3;

                //make each spot have user input and add border within the subgrid
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        JTextField textField = new JTextField(1);
                        textField.setHorizontalAlignment(JTextField.CENTER);
                        int rowIndex = rowStart + i;
                        int colIndex = colStart + j;
                        if(board[rowIndex][colIndex] != 0) {
                            //set prefilled numbers taken from board
                            textField.setText(String.valueOf(board[rowIndex][colIndex]));
                            textField.setEditable(false);
                        }
                        else {
                        	// Add document listener to capture user input and update the grid
                            textField.getDocument().addDocumentListener(new DocumentListener() {
                                @Override
                                public void insertUpdate(DocumentEvent e) {
                                    updateGrid(rowIndex, colIndex, textField);
                                }
                                @Override
                                public void removeUpdate(DocumentEvent e) {
                                    updateGrid(rowIndex, colIndex, textField);
                                }
                                @Override
                                public void changedUpdate(DocumentEvent e) {
                                    updateGrid(rowIndex, colIndex, textField);
                                }
                            });
                        }
                        //filter to allow only digits
                        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new IntFilter());
                        sudokuCells[rowIndex][colIndex] = textField;
                        subgridPanel.add(textField);
                        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    }
                }

                sudokuPanel.add(subgridPanel); // put 3x3 subgrids into 9x9 board
            }
        }
    }

	public JTextField[][] getSudokuCells() {
        return sudokuCells;
    }
    
    //filter to only allow ints
    class IntFilter extends DocumentFilter {

        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            //if the inserted string is null
            if(string == null){
            	return;
            }
            //if the inserted string contains only digits (0-9)
            if (fb.getDocument().getLength() + string.length() <= 1 && string.matches("\\d")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            //if the replacement text is null
            if (text == null){
            	return;
            }
            //if the replacement text contains only digits (0-9)
            if (fb.getDocument().getLength() - length + text.length() <= 1 && text.matches("\\d")) {
                //call the replace method of the superclass (DocumentFilter)
            	super.replace(fb, offset, length, text, attrs);
            }
        }
    }
    
    class ThemeComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setTheme();
        }
    }
    
    private void setTheme() {
    	selectedTheme = (String) themeCB.getSelectedItem();
        if (selectedTheme.equals("basic")) {
            // Change border and background color for Theme 1
            for (JTextField[] row : sudokuCells) {
                for (JTextField textField : row) {
                    textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    textColor = new Color(177, 123, 222);
                    textField.setBackground(Color.WHITE);
                }
            }
        } else if (selectedTheme.equals("spring")) {
            // Change border and background color for spring colors
            for (JTextField[] row : sudokuCells) {
                for (JTextField textField : row) {
                	Color bor = new Color(133, 178, 130);
                	textColor = bor;
                	Color bac = new Color(255, 219, 237);
                    textField.setBorder(BorderFactory.createLineBorder(bor));
                    textField.setBackground(bac);
                }
            }
        } else if (selectedTheme.equals("winter")) {
            // Change border and background color for winter colors
            for (JTextField[] row : sudokuCells) {
                for (JTextField textField : row) {
                	Color bor = new Color(31, 88, 153);
                	textColor = bor;
                	Color bac = new Color(171, 222, 247);
                    textField.setBorder(BorderFactory.createLineBorder(bor));
                    textField.setBackground(bac);
                }
            }
        } else if (selectedTheme.equals("fall")) {
            // Change border and background color for fall colors
            for (JTextField[] row : sudokuCells) {
                for (JTextField textField : row) {
                	Color bor = new Color(188, 57, 8);
                	textColor = bor;
                	Color bac = new Color(246, 170, 28);
                    textField.setBorder(BorderFactory.createLineBorder(bor));
                    textField.setBackground(bac);
                }
            }
        } else if (selectedTheme.equals("summer")) {
            // Change border and background color for summer colors
            for (JTextField[] row : sudokuCells) {
                for (JTextField textField : row) {
                	Color bor = new Color(61 ,165, 217);
                	textColor = bor;
                	Color bac = new Color(255, 229, 124);
                    textField.setBorder(BorderFactory.createLineBorder(bor));
                    textField.setBackground(bac);
                }
            }
        }
        updateTextColors();
    }
    
    // Method to update text color based on current theme
    private void updateTextColor(JTextField textField) {
        if (textField.isEditable()) { // Check if the text field is editable (user-inputted)
            textField.setForeground(textColor); // Set text color for user-inputted numbers
        } else {
            textField.setForeground(Color.BLACK); // Set text color for pre-inputted numbers
        }
    }
    
    // Method to update text color for each text field
    private void updateTextColors() {
        if (sudokuCells != null) {
            for (JTextField[] row : sudokuCells) {
                for (JTextField textField : row) {
                    updateTextColor(textField); // Update text color for each text field
                }
            }
        }
    }
    
    private void clear(JPanel sudokuPanel) {
        for (JTextField[] row : sudokuCells) {
            for (JTextField textField : row) {
                if (textField.isEditable()) {
                	 //get rid of filter that makes only ints allowed
                     AbstractDocument doc = (AbstractDocument) textField.getDocument();
                     doc.setDocumentFilter(null);
                     //clear textfield
                     textField.setText("");
                     //put filter back
                     doc.setDocumentFilter(new IntFilter());
                }
            }
        }
        sudokuPanel.repaint();
    }

    
    public void setFileBoard(HashMap<Integer, int[][]> eB, HashMap<Integer, int[][]> mB, HashMap<Integer, int[][]> hB, HashMap<Integer, int[][]> rB) {
    	if(r) {
    		ComputerGenBoard c = new ComputerGenBoard();
    		c.fillValues();
    		if(level==1) {
        		c.removespotsDigits(1);
        		rB = compGen(c.getBoard(), 1, 0, 0);
			}
			else if(level==2) {
				c.removespotsDigits(2);
        		rB = compGen(c.getBoard(), 1, 0, 0);
			}
			else{
				c.removespotsDigits(3);
        		rB = compGen(c.getBoard(), 1, 0, 0);
			}
    		this.fileBoard = rB;   	
    	}
    	else {
	    	if(level==1) {
				this.fileBoard = eB;
			}
			else if(level==2) {
				this.fileBoard = mB;
			}
			else{
				this.fileBoard = hB;
			}
    	}
    	
	}
    
 // Method to update the grid with user input
    private void updateGrid(int row, int col, JTextField textField) {
        String text = textField.getText();
        if (!text.isEmpty()) {
            int value = Integer.parseInt(text);
            grid[row][col] = value;
        } else {
            grid[row][col] = 0; // Reset to 0 if input is empty
        }
    }
   
    private int[][] getUserInput() {
        int[][] userInput = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = sudokuCells[i][j].getText();
                if (!text.isEmpty()) {
                    userInput[i][j] = Integer.parseInt(text);
                } else {
                    userInput[i][j] = 0;
                }
            }
        }
        return userInput;
    }
   
    //compare solved board with user input
    private boolean isSolvedCorrectly(int[][] sB) {
    	solvedBoard = sB;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuCells[i][j].getText().isEmpty() || Integer.parseInt(sudokuCells[i][j].getText()) != sB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
 	
 	//fill hashMap with computer generated board recursively 
    public HashMap<Integer, int[][]> compGen(int[][] b, int count, int row, int col) {
    	//updates count so it doesn't run forever
        if (count > 9) {
            return rB;
        }
        
        // make variables
        int[][] w = new int[3][3];
        int a = 0;
        int q = 0;

        //runs and updates w (3x3 square) with corresponding values from board
        for(int r = row; r < row + 3; r++) {
            for (int c = col; c < col + 3; c++) {
                w[a][q] = b[r][c];
                q++;
            }
            //update variables
            a++;
            q = 0;
        }
        
        //put the 3x3 array into the hashMap
        rB.put(count, w);

        //recursively returns the values based on where they are in overall board 
        //fills in from left to right then goes down a block 
        if (col != 6) {
            return compGen(b, ++count, row, col + 3);
        } else if (row != 6) {
            return compGen(b, ++count, row + 3, 0);
        } else {
            return rB;
        }
    }

    
}