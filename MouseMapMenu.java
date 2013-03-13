import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;



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
	
			carte = c;
			
	               	JMenuItem jmenuitem1 = new JMenuItem("Ajouter/Supprimer Marqueur");
			jmenuitem1.addActionListener(this);
           		add(jmenuitem1);
				            		
	               	JMenuItem jmenuitem2 = new JMenuItem("Centrer ici");
			jmenuitem2.addActionListener(this);      		
			add(jmenuitem2);
			
            		JMenuItem jmenuitem3 = new JMenuItem("Taille originale");
			jmenuitem3.addActionListener(this);          		
			add(jmenuitem3);
			
			JMenuItem jmenuitem4 = new JMenuItem("Zoom In");
			jmenuitem4.addActionListener(this);          		
			add(jmenuitem4);
			
			JMenuItem jmenuitem5 = new JMenuItem("Zoom Out");
			jmenuitem5.addActionListener(this);          		
			add(jmenuitem5);

	               	JMenuItem jmenuitem6 = new JMenuItem("Enregistrer la zone");
			jmenuitem6.addActionListener(this);
           		add(jmenuitem6);
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
	
		if(e.getActionCommand().equals("Ajouter/Supprimer Marqueur"))
		{	
			//System.out.println(getX());	
			System.out.println("Appui sur Ajouter/Supprimer Marqueur");
			carte.pinMap(carte.endX, carte.endY);
		}
		
		if(e.getActionCommand().equals("Centrer ici"))
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
		
		if(e.getActionCommand().equals("Taille originale"))
		{	
			carte.initLocation(); //fonction invalide avec la présence de marqueurs (reprise de l'image initiale)
			//placer les pins en originalOffsetX et originalOffsetY
			//dans initLocation
		}
		
		if(e.getActionCommand().equals("Zoom In"))
		{	
			carte.scale += 1;
			carte.setScale(carte.endX, carte.endY);
		}
		
		if(e.getActionCommand().equals("Zoom Out"))
		{	
			carte.scale -= 1;
			carte.setScale(carte.endX, carte.endY);	
		}
		
		if(e.getActionCommand().equals("Enregistrer la zone"))
		{
			//carte.CropMap();
			carte.startDraw();
		}
	}		
}


