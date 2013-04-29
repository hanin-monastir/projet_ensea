import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import java.awt.image.BufferedImage;

class CropMenu extends JPopupMenu implements ActionListener{
	
	Map carte;
	//Attention à ne pas creer de nouvelle instance de Map
	String sauver;
	
	CropMenu(Map c){
			Locale currentLocale = Locale.getDefault();
			String locale = currentLocale.getLanguage();
			String country = currentLocale.getCountry();
        		ResourceBundle messages;
        		currentLocale = new Locale(locale, country);
        		String path = "resources/locales/" + locale + "/CropMenu"; 
        		messages = ResourceBundle.getBundle(path, currentLocale);	
	
			sauver = messages.getString("sauver");		
				
			carte = c;
			
	               	JMenuItem jmenuitem1 = new JMenuItem(sauver);
			jmenuitem1.addActionListener(this);
         		add(jmenuitem1);
	}
	
	public void actionPerformed(ActionEvent e){
	
		if(e.getActionCommand().equals(sauver))
		{		
			System.out.println("Enregistrer");
			carte.cropMap();
		}
	}		
}


