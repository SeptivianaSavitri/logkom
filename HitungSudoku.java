import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HitungSudoku {
	public static HashMap<String,Integer> hm = new HashMap<String,Integer>();
	public static void main(String[] args) {
		encode(9);
		hitung(9);
		
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
	public static void hitung(int n){
		int hit = 0;
		//mengecek pada setiap field at least diisi satu digit
		for(int i = 1; i<=n;i++){
			for(int j = 1; j<=n;j++){
				for(int k =1;k<=n;k++){
					if(k==n){
						//System.out.println(hm.get(""+i+","+j+","+k)+" 0" );
					}else{
						//System.out.print(hm.get(""+i+","+j+","+k)+" " );
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
					}else{
						//System.out.print(hm.get(""+i+","+j+","+k)+" " );
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
							}else{
								//System.out.print(hm.get(""+i+","+j+","+k)+" " );
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
								hit += 1;
							}
							
						}
							
						
						
						
					}
				}
				System.out.println("Jumlah hitung = " + hit);
				
				//mengecek tiap subgrid at least ada satu digit
				for(int k = 1; k<=n;k++){
					for(int m=0; m<=2;m++){
						for(int p=0; 0<=2;p++){
							for(int i=1;i<=3;i++){
								for(int j=1;j<=3;j++){
									if(j==n){
										System.out.println((""+((3*m)+i)+","+((3*p)+j)+","+k)+" 0" );
									}else{
										System.out.println((""+((3*m)+i)+","+((3*p)+j)+","+k)+" " );
										
									}
								}
							}
						}
					}
				}
				
				System.out.println("Jumlah hitung = " + hit);
				
	}
}
