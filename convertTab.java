//Matlab import
import com.mathworks.toolbox.javabuilder.* ;
import Panorama.* ;

import java.util.* ;
import java.lang.* ;


public class convertTab implements Runnable{
	private double[][] Tableau;
	private MWArray aconvertir;
	private int ligne;
	private int colonne;

	convertTab(MWNumericArray tab,int l,int c){
		aconvertir = tab;
		ligne = l;
		colonne = c;
		Tableau = new double[ligne][colonne];
	}
	
	public double[][] getTableau(){
		return Tableau;
	}
	
	public void run() {
		for (int i=2;i<ligne;i++){
			for (int j=1;j<colonne;j++){
				int[] idx = new int[]{i,j};
				Tableau[i][j] = (double) aconvertir.get(idx);
			}
		}
	}
}
