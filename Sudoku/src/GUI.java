import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private JTextField[][] sudokuCells;

    public GUI() {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        JPanel sudokuPanel = new JPanel();
        sudokuPanel.setLayout(new GridLayout(9, 9));
        sudokuCells = new JTextField[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField textField = new JTextField(1);
                textField.setHorizontalAlignment(JTextField.CENTER);
                sudokuCells[i][j] = textField;
                sudokuPanel.add(textField);
            }
        }

        JPanel buttonPanel = new JPanel();
        JButton solveButton = new JButton("Solve");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);

        getContentPane().add(BorderLayout.CENTER, sudokuPanel);
        getContentPane().add(BorderLayout.SOUTH, buttonPanel);
    }

    public JTextField[][] getSudokuCells() {
        return sudokuCells;
    }
}