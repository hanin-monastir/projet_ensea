//Matlab import
import com.mathworks.toolbox.javabuilder.* ;
import Panorama.* ;

import java.util.* ;
import java.lang.* ;

/**
 * <b>convertTab est la classe qui permet de convertir le tableau de donnée du format
 *  MWNumericArray à double .</b>
 * <p>
 * Elle permet de bénéficier de l'avantage des threads pour la conversion.
 * Les deux tableaux latitude et longitude sont ainsi convertis quasiment en même temps et non plus
 * séquentiellement.
 * </p>
 *
 * @see Fenetre
 * @author benoit Franquet Corentin Floch
 * @version 1.0
 */
public class convertTab implements Runnable{
	/**
	*	Le tableau une fois convertit
	*/
	private double[][] Tableau;
	/**
	*	Le tableau à convertir
	*/
	private MWArray aconvertir;
	/**
	*	Le nombre de ligne du tableau
	*/
	private int ligne;
	/**
	*	Le nombre de colonne du tableau
	*/
	private int colonne;

	/**
	*	Constructeur de la classe
	*	@param tab
	*		Le tableau brut à convertir
	*	@param l
	*		Le nombre de ligne
	*	@param c
	*		Le nombre de colonne
	*	@see Fenetre
	*/
	convertTab(MWNumericArray tab,int l,int c){
		aconvertir = tab;
		ligne = l;
		colonne = c;
		Tableau = new double[ligne][colonne];
	}
	/**
	*	Fonction qui permet de retourner le tableau convertit
	*	@return Le tableau convertit
	* 	@see Fenetre
	*/	
	public double[][] getTableau(){
		return Tableau;
	}
	/**
	*	Fonction de lancement du thread, pendant lequel la conversion s'effectue
	*
	*/
	public void run() {
		for (int i=2;i<ligne;i++){
			for (int j=1;j<colonne;j++){
				int[] idx = new int[]{i,j};
				Tableau[i][j] = (double) aconvertir.get(idx);
			}
		}
	}
}
