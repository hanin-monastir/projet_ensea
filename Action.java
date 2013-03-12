import java.util.*;
import java.io.*;
import java.util.Collections;

/**
 * <b>Action est la classe qui permet l'annulation des actions.</b>
 * <p>
 * Elle permet d'enregister l'historique des actions
 * </p>
 * 
 * @author benoit Franquet Corentin Floch
 * @version 1.0
 */

public class Action {
        /**
        * copie grâce au copie constructeur des listes de pin et de ligne de Map
        * 
        */
	private ArrayList<Pin> copyPin;
	private ArrayList<StraightLine> copyLine;

        /**
        * Permet d'ajouter/supprimer un marqueur ou une ligne, ainsi que d'afficher un menu
        * 
        * @param p
        *            La liste de pin de Map avant modification
	* @param l
	*	     La liste de ligne avant modification
        * 
        */
	Action(ArrayList<Pin> p,ArrayList<StraightLine> l){
		//on crée une copie des listes de pin et ligne
		copyPin = new ArrayList<Pin>(p);
		copyLine = new ArrayList<StraightLine>(l);
	}
	
	/**
        * Accesseurs permettant d'accéder à la liste de pin
        * 
	* @return La copie de la liste de pin 
        */
	public ArrayList<Pin> getListPin(){
		return copyPin;
	}
	/**
        * Accesseurs permettant d'accéder à la liste de ligne
        * 
	* @return La copie de la liste de ligne
        */
	public ArrayList<StraightLine> getListLine(){
		return copyLine;
	}
}
