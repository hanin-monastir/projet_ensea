//Matlab import
import java.util.* ;
import java.lang.* ;
import java.io.*;
import java.net.*;
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
	private double[][] TableauLAT;
	private double[][] TableauLON;
	/**
	*	Le nombre de ligne du tableau
	*/
	private int ligne;
	/**
	*	Le nombre de colonne du tableau
	*/
	private int colonne;
	/**
	*	Le fichier de latidue
	*/
	private File lat;
	private File lon;

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
	convertTab(File flat,File flon){
		TableauLAT = null;
		TableauLON = null;
		lat = flat;
		lon = flon;
	}
	/**
	*	Fonction qui permet de retourner le tableau convertit
	*	@return Le tableau convertit
	* 	@see Fenetre
	*/	
	public double[][] getLAT(){
		return TableauLAT;
	}
	public double[][] getLON(){
		return TableauLON;
	}
	/**
	*	On retourne les dimensions
	*
	*/
		public int getLigne(){
		return ligne;
	}
	public int getColonne(){
		return colonne;
	}
	/**
	*	Fonction de lancement du thread, pendant lequel la conversion s'effectue
	*
	*/
	public void run() {
		try{
			//Lecture du tableau de latitude pour obtenir les tailles
			FileReader fichierlu = new FileReader(lat);
			BufferedReader bufferlu = new BufferedReader(fichierlu);
			String Ligne = "";
			String[] resultat = null;
			while ((Ligne = bufferlu.readLine()) != null) {	
				resultat = Ligne.split(",");
				ligne = ligne + 1;
				colonne = resultat.length;
			}
		
			TableauLAT = new double[ligne][colonne];
			TableauLON = new double[ligne][colonne];
			
			//lecture des cellules de lat
			FileReader fichierLAT = new FileReader(lat);
			FileReader fichierLON = new FileReader(lon);
			BufferedReader bufferLAT = new BufferedReader(fichierLAT);
			BufferedReader bufferLON = new BufferedReader(fichierLON);
			String LigneLAT = "";
			String[] resultatLAT = null;
			String LigneLON = "";
			String[] resultatLON = null;
			int l = 0;	
			while ((LigneLAT = bufferLAT.readLine()) != null) {	
				LigneLON = bufferLON.readLine();
				resultatLAT = LigneLAT.split(",");
				resultatLON = LigneLON.split(",");
				
				for(int c = 0;c<resultat.length;c++){
					double lt = Double.parseDouble(resultatLAT[c]);	
					double ln = Double.parseDouble(resultatLON[c]);
					TableauLAT[l][c] = lt;
					TableauLON[l][c] = ln;
				}
				
				l++;
			}		
		

		} catch(Exception e){
				e.printStackTrace();
		}
	}
}
