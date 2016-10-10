import java.awt.Color;

import javax.swing.*;


public class Grid {
	private JFrame f;
	private JPanel p;
	private JButton b1;
	private JLabel lab;
	
	public Grid(){
		gui();
	}
	public void gui(){
		f=new JFrame("Sudoku");
		f.setVisible(true);
		f.setSize(600, 400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p = new JPanel();
		p.setBackground(Color.RED);
		
		b1 = new JButton("Solve");
		lab = new JLabel("Ini label solve");
		
		p.add(b1);
		p.add(lab);
		
		f.add(p);
		
	}
	
	
	public static void main(String[] args){
		new Grid();
	} 
}
