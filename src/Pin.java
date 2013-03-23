import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

import java.awt.image.BufferedImage;

import java.io.*;
import javax.imageio.ImageIO;
import java.net.*;
import javax.swing.*;
import java.lang.*;

/**
 * <b>Pin est la classe gérant les pins qui seront placés à l'écran lors d'un double clic</b>
 * <p>
 * Elle permet d'effectuer plusieurs actions:
 * <ul>
 * <li>Un double clic permet d'ajouter une pin</li>
 * <li>un nouveau double clic supprime la pin si elle existe déjà</li>
 * <li>cliquer sur deux pins permet de les relier</li>
 * </ul>
 * </p>
 * <p>
 * Les pins peuvent être restaurer via le menu Annuler en cas de mauvaise manipulation
 * </p>
 * 
 * @see Map
 * 
 * @author benoit Franquet Corentin Floch
 * @version 1.0
 */

public class Pin extends JPanel{
	/*
	int poffsetX, poffsetY;
	int poffsetX_old, poffsetY_old;
	*/

        /**
         * Menmbre permettant de placer la pin au bon endroit sur la map
         * 
         */
	Point poffset;
	Point poffset_old;
	
        /**
         * la map qui sur laquelle sera dessiné les pins
         * 
         */
	Map map;
	
	/**
         * Image de la pin
         * 
         */
	BufferedImage pin;
	
        /**
         * Coordonnée latitude et longitude de la pin
         * 
         */
	private float latitude;
	private float longitude;	


        /**
         * Constructeur Pin
         * <p>
         * On charge l'image et on la place au bon endroit
         * </p>
         * 
         * @param x
         *            La coordonnée en x où placer la pin
	 * @param y
	 *	      La coordonnée en y où placer la pin
         * 
	 * @param m
	 *	      Une map qui sera utilisé pour affichr les pins	
	 *
         */
	Pin(int x, int y, Map m){	
		
		poffset = new Point();
		poffset_old = new Point();
		
		try{
			String fileName = "resources/Images/blu-circle32.png";
			URL defaultPin = Pin.class.getResource(fileName);
			File epingle = new File(defaultPin.toURI());
        		pin =ImageIO.read(epingle);
        	}
        	
        	
        	catch(Exception ioe)  
        	{  
            		System.out.println("read trouble: " + ioe.getMessage());  
        	}  
        	/*
        	poffsetX = x - pin.getWidth()/2;
		poffsetY = y - pin.getHeight();
		*/
		poffset.setX(x - pin.getWidth()/2);
		poffset.setY(y - pin.getHeight());
		
		map = m;
	}

        /**
         * Fonction qui permet d'afficher les pins sur la map
         * 
         * @param g
         *            Un Graphics2D qui contiendra l'image a affiché
         * 
         */
	public void draw(Graphics2D g){
		 g.drawImage(pin, poffset.getX(), poffset.getY(), map);
	
	}	

        /**
         * Fonction qui permet d'attribuer les coordonnées d'une pin
         * <p>
         * A la construction d'un objet on acquière les coordonnés de la souris, puis on revient
	 * aux vrais coordonnées en annulant l'effet du zoom et de l'offset, enfin on lit dans les tableeaux de coordonnées.
         * </p>
         * 
         * @param lat
         *            La latitude de la pin
	 * @param lon
	 *	      La longitude de la pin
         * 
         */	
	public void setCoord(double lat,double lon){
		latitude = (float)lat;
		longitude = (float)lon;
	}
        /**
         * Accesseur
         * <p>
         * Permet d'acceder à coorodnnée latitude
         * </p>
         *
         */
	// retourne la latitude
	public float getLatitude(){
		return latitude;
	}
        /**
         * Accesseur
         * <p>
         * Permet d'acceder à coorodnnée longitude
         * </p>
         *
         */
	// retourne la longitude du point
	public float getLongitude(){
		return longitude;
	}
}

