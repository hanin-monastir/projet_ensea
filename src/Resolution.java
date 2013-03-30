import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Collections;

class Resolution extends JFrame implements ActionListener{

	JSlider resolutionSlider;
	JButton validate;
	Container contentPane;
	Map carte;
	String nom;
	SliderListener ecouteur;
	
	Resolution(Map m){
		
		super("Choix résolution");
		
		carte = m;
		
		setResizable(false);
		setSize(500,120);
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((tailleEcran.width - getWidth())/2, (tailleEcran.height - getHeight())/2);
		
		
		
		resolutionSlider = new JSlider(JSlider.HORIZONTAL,20,100,100);
		ecouteur = new SliderListener();
		ecouteur.setImage(carte.getNamePicture());
		resolutionSlider.addChangeListener(ecouteur);
		
		validate = new JButton("Ok");
		//affichage des labels	sur les sliders
		resolutionSlider.setMajorTickSpacing(10);
		resolutionSlider.setMinorTickSpacing(5);
		resolutionSlider.setPaintTicks(true);
		resolutionSlider.setPaintLabels(true);
		

		JPanel pan1 = new JPanel();
		pan1.setLayout(new GridLayout(3,1));
		pan1.add(new JLabel("Facteur % :"));
		pan1.add(resolutionSlider);
		
		JPanel pan2 = new JPanel();
		pan2.setLayout(new GridBagLayout());
		
		GridBagConstraints cpp = new GridBagConstraints();
		cpp.fill = GridBagConstraints.BOTH;
		cpp.insets = new Insets(1, 1, 1, 1);
		//cpp.weightx = 1;
		
		cpp.gridx = 1;
		cpp.gridy = 0;
		pan2.add(validate,cpp);
		validate.addActionListener(this);
		
		pan1.add(pan2);
		
		add(pan1);
		setVisible(false);
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getActionCommand().equals("Ok"))
		{
			System.out.println("Appui sur Ok " + carte.getNamePicture() + " " + ecouteur.getCurrentValue());
			/*
				Gestion des modes
			*/
			if(carte.getMode() == "Panorama"){
				//on ramène les pins au bon endroit
				ArrayList<Pin> listPin = carte.getListPin();
				int offsetX = carte.getOffsetX();
				int offsetY = carte.getOffsetY();
				double scale = carte.getScale();
				
				for(Pin p : listPin){
					int xp = listPin.get(listPin.indexOf(p)).poffset.getX();
					int yp = listPin.get(listPin.indexOf(p)).poffset.getY();		
			
					xp = (int)((xp-offsetX)/scale);
					yp = (int)((yp-offsetY)/scale);								
					//retour au vrai coordonnées
					p.poffset.setX(xp);
					p.poffset.setY(yp);			
				}

				
				//on charge la nouvelle image
				carte.loadImage(ecouteur.getNewImage());
			}
			setVisible(false);	
			/*
			//Zone ou l'on va afficher l'image
			*/	
			
		}
	}			
}
