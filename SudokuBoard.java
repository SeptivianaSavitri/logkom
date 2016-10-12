import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.omg.Messaging.SyncScopeHelper;
 
public class SudokuBoard extends JFrame {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    static int n = 9;
    static ArrayList<String> inputs = new ArrayList<String>();
    static ArrayList<JTextField> texts = new ArrayList<JTextField>();
    static ArrayList<String> coordinateX = new ArrayList<String>();
    static ArrayList<String> coordinateY = new ArrayList<String>();
    static JTextField[][] textfields = new JTextField[n][n];
    static JButton button;
    static JTextField txt;
    static int counter = 0;
    String result;
    
    public SudokuBoard() {
    	this.setSize(600,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SudokuBoard.addComponentsToPane(this.getContentPane());
        this.buttonHandler();
    }
    
    public SudokuBoard(Container pane) {
    	this.setSize(300,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SudokuBoard.addComponentsToPane(pane);
        this.buttonHandler();
    }
    
    public static void addComponentsToPane(Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
 
    pane.setLayout(new GridLayout(n+1,n+1));
    GridBagConstraints c = new GridBagConstraints();
  
    for (int i = 0; i < n; i++) {
    	for (int j = 0; j < n; j++) {
    		textfields[i][j] = new JTextField(j);
    		pane.add(textfields[i][j]);
    		//c.fill = GridBagConstraints.NONE;
    		//c.gridx = j;
    		//c.gridy = i;
    		//pane.add(textfields[i][j], c);
    	}
    	
    }
    
    button = new JButton("Solve");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = GridBagConstraints.EAST;//top padding
    c.gridy = GridBagConstraints.EAST;       //third row
    pane.add(button, c);
    }
    
    public void buttonHandler() {
    	button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int p;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
					  
					  if(!textfields[i][j].getText().isEmpty()) {
						  p = Integer.parseInt(textfields[i][j].getText());
						  if(p>9 && p<1) {
							  SudokuBoard boardBaru = new SudokuBoard(new Container());
							  JOptionPane.showMessageDialog(boardBaru, "Eggs are not supposed to be green.");
							  boardBaru.setVisible(true);
						  }
					    result = (i+1) + " " + (j+1) + " " + textfields[i][j].getText();
						System.out.println(result);
					  }
				    }
			    }
				
				// nyambung ke minisat
				
				//baca minisat
				
			}
    	});
    }
    

    /**JTextField text = new JTextField(1);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipady = 0;       //reset to default
    c.weighty = 1.0;   //request any extra vertical space
    //bottom of space
    c.insets = new Insets(10,0,0,0);  //top padding
    c.gridx = 1;       //aligned with button 2
    c.gridwidth = 2;   //2 columns wide
    c.gridy = 2;       //third row
    pane.add(text, c);
    
    JTextField text1 = new JTextField(1);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipady = 0;       //reset to default
    c.weighty = 1.0;   //request any extra vertical space
    //bottom of space
    c.insets = new Insets(10,0,0,0);  //top padding
    c.gridx = 1;       //aligned with button 2
    c.gridwidth = 2;   //2 columns wide
    c.gridy = 1;       //third row
    pane.add(text1, c);
    
    **/
    
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    /**private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("SudokuBoard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
 
        //Display the window.
        frame.pack();
        frame.setSize(600, 400);
        frame.setVisible(true);
    }**/
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
    	EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {

                SudokuBoard t = new SudokuBoard();
                t.setVisible(true);

            }



         });
    }
}