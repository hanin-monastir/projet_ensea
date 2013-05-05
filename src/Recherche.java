import java.awt.*;  
import java.awt.event.*;  
import java.awt.geom.*;  
import java.awt.image.BufferedImage;  
import java.io.*;
import javax.imageio.ImageIO; 
import java.util.*;
//import java.net.*;  
 
import javax.swing.*;  
import javax.swing.event.*; 


public class Recherche extends JPanel implements ActionListener, Serializable{

	JButton Add;
	JButton Del;
	JButton Bouton;  
	JTextField coord_lat_dd;
	JTextField coord_lon_dd;
	String getlat,getlon;	
	Boolean isSearching;

	Recherche(){
	
		super();
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
        	String path = "resources/locales/" + locale + "/Recherche"; 
        	messages = ResourceBundle.getBundle(path, currentLocale);	
		String recherche = messages.getString("recherche");
		
		//permet d'utiliser le panneau de recherche

		getlat = "";
		getlon = "";

		setLayout(new BorderLayout());
        	
		JLabel Recherche = new JLabel(recherche);
		Recherche.setHorizontalAlignment(SwingConstants.CENTER);		
		JLabel Latitude = new JLabel("Latitude");
		JLabel Longitude = new JLabel("Longitude");

		coord_lat_dd = new JTextField();
		coord_lon_dd = new JTextField();

		Bouton = new JButton("Recherche"); 
		//Bouton.addActionListener(this);
	
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();

		//on règle les layouts
		panel1.setLayout(new GridBagLayout());
				
		//ajout au panel 1 avec contraintes
		GridBagConstraints contraintes = new GridBagConstraints();
		contraintes.gridx = 0;
    		contraintes.gridy = 0;
		contraintes.fill = GridBagConstraints.BOTH;
		contraintes.insets = new Insets(10, 10, 10, 10);
		contraintes.weightx = 1;		
		panel1.add(Recherche,contraintes);		
		contraintes.gridx = 0;
    		contraintes.gridy = 1;
		contraintes.insets = new Insets(5, 5, 5, 5);
		contraintes.fill = GridBagConstraints.BOTH;
		panel1.add(Latitude,contraintes);
		contraintes.gridx = 0;
    		contraintes.gridy = 2;
		contraintes.insets = new Insets(5, 5, 5, 5);
		contraintes.fill = GridBagConstraints.BOTH;
		panel1.add(coord_lat_dd,contraintes);
		contraintes.gridx = 0;
    		contraintes.gridy = 3;
		contraintes.insets = new Insets(5, 5, 5, 5);
		contraintes.fill = GridBagConstraints.BOTH;		
		panel1.add(Longitude,contraintes);
		contraintes.gridx = 0;
    		contraintes.gridy = 4;
		contraintes.fill = GridBagConstraints.BOTH;
		contraintes.insets = new Insets(5, 5, 5, 5);
		panel1.add(coord_lon_dd,contraintes);
		contraintes.gridx = 0;
    		contraintes.gridy = 5;
		contraintes.insets = new Insets(5, 5, 5, 5);
		contraintes.fill = GridBagConstraints.BOTH;
		panel1.add(Bouton,contraintes);
	
		//on ajoute les panneaux
		add(panel1,"North");
	}
	
	public void actionPerformed(ActionEvent e){
		
	}


	public void setCoord(double lat,double lon){
		String Lat = "" + lat;
		String Lon = "" + lon;		
		coord_lat_dd.setText(Lat);
		coord_lon_dd.setText(Lon);
	}

	
	public JButton getButton(){
		
		return Bouton;
	}
	
	public String getLatitude(){
		return getlat = coord_lat_dd.getText();
			
	}
	public String getLongitude(){
		return getlon = coord_lon_dd.getText();
	}
		
}

