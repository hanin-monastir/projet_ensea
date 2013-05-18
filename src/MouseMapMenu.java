import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;



/**
 * <b>MouseMapMenu est la classe qui permet de gérer le menu du clic droit</b>
 * <p>
 * Elle permet d'acceder aux différentes actions
 * </p>
 * 
 * @author benoit Franquet Corentin Floch
 * @version 1.0
 */
public class MouseMapMenu extends JPopupMenu implements ActionListener{

	Recherche search;
	
        /**
        * Une map qui sera une référence de la map de la fenetre
        * 
        */
	Map carte;
	//Attention à ne pas creer de nouvelle instance de Map
	/**
	* Les strings pour l'internationalisation
	*
	*
	*/
	String add;
	String center;
        String size;
        String plus;
        String moins;
        /**
        * Un element de menu qui doit pouvoir être grisé
        */
        JMenuItem jmenuitem1;
        
        /**
        * Constructeur MouseMapMenu
        * <p>
        * Construction du menu clic droit
        * </p>
        * 
        * @param c
        *            On envoit une map en argument
        * 
        */
	MouseMapMenu(Map c){
			Locale currentLocale = Locale.getDefault();
			String locale = currentLocale.getLanguage();
			String country = currentLocale.getCountry();
		
			//test opur régler la locale si la traduction est absente
			String findlocale = "resources/locales/" + locale;
			File flocale = new File(findlocale);
			if(!flocale.exists()){
				locale = "en";
				country = "US";
			}
        	
        		ResourceBundle messages;
        		currentLocale = new Locale(locale, country);
        		String path = "resources/locales/" + locale + "/MouseMapMenu"; 
        		messages = ResourceBundle.getBundle(path, currentLocale);
        		
        		add = messages.getString("add");
			center = messages.getString("center");
        		size = messages.getString("size");
        		plus = messages.getString("plus");
        		moins = messages.getString("moins");
        	
        	
			carte = c;
			
	               	jmenuitem1 = new JMenuItem(add);
			jmenuitem1.addActionListener(this);
           		add(jmenuitem1);
				            		
	               	JMenuItem jmenuitem2 = new JMenuItem(center);
			jmenuitem2.addActionListener(this);      		
			add(jmenuitem2);
			
            		JMenuItem jmenuitem3 = new JMenuItem(size);
			jmenuitem3.addActionListener(this);          		
			add(jmenuitem3);
			
			JMenuItem jmenuitem4 = new JMenuItem(plus);
			jmenuitem4.addActionListener(this);          		
			add(jmenuitem4);
			
			JMenuItem jmenuitem5 = new JMenuItem(moins);
			jmenuitem5.addActionListener(this);          		
			add(jmenuitem5);
	}

        /**
        * Gestion des actions liées au menu 
        * <p>
        * Permet de gérer le menu de la barre de menu et du menu clic droit
        * </p>
        * 
        * @param e
        *           Un évènement intervenant sur le mnnu
        * 
        */
	public void actionPerformed(ActionEvent e){
	
		if(e.getActionCommand().equals(add))
		{	
			//System.out.println(getX());	
			System.out.println("Appui sur Ajouter/Supprimer Marqueur");
			if(carte.getMode() == "Panorama" ){
				carte.pinMap(carte.endX, carte.endY);
			}
		}
		
		if(e.getActionCommand().equals(center))
		{	
			//faire en sorte de lancer un repaint() sur Map
			carte.offsetX_old = carte.offsetX;
			carte.offsetY_old = carte.offsetY;
			
			carte.offsetX = carte.dimX/2 - (carte.endX - carte.offsetX);
			carte.offsetY = carte.dimY/2 - (carte.endY - carte.offsetY);
			
			//actualiser la position des pin
			for(Pin p : carte.listPin){
              		
				p.poffset.setX(p.poffset.getX() + carte.offsetX - carte.offsetX_old);
				p.poffset.setY(p.poffset.getY() + carte.offsetY - carte.offsetY_old);
			}
			
			carte.repaint();
		}
		
		if(e.getActionCommand().equals(size))
		{	
			carte.initLocation();
			//modifie également la liste des pin
		}
		
		if(e.getActionCommand().equals(plus))
		{	
			carte.scale += 1;
			carte.setScale(carte.endX, carte.endY);
		}
		
		if(e.getActionCommand().equals(moins))
		{	
			if(carte.scale >= 2){
				carte.scale -= 1;
				carte.setScale(carte.endX, carte.endY);	
			}
		}
	}
	public void enabledMenu()
	{
		if(carte.getMode() == "Panorama"){
			jmenuitem1.setEnabled(true);
		}
		else
		{
			jmenuitem1.setEnabled(false);
		}
	}		
}


