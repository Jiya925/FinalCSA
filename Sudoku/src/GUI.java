import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class GUI extends JFrame {

    private JTextField[][] sudokuCells;

    public GUI() {
    	//create 800x800 window
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        JPanel sudokuPanel = new JPanel(); //panel for 9x9 board
        sudokuPanel.setLayout(new GridLayout(3, 3)); //layout represents 3x3 subgrids
        sudokuCells = new JTextField[9][9];

        //make each subgrid have 3x3 layout and add border
        for (int i = 0; i < 9; i++) {
            JPanel subgridPanel = new JPanel(); //panel for each 3x3
            subgridPanel.setLayout(new GridLayout(3, 3));
            subgridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

          //make each spot have user input and add border
            for (int j = 0; j < 9; j++) {
                JTextField textField = new JTextField(1);
                textField.setHorizontalAlignment(JTextField.CENTER);
                //set document filter to allow only digits
                ((AbstractDocument) textField.getDocument()).setDocumentFilter(new IntFilter());
                sudokuCells[i][j] = textField;
                subgridPanel.add(textField);
                textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }

            sudokuPanel.add(subgridPanel); //put 3x3 subgrids into 9x9 board
        }
        
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
        String[] choices = { "THEME 1","THEME 2", "THEME 3"};
        final JComboBox<String> cb = new JComboBox<String>(choices);
        cb.setVisible(true);
        topPanel.add(cb);
        
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
}