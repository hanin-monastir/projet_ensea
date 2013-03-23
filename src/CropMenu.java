import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.image.BufferedImage;

class CropMenu extends JPopupMenu implements ActionListener{
	
	Map carte;
	//Attention à ne pas creer de nouvelle instance de Map
	
	CropMenu(Map c){
	
			carte = c;
			
	               	JMenuItem jmenuitem1 = new JMenuItem("Enregistrer");
			jmenuitem1.addActionListener(this);
         		add(jmenuitem1);
	}
	
	public void actionPerformed(ActionEvent e){
	
		if(e.getActionCommand().equals("Enregistrer"))
		{		
			System.out.println("Enregistrer");
			carte.cropMap();
		}
	}		
}


