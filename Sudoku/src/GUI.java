import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GUI extends JFrame {

	public int level;
    private JTextField[][] sudokuCells;
    private JComboBox<String> themeCB;
    private HashMap<Integer, int[][]> fileBoard;

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
        
//        int[][] board = new int[9][9];
//        
//        //iterate through each 3x3 array in fileBoard
//        for (Map.Entry<Integer, int[][]> entry : fileBoard.entrySet()) {
//            int key = entry.getKey();
//            int[][] threeByThree = entry.getValue();
//
//            //calculate starting row and column in the 9x9 grid based on the key
//            int rowStart = ((key-1) / 3) * 3;
//            int colStart = ((key-1) % 3) * 3;
//
//            //place the 3x3 array into the 9x9 grid at the calculated starting position
//            for (int i = 0; i < 3; i++) {
//                for (int j = 0; j < 3; j++) {
//                    if (rowStart + i < 9 && colStart + j < 9) {
//                        board[rowStart + i][colStart + j] = threeByThree[i][j];
//                    }
//                }
//            }
//        }
//
//        //make each subgrid have 3x3 layout and add border
//        for(int subgridRow = 0; subgridRow < 3; subgridRow++) {
//            for(int subgridCol = 0; subgridCol < 3; subgridCol++) {
//                JPanel subgridPanel = new JPanel(); // panel for each 3x3
//                subgridPanel.setLayout(new GridLayout(3, 3));
//                subgridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//
//                //calculate the starting row and column index
//                int rowStart = subgridRow * 3;
//                int colStart = subgridCol * 3;
//
//                //make each spot have user input and add border within the subgrid
//                for(int i = 0; i < 3; i++) {
//                    for(int j = 0; j < 3; j++) {
//                        JTextField textField = new JTextField(1);
//                        textField.setHorizontalAlignment(JTextField.CENTER);
//                        int rowIndex = rowStart + i;
//                        int colIndex = colStart + j;
//                        if(board[rowIndex][colIndex] != 0) {
//                            //set prefilled numbers taken from board
//                            textField.setText(String.valueOf(board[rowIndex][colIndex]));
//                            textField.setEditable(false);
//                        }
//                        //filter to allow only digits
//                        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new IntFilter());
//                        sudokuCells[rowIndex][colIndex] = textField;
//                        subgridPanel.add(textField);
//                        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//                    }
//                }
//
//                sudokuPanel.add(subgridPanel); // put 3x3 subgrids into 9x9 board
//            }
//        }
        
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
        bottemPanel.add(checkButton);
        bottemPanel.add(clearButton);
        
        //put each panel together
        getContentPane().add(BorderLayout.CENTER, sudokuPanel);
        getContentPane().add(BorderLayout.NORTH, topPanel);
        getContentPane().add(BorderLayout.SOUTH, bottemPanel);
        
        //code for what happens when each button is clicked
        easyLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level = 1;
                setFileBoard(eB, mB, hB); // Update fileBoard for easy level
                setBoard(sudokuPanel);
            }
        });

        mediumLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	level = 2;
            	setFileBoard(eB, mB, hB);
            	setBoard(sudokuPanel);
            }
        });

        hardLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	level = 3;
            	setFileBoard(eB, mB, hB);
            	setBoard(sudokuPanel);
            }
        });

        randomLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //generate random board
            }
        });

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check answer
            	//make curr board into 9x9
            	//call solver method
            	//compare curr w solved
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //clear guesses
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
            if(string.matches("\\d")){
                //call the insertString method of the superclass (DocumentFilter)
                super.insertString(fb, offset, string, attr);
            }
        }

        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            //if the replacement text is null
            if (text == null){
            	return;
            }
            //if the replacement text contains only digits (0-9)
            if (text.matches("\\d")) {
                //call the replace method of the superclass (DocumentFilter)
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
    
    class ThemeComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedTheme = (String) themeCB.getSelectedItem();
            if (selectedTheme.equals("basic")) {
                // Change border and background color for Theme 1
                for (JTextField[] row : sudokuCells) {
                    for (JTextField textField : row) {
                        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        textField.setBackground(Color.WHITE);
                    }
                }
            } else if (selectedTheme.equals("spring")) {
                // Change border and background color for spring colors
                for (JTextField[] row : sudokuCells) {
                    for (JTextField textField : row) {
                    	Color bor = new Color(131, 204, 173);
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
                    	Color bac = new Color(171, 222, 247);
                        textField.setBorder(BorderFactory.createLineBorder(bor));
                        textField.setBackground(bac);
                    }
                }
            } else if (selectedTheme.equals("fall")) {
                // Change border and background color for fall colors
                for (JTextField[] row : sudokuCells) {
                    for (JTextField textField : row) {
                    	Color bor = new Color(125, 11, 11);
                    	Color bac = new Color(236, 106, 49);
                        textField.setBorder(BorderFactory.createLineBorder(bor));
                        textField.setBackground(bac);
                    }
                }
            } else if (selectedTheme.equals("summer")) {
                // Change border and background color for summer colors
                for (JTextField[] row : sudokuCells) {
                    for (JTextField textField : row) {
                    	Color bor = new Color(245, 230, 153);
                    	Color bac = new Color(121, 248, 252);
                        textField.setBorder(BorderFactory.createLineBorder(bor));
                        textField.setBackground(bac);
                    }
                }
            }
        }
    }
    
    
    
    public void setFileBoard(HashMap<Integer, int[][]> eB, HashMap<Integer, int[][]> mB, HashMap<Integer, int[][]> hB) {
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