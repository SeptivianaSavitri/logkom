import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HitungSudoku {
	public static HashMap<String,Integer> hm = new HashMap<String,Integer>();
	public static BufferedWriter bw;
	public static void main(String[] args) {
		
		
		int n = 9;
		int inputUser = 1;
		
		// if file doesnt exists, then create it
		try {
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
			bw.close();
			System.out.println("Done");

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
		System.out.println("Jumlah hitung = " + hit);
		
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
		System.out.println("Jumlah hitung = " + hit);
		
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
		System.out.println("Jumlah hitung = " + hit);
		
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
				System.out.println("Jumlah hitung = " + hit);
				
				
				
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
				System.out.println("Jumlah hitung = " + hit);
				
				
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
				System.out.println("Jumlah hitung = " + hit);
				
				
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
										System.out.println((""+((akar*m)+i)+","+((akar*p)+j)+","+k)+" 0" );
										kelipatan++;
										hit +=1;
									}else{
										System.out.print((""+((akar*m)+i)+","+((akar*p)+j)+","+k)+" " );
										kelipatan++;
										
									}
								}
								
							}
							
						}
					}
				}
				System.out.println("Jumlah hitung = " + hit);
	}
}
