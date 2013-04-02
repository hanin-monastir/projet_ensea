import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
*	<b> Gestion des écoute sur un slider </b>
*	Cette classe permet de construire un écouteur personnalisé
*	@see Resolution
*	@author Franquet Benoit Floch Corentin
*	@version 1.0
*/
public class SliderListener implements ChangeListener {
	/**
	*	La résolution en pourcentage
	*/
	private float pourcentage;
	/**
	*	Fonction qui permet de détecter si le slider a été bougé
	*	@param e
	*		Une modification du slider
	*/
	public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();
	        if (!source.getValueIsAdjusting()) {
			pourcentage = source.getValue();
			pourcentage /= 100;
            		System.out.println(pourcentage +" ");
        	}    
    	}
    	/**
	*	Fonction qui retourne le pourcentage
	*	@return le pourcentage
	*/ 	
 	public float getCurrentValue(){
 		return pourcentage;
 	}  	
}
