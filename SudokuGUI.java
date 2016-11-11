
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.io.IOException;

import org.omg.Messaging.SyncScopeHelper;

import java.lang.Math;

public class SudokuGUI extends JFrame{
	final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
	static int n = dimention();

	// GUI
	static JTextField[][] textFields =  new JTextField[n][n];
	static JButton solve;
    static JButton clear;

	// Storage Data
	static ArrayList<String> userInput = new ArrayList<String>();
	static ArrayList<String> minisatResult = new ArrayList<String>();
	String result;

	// Brain
	static Sudoku process = new Sudoku();

	public SudokuGUI() {
		super("Sudoku " + n + "x" + n);
    	this.setSize(600,400);
    	// this.setResizable(true); // sebenernya udah by default
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	SudokuGUI.addComponentsToPane(this.getContentPane());
        this.buttonHandler();

    	//JButton check = new JButton("Check");
    	//check.setPreferredSize(new Dimension(40, 40));
    	//check.addActionListener(this);
    	//add(check);

    	
    }

    public static void infoBox(String infoMsg, String title) {
    	JOptionPane.showMessageDialog(null,  infoMsg, "InfoBox: " + title, JOptionPane.INFORMATION_MESSAGE);
    }

   	// Meminta dimensi yang diinginkan
	public static int dimention() {
		n = Integer.parseInt(JOptionPane.showInputDialog("Sudoku's dimention:"));
		int nTemp = (int) Math.sqrt((double)n);
		nTemp = nTemp * nTemp;

		while(nTemp != n || nTemp == 4 || nTemp == 1) {
			n = Integer.parseInt(JOptionPane.showInputDialog("Please Enter the Right Sudoku's dimention:"));
			nTemp = (int) Math.sqrt((double)n);
			nTemp = nTemp * nTemp;
		}
		return n;

	}

	public static void addComponentsToPane(Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }
 
    pane.setLayout(new GridLayout(n+1,n+1));
    GridBagConstraints c = new GridBagConstraints();
  
    for (int i = 0; i < n; i++) {
    	for (int j = 0; j < n; j++) {
    		textFields[i][j] = new JTextField(j);
    		pane.add(textFields[i][j]);
    		//c.fill = GridBagConstraints.NONE;
    		//c.gridx = j;
    		//c.gridy = i;
    		//pane.add(textFields[i][j], c);
    	}
    	
    }
    
    
    solve = new JButton("Solve");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = GridBagConstraints.EAST;//top padding
    c.gridy = GridBagConstraints.EAST;       //third row
    pane.add(solve, c);
    
    clear = new JButton("Clear");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = GridBagConstraints.EAST;//top padding
    c.gridy = GridBagConstraints.EAST;       //third row
    pane.add(clear, c);
    }
    
    public void buttonHandler() {
    	clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for (int i = 0; i < n; i++) {
			    	for (int j = 0; j < n; j++) {
			    		textFields[i][j].setText("");
			    	}
			    	
			    }

			    // vitri
			    minisatResult.clear();
			    userInput.clear();
			    process.hmresult.clear();
			    process.hm.clear();

			    //System.out.println(minisatResult.size());

			}
    		
    	});
    	
    	solve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int p;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
					  
					  if(!textFields[i][j].getText().isEmpty()) {
						  
						  if(textFields[i][j].getText().matches("[a-zA-z]{1}")) {
							  SudokuGUI.infoBox("Sorry, input must be integer between 1 to " + n, "Wrong input");
							  return;
						  }
						  
						  p = Integer.parseInt(textFields[i][j].getText());
						  if(p>n || p<1) {
							  SudokuGUI.infoBox("Sorry, your input must be between 1 to " + n, "Wrong input");
							  return;
						  }
					    result = (i+1) + "," + (j+1) + "," + textFields[i][j].getText();
					    userInput.add(result);
						
					  }
				    }
			    }
				try {
					process.carisolusi(n, userInput, minisatResult); // vitri
					for(int i =0; i< minisatResult.size();i++){
						System.out.println(minisatResult.get(i));
					}
					for(int i =0; i< minisatResult.size();i++){
				    	String tmp = minisatResult.get(i);
				    	
				    	// menampilkan text field
				    	String[] splitOrdinat = tmp.split(",");
				    	int x = Integer.parseInt(splitOrdinat[0]) -1;
				    	int y = Integer.parseInt(splitOrdinat[1]) -1;
				    	String value = splitOrdinat[2];
				    	textFields[x][y].setText(value);
				    }
				    minisatResult.clear();
					
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// nyambung ke minisat
				
				//baca minisat
				
			}
    	});
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
    	EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
            	SudokuGUI t = new SudokuGUI();
                t.setVisible(true);
            }
         });
    }
}