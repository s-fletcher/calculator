package me.samfletcher;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class GUI
{

    private JFrame frmCalculator;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    GUI window = new GUI();
                    window.frmCalculator.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
    
    /**
     * Create the application.
     */
    public GUI()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    @SuppressWarnings("serial")
    private void initialize()
    {
        // Sets up window
        frmCalculator = new JFrame();
        frmCalculator.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
        frmCalculator.setTitle("Calculator");
        frmCalculator.setResizable(false);
        frmCalculator.setBounds(100, 100, 450, 300);
        frmCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Creates new panel
        JPanel panel = new JPanel();
        frmCalculator.getContentPane().add(panel, BorderLayout.CENTER);
        
        // Creates label telling user to enter a math problem
        JLabel lblEnterAMath = new JLabel("Enter a math problem:");
        lblEnterAMath.setBounds(10, 124, 135, 20);
        
        // Creates text box for user to input math problem
        JTextPane textPane = new JTextPane();
        textPane.setBounds(142, 124, 195, 20);
        panel.setLayout(null);
        panel.add(lblEnterAMath);
        panel.add(textPane);
        
        // Creates label showing user the supported Operators
        JLabel lblSupportedOperators = new JLabel("Supported Operators: " + new String(Calculator.operators));
        lblSupportedOperators.setHorizontalAlignment(SwingConstants.CENTER);
        lblSupportedOperators.setBounds(10, 167, 414, 14);
        panel.add(lblSupportedOperators);
        
        // Creates label for my trademark
        JLabel lblApplicationCreatedBy = new JLabel("application created by Sam Fletcher");
        lblApplicationCreatedBy.setFont(new Font("Tahoma", Font.PLAIN, 8));
        lblApplicationCreatedBy.setBounds(10, 234, 182, 14);
        panel.add(lblApplicationCreatedBy);
        
        // Creates label to show the solution text
        JLabel lblSolution = new JLabel("SOLUTION");
        lblSolution.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblSolution.setBounds(10, 55, 114, 23);
        panel.add(lblSolution);
        
        // Creates label that will be replaced with the actual solution after calculation
        JLabel lblSolutionHere = new JLabel("=");
        lblSolutionHere.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblSolutionHere.setBounds(124, 55, 300, 23);
        panel.add(lblSolutionHere);
        
        // Label for recent equations
        JLabel lblEquation = new JLabel("");
        lblEquation.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEquation.setBounds(10, 30, 414, 14);
        panel.add(lblEquation);
        
        // Automatically sets text pane to be focused
        int condition = JComponent.WHEN_FOCUSED;
        InputMap iMap = textPane.getInputMap(condition);
        ActionMap aMap = textPane.getActionMap();

        // Setting up to get user's input for enter
        String enter = "enter";
        iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enter);
        aMap.put(enter, new AbstractAction() {

            /**
             * Enter has been pressed
             */
           @Override
           public void actionPerformed(ActionEvent arg0) {
               // Calls calculation method to begin process
               calculated(textPane, lblSolutionHere, lblEquation);
           }
        });
        
        // Button that is pressed to calculate
        JButton btnNewButton = new JButton("Calculate");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnNewButton.setBounds(344, 123, 80, 23);
        panel.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            
            /**
             * Calculate button has been pressed
             */
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Calls calculation method to begin process
                calculated(textPane, lblSolutionHere, lblEquation);
            }
        });
        
    }
    
    /**
     * Display's output for user
     */
    private void calculated(JTextPane textPane, JLabel label, JLabel lblEquation) {
        lblEquation.setText(textPane.getText());
        label.setText(Calculator.calculate(textPane.getText()));
        textPane.setText("");
        textPane.grabFocus();
    }
}
