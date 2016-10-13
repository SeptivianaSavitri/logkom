
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.omg.Messaging.SyncScopeHelper;
 
public class GabungSudoku extends JFrame {
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    static int n = 9;
    static ArrayList<String> inputs = new ArrayList<String>();
    static ArrayList<JTextField> texts = new ArrayList<JTextField>();
    static ArrayList<String> coordinateX = new ArrayList<String>();
    static ArrayList<String> coordinateY = new ArrayList<String>();
    static JTextField[][] textfields = new JTextField[n][n];
    static JButton solve;
    static JButton clear;
    static JTextField txt;
    static int counter = 0;
    public static HashMap<String,Integer> hm = new HashMap<String,Integer>();
   	public static HashMap<Integer, String> hmresult = new HashMap<Integer, String>();
   	public static BufferedWriter bw;
   	public static ArrayList<String> hasilMinisat = new ArrayList<String>();
   	public static ArrayList<String> isiUser = new ArrayList<String>();
    String result;
    
    
    public GabungSudoku() {
    	this.setSize(600,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GabungSudoku.addComponentsToPane(this.getContentPane());
        this.buttonHandler();
    }
    
    /**public GabungSudoku(Container pane) {
    	this.setSize(300,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GabungSudoku.addComponentsToPane(pane);
        this.buttonHandler();
    }**/
    
    public static void infoBox(String infoMsg, String title) {
    	JOptionPane.showMessageDialog(null,  infoMsg, "InfoBox: " + title, JOptionPane.INFORMATION_MESSAGE);
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
			    		textfields[i][j].setText("");
			    	}
			    	
			    }

			    hasilMinisat.clear();
			    isiUser.clear();
			    hmresult.clear();
			    hm.clear();
			}
    		
    	});
    	
    	solve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int p;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
					  
					  if(!textfields[i][j].getText().isEmpty()) {
						  
						  if(textfields[i][j].getText().matches("[a-zA-z]{1}")) {
							  GabungSudoku.infoBox("Sorry, input must be integer between 1 to " + n, "Wrong input");
							  return;
						  }
						  
						  p = Integer.parseInt(textfields[i][j].getText());
						  if(p>n || p<1) {
							  GabungSudoku.infoBox("Sorry, your input must be between 1 to " + n, "Wrong input");
							  return;
						  }
					    result = (i+1) + "," + (j+1) + "," + textfields[i][j].getText();
					    isiUser.add(result);
						
					  }
				    }
			    }
				try {
					carisolusi(9,isiUser);
					for(int i =0; i< hasilMinisat.size();i++){
				    	String tmp = hasilMinisat.get(i);
				    	System.out.println(hasilMinisat.get(i));
				    	System.out.println("iniii angkanya "+ hm.get(hasilMinisat.get(i)));



				    	String[] splitOrdinat = tmp.split(",");
				    	int x = Integer.parseInt(splitOrdinat[0]) -1;
				    	int y = Integer.parseInt(splitOrdinat[1]) -1;
				    	String value = splitOrdinat[2];
				    	textfields[x][y].setText(value);
				    }
					
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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

            	GabungSudoku t = new GabungSudoku();
            	t.setTitle("Sudoku " + n + "x" + n);
                t.setVisible(true);

            }



         });
    	
    }
   

	public static void carisolusi(int n, ArrayList<String> arr) throws InterruptedException{
		try {
			int inputUser = arr.size();
			int jumlahVar = (int) Math.pow(n, 3);
			int jumlahKlausa = (4*n*n) + (4* n * n * (combination(n,2))) + inputUser;
			
			String content = "p cnf "+ jumlahVar+" "+jumlahKlausa +"\n" ;
			

			File file = new File("filename.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(content);
			
		
			encode(n);
			hitung(n);
			
			
			//memasukan constraint yang berasal dari masukan user
			if(isiUser.size()!=0){
				for(int i =0;i<isiUser.size();i++){
					bw.write(hm.get(isiUser.get(i))+" 0"+"\n");
					System.out.println(isiUser.get(i));

				}
			}
			bw.close();
			
			//kode untuk menjalankan minisat
			Process process = Runtime.getRuntime().exec("minisat filename.txt result.txt");
			System.out.println("Done");
			
			
			/**String[] args = new String[] {"/bin/bash", "-c", "minisat filename.txt result.txt", "", ""};
			Process process = new ProcessBuilder(args).start();
			process.waitFor();
			**/
			//ngebaca output
			FileReader in = new FileReader("result.txt");
		    BufferedReader br = new BufferedReader(in);
			String line = br.readLine();
			if(line.equalsIgnoreCase("SAT")){
					System.out.println("Problem Satisfiable");
					String barisdua = br.readLine();
			    String[] angka = barisdua.split(" ");
			    int hasilSplit=0;
			    
			    //Meletakkan angka pada koordinat yang benar. Hasil jawaban benar ada pada HashMap hasilMinisat
			    for(int i = 0;i<angka.length;i++){
			    	hasilSplit= Integer.parseInt(angka[i]);
			    	if(hasilSplit>0){
			    		//System.out.println(""+hasilSplit);
			    		hasilMinisat.add(""+hmresult.get(hasilSplit));
			    		//System.out.println(""+hmresult.get(hasilSplit));
			    	}
			    }
			    System.out.println("KOORDINAT YANG BENAR");
			    for(int i =0; i< hasilMinisat.size();i++){
			    	String tmp = hasilMinisat.get(i);
			    	System.out.println(hasilMinisat.get(i));
			    	String[] splitOrdinat = tmp.split(",");
			    	int x = Integer.parseInt(splitOrdinat[0]) -1;
			    	int y = Integer.parseInt(splitOrdinat[1]) -1;
			    	int value = Integer.parseInt(splitOrdinat[2]) ;
			    	
			    }
			}else{

				System.out.println("Tidak ada solusi!");
				GabungSudoku.infoBox("Sorry, your input is unsatisfiable" + n, "Unsatifiable");
				hasilMinisat.clear();
			    isiUser.clear();
			}
		    //System.out.println(line);
		    
		    in.close();
			

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static int combination(int n, int k)
	{
	    return (fact(n) / (fact(k) * fact(n - k)));
	}

	public static int fact(int i)
	{
	    if (i == 1)
	    {
	        return 1;
	    }
	    return i * fact(i - 1);
	}
	public static void encode(int n){
		int code = 1;
		for(int i = 1; i<=n;i++){
			for(int j = 1; j<=n;j++){
				for(int k =1;k<=n;k++){
					hm.put(""+i+","+j+","+k, code);
					hmresult.put(code, ""+i+","+j+","+k);
					code++;				
				}	
			}
		}		      
	}
	public static void hitung(int n) throws IOException{
		int hit = 0;
		//mengecek pada setiap field at least diisi satu digit
		for(int i = 1; i<=n;i++){
			for(int j = 1; j<=n;j++){
				for(int k =1;k<=n;k++){
					if(k==n){
						//System.out.println(hm.get(""+i+","+j+","+k)+" 0" );
						bw.write(hm.get(""+i+","+j+","+k)+" 0"+"\n");
					}else{
						//System.out.print(hm.get(""+i+","+j+","+k)+" " );
						bw.write(hm.get(""+i+","+j+","+k)+" ");
					}
					
				}
						
				hit += 1;
			}
		}
		//System.out.println("Jumlah hitung = " + hit);
		
		//mengecek pada setiap field hanya ada satu digit
		for(int i = 1; i<=n;i++){
			for(int j = 1; j<=n;j++){
				for(int k =1;k<n;k++){
					for (int m = k+1; m <=n; m++){
						//System.out.println("-"+hm.get(""+i+","+j+","+k)+" -"+hm.get(i+","+j+","+ m)+" 0");
						bw.write("-"+hm.get(""+i+","+j+","+k)+" -"+hm.get(i+","+j+","+ m)+" 0"+"\n");
						hit += 1;
					}		
				}
						
				
			}
		}
		//System.out.println("Jumlah hitung = " + hit);
		
		//mengecek tiap baris at least ada satu digit
		for(int j = 1; j<=n;j++){
			for(int k = 1; k<=n;k++){
				for(int i =1;i<=n;i++){
					if(i==n){
						//System.out.println(hm.get(""+i+","+j+","+k)+" 0" );
						bw.write(hm.get(""+i+","+j+","+k)+" 0"+"\n");
					}else{
						//System.out.print(hm.get(""+i+","+j+","+k)+" " );
						bw.write(hm.get(""+i+","+j+","+k)+" ");
					}
					
				}
					
				
				hit += 1;
				
			}
		}
		//System.out.println("Jumlah hitung = " + hit);
		
		//mengecek tiap baris cuma terisi satu digit
				for(int j = 1; j<=n;j++){
					for(int k = 1; k<=n;k++){
						for(int i =1;i<n;i++){
							for (int m = (i+1); m<=n; m++){
								//System.out.println("-"+hm.get(""+i+","+j+","+k)+" -"+hm.get(m+","+j+","+ k)+" 0");
								bw.write("-"+hm.get(""+i+","+j+","+k)+" -"+hm.get(m+","+j+","+ k)+" 0"+"\n");
								hit += 1;
							}
							
						}
							
						
					
					}
				}
				//System.out.println("Jumlah hitung = " + hit);
				
				
				
				//mengecek tiap kolom at least ada satu digit
				for(int i = 1; i<=n;i++){
					for(int k = 1; k<=n;k++){
						for(int j =1;j<=n;j++){
							if(j==n){
								//System.out.println(hm.get(""+i+","+j+","+k)+" 0" );
								bw.write(hm.get(""+i+","+j+","+k)+" 0"+"\n");
							}else{
								//System.out.print(hm.get(""+i+","+j+","+k)+" " );
								bw.write(hm.get(""+i+","+j+","+k)+" ");
							}
							
						}
							
						
						
						hit += 1;
					}
				}
				//System.out.println("Jumlah hitung = " + hit);
				
				
				//mengecek tiap kolom hanya ada satu digit
				
				for(int i = 1; i<=n;i++){
					for(int k = 1; k<=n;k++){
						for(int j =1;j<n;j++){
							for (int m = j+1; m<=n; m++){
								//System.out.println("-"+hm.get(""+i+","+j+","+k)+" -"+hm.get(i+","+m+","+ k)+" 0");
								bw.write("-"+hm.get(""+i+","+j+","+k)+" -"+hm.get(i+","+m+","+ k)+" 0"+"\n");
								hit += 1;
							}							
						}																					
					}
				}
				//System.out.println("Jumlah hitung = " + hit);
				
				
				//mengecek tiap subgrid at least ada satu digit
				int akar = (int) Math.sqrt(n);
				int kelipatan=1;
				for(int k = 1;k<=n;k++){
					for(int m = 0;m<=(akar-1);m++){
						for(int p =0;p<=(akar-1);p++){
							for(int i=1;i<=akar;i++){
								for(int j=1;j<=akar;j++){
									if(kelipatan%n==0){
										kelipatan=0;
										//System.out.println((""+((akar*m)+i)+","+((akar*p)+j)+","+k)+" 0" );
										//System.out.println(hm.get(""+((akar*m)+i)+","+((akar*p)+j)+","+k)+" 0" );
										bw.write(hm.get(""+((akar*m)+i)+","+((akar*p)+j)+","+k)+" 0"+"\n" );
										kelipatan++;
										hit +=1;
									}else{
										//System.out.print(hm.get(""+((akar*m)+i)+","+((akar*p)+j)+","+k)+" " );
										bw.write(hm.get(""+((akar*m)+i)+","+((akar*p)+j)+","+k)+" ");;
										kelipatan++;
										
									}
								}
								
							}
							
						}
					}
				}
				//System.out.println("Jumlah hitung = " + hit);
				
				
				//mengecek tiap subgrid at most cuma ada 1 digit
				for(int k = 1; k<=n;k++){
					for(int m=0; m<=(akar-1); m++){
						for(int p =0; p<=(akar-1);p++){
							for(int i = 1;i<=akar;i++){
								for(int j=1;j<=akar;j++){
									for(int q = j+1;q<=akar;q++){
										//System.out.println("-"+(""+((akar*m)+i)+","+((akar*p)+j)+","+k)+" -"+(((akar*m)+i)+","+((akar*p)+q)+","+ k)+" 0");
										bw.write("-"+hm.get(""+((akar*m)+i)+","+((akar*p)+j)+","+k)+" -"+hm.get(((akar*m)+i)+","+((akar*p)+q)+","+ k)+" 0"+"\n");
										hit += 1;
									}
								}
								
							}
						}
					}
				}
				//System.out.println("Jumlah hitung = " + hit);
				
				for(int k = 1; k<=n;k++){
					for(int m=0; m<=(akar-1); m++){
						for(int p =0; p<=(akar-1);p++){
							for(int i = 1;i<=akar;i++){
								for(int j=1;j<=akar;j++){
									for(int q = i+1;q<=akar;q++){
										for(int r = 1;r<=akar;r++){
											//System.out.println("-"+(""+((akar*m)+i)+","+((akar*p)+j)+","+k)+" -"+(((akar*m)+q)+","+((akar*p)+r)+","+ k)+" 0");
											bw.write("-"+hm.get(""+((akar*m)+i)+","+((akar*p)+j)+","+k)+" -"+hm.get(((akar*m)+q)+","+((akar*p)+r)+","+ k)+" 0"+"\n");
											hit += 1;
										}
										
									}
								}
								
							}
						}
					}
				}
				//System.out.println("Jumlah hitung = " + hit);
				
	}

}