import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Collections;
import com.mathworks.toolbox.javabuilder.* ;
import Panorama.* ;
import java.io.*;	

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
			/*
				Gestion des modes
			*/
			setVisible(false);
			System.out.println(carte.getNamePicture());
			//restore l'étét d'origine de la carte
			carte.cancelAll();
			
			//affiche l'image
			displayImage();			
		}
	}	
	
	public void displayImage(){
		//mise en place
		Object facteur[] = new Object[1];
		Object nom[] = new Object[1];
		Object path[] = new Object[1];
						
            	Panorama pano = null;	
            	try{
            		pano = new Panorama();
            		facteur[0] = ecouteur.getCurrentValue();
            		nom[0] = carte.getNamePicture();
            		path[0] = pano.SubImage(1,facteur[0],nom[0]);
			File file = new File(carte.getNamePicture());
			String fileimage = file.getAbsolutePath();            			
            		String dos = fileimage.substring(0,fileimage.lastIndexOf(File.separator));
            		String newimage = dos + "/subImage.png";
            		System.out.println(newimage);
            		//on charge la nouvelle image
			carte.loadImage(newimage);
			carte.repaint();
            	} catch(MWException ei){
            		ei.printStackTrace();
            	} catch(Exception ea){
            		ea.printStackTrace();
            	} finally{
           		pano.dispose();            	
           	}	
	}	
}
