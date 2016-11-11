import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Sudoku {
	static ArrayList<String> coordinateX = new ArrayList<String>();
    static ArrayList<String> coordinateY = new ArrayList<String>();

    public static HashMap<String,Integer> hm = new HashMap<String,Integer>();
   	public static HashMap<Integer, String> hmresult = new HashMap<Integer, String>();
   	static BufferedWriter bw;

	public Sudoku() {
		System.out.println("constractor");
	}

	public static void main (String[] args) {
		System.out.println("masuk class sudoku");
	}
	
	public static void carisolusi(int n, ArrayList<String> isiUser, ArrayList<String> hasilMinisat) throws InterruptedException{
		try {
			int inputUser = isiUser.size();
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
					//System.out.println(isiUser.get(i));

				}
			}
			bw.close();
			
			//kode untuk menjalankan minisat
			//Process process = Runtime.getRuntime().exec("minisat filename.txt result.txt");
			//process.waitFor();
			System.out.println("Masih proses minisat");
			System.out.println("Done");
			
			
			/**String[] args = new String[] {"/bin/bash", "-c", "minisat filename.txt result.txt", "", ""};
			Process process = new ProcessBuilder(args).start();
			process.waitFor();
			**/
			//membaca output

			System.out.println("Masuk br");
		    BufferedReader br = new BufferedReader(new FileReader("result.txt"));
			String line = br.readLine();
			if(line.equalsIgnoreCase("SAT")){
					System.out.println("Problem Satisfiable");
					String barisdua = br.readLine();
					System.out.println("Ini baris 2: " +barisdua);
					String baristiga = br.readLine();
					System.out.println("Ini baris 3: " +baristiga);

			    String[] angka = barisdua.split(" ");
			    int hasilSplit=0;
			    
			    //Meletakkan angka pada koordinat yang benar. Hasil jawaban benar ada pada HashMap hasilMinisat
			    for(int i = 0;i<angka.length;i++){
			    	hasilSplit= Integer.parseInt(angka[i]);
			    	if(hasilSplit>0){
			    		//System.out.println(""+hasilSplit);
			    		//System.out.println(hasilSplit);
			    		//System.out.println("nilai yang masuk ke minisat"+hmresult.get(hasilSplit));
			    		
			    		hasilMinisat.add(""+hmresult.get(hasilSplit));

			    		//System.out.println(""+hmresult.get(hasilSplit));
			    	}
			    }
			    System.out.println("KOORDINAT YANG BENAR");
			    for(int i =0; i< hasilMinisat.size();i++){
			    	String tmp = hasilMinisat.get(i);
			    	//System.out.println(hasilMinisat.get(i));
			    	String[] splitOrdinat = tmp.split(",");
			    	int x = Integer.parseInt(splitOrdinat[0]) -1;
			    	int y = Integer.parseInt(splitOrdinat[1]) -1;
			    	int value = Integer.parseInt(splitOrdinat[2]) ;
			    	
			    }
			}else{

				System.out.println("Tidak ada solusi!");
				SudokuGUI.infoBox("Sorry, your input is unsatisfiable" + n, "Unsatifiable");
				hasilMinisat.clear();
			    isiUser.clear();
			}
		    //System.out.println(line);
		    
		    br.close();
			

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
					hm.put(""+i+","+j+","+k, code); //hm key : koordinat value : angka
					hmresult.put(code, ""+i+","+j+","+k); //hmresult key: angka , value : koordinat
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
				
				for(int k = 1; k<=n;k++)
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


}