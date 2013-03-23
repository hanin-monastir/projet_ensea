import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.Serializable;
import java.io.*;
import java.util.*;
import java.lang.*;
 
/**
 * <b>Configuration est la classe qui permet de paramétrer l'itinéraire suivit par l'avion.</b>
 * <p>
 * Elle permet de régler plusieurs paramètres importants lors de la création de l'itinéraire pour l'avion.
 * <ul>
 * <li>La taille de l'image en pixel: 600 correspond à une image 600x600 pixels</li>
 * <li>Modifier le facteur de zoom de la zone</li>
 * <li>Définir l'espace inter-breakpoint</li>
 * <li>Définir les limites de la zone survoléel</li>
 * </ul>
 * </p>
 * <p>
 * Les paramètres sont enregistrés dans un fichier itinéraire.txt
 * </p>
 * 
 * @see Itineraire
 * @author benoit Franquet Corentin Floch
 * @version 1.0
 */
public class Configuration extends JFrame implements ActionListener{
	
	/**
	*	Le paramètre de zoom 
	*/
	private int zoom;
	/**
	*	Le paramètre réglant la taille de l'image
	*/
	private int taille;
	/**
	*	L'espace entre les marqueurs
	*/
	private int espace;
	/**
	*	Les champs contenant les paramètres	
	*/
	private JTextField TP1lat;
	private JTextField TP1lon;
	private JTextField TP2lat;
	private JTextField TP2lon;
	private JTextField TP3lat;
	private JTextField TP3lon;
	private JTextField TP4lat;
	private JTextField TP4lon;
	private JTextField Adresse;
	private JSlider TZoom;
	private JSlider TSpace;
	private JSlider TSize;
	private JSlider TAngle;	
	/**
	*	le dossier contenant les breakpoints lors de l'enregistrement
	*/	
	private String Dossier;
	
        /**
         * 	Constructeur Configuration.
         * 	<p>
         * 	A la construction d'un objet Configuration, on charge les paramètres précédement enregistrés
         * 	</p>
         * 	@see Fenetre
         */
	Configuration(){
		super("Configuration de l'itinéraire");
		setResizable(false);
		setLocation(200,30);
		setIconImage(new ImageIcon(this.getClass().getResource("map_icone.png")).getImage());
		setSize(400,500);
				
		
		//création des composants
		/*
			Bouton de sauvegarde
		*/
		JButton Sauvegarde = new JButton("Sauvegarder");
		Sauvegarde.addActionListener(this);
		
		JButton Record = new JButton("Choisir");
		Record.addActionListener(this);
		/*
			Les  différents Labels			
		*/	
		JLabel Zoom = new JLabel("Facteur de zoom");
		JLabel Point1 = new JLabel("Point 1");
		JLabel Point2 = new JLabel("Point 2");
		JLabel Point3 = new JLabel("Point 3");
		JLabel Point4 = new JLabel("Point 4");
		JLabel Taille = new JLabel("Taille de l'image");
		JLabel Espace = new JLabel("Espace inter-breakpoint");
		JLabel Arc = new JLabel("Angle pour le demi tours");
		/*
			Les différents champs de texte
		*/
		TP1lat = new JTextField();
		TP1lon = new JTextField();
		TP2lat = new JTextField();
		TP2lon = new JTextField();
		TP3lat = new JTextField();
		TP3lon = new JTextField();
		TP4lat = new JTextField();
		TP4lon = new JTextField();
		Adresse = new JTextField();

		/*Les différents paramètres de tailles */
		TZoom = new JSlider(JSlider.HORIZONTAL,1,18,1);
		TSize = new JSlider(JSlider.HORIZONTAL,200,640,400);
		TSpace = new JSlider(JSlider.HORIZONTAL,10,100,10);
		TAngle = new JSlider(JSlider.HORIZONTAL,10,50,10);

			//affichage des labels	sur les sliders
		TZoom.setMajorTickSpacing(5);
		TZoom.setMinorTickSpacing(1);
		TZoom.setPaintTicks(true);
		TZoom.setPaintLabels(true);

		TSize.setMajorTickSpacing(50);
		TSize.setMinorTickSpacing(10);
		TSize.setPaintTicks(true);
		TSize.setPaintLabels(true);

		TSpace.setMajorTickSpacing(10);
		TSpace.setMinorTickSpacing(10);
		TSpace.setPaintTicks(true);
		TSpace.setPaintLabels(true);

		TAngle.setMajorTickSpacing(5);
		TAngle.setMinorTickSpacing(1);
		TAngle.setPaintTicks(true);
		TAngle.setPaintLabels(true);
		/*
			Lecture des paramètres précédement enregistrés
		*/
		readParameters();

		//Agencement
		/*
			Réglage du layout
		*/
		setLayout(new GridBagLayout());
		
		/*
			Panel qui acceuille le choix du dossier d'enregistrement
		*/				
		JPanel Psave = new JPanel();
		Psave.setLayout(new GridBagLayout());
				
			GridBagConstraints cps = new GridBagConstraints();	
			cps.fill = GridBagConstraints.BOTH;
			cps.insets = new Insets(3, 3, 3, 3);
			cps.weightx = 1;
			//le textfield qui contient l'adresse
			cps.gridx = 0;
			cps.gridy = 0;
			Psave.add(Adresse,cps);
			//le bouton maintenant
			cps.gridx = 1;
			cps.gridy = 0;
			Psave.add(Record,cps);
		/*
			Panel qui va acceuillir les points
		*/
		JPanel Ppoint = new JPanel();
		Ppoint.setLayout(new GridBagLayout());
			
			GridBagConstraints cpp = new GridBagConstraints();
			cpp.fill = GridBagConstraints.BOTH;
			cpp.insets = new Insets(3, 3, 3, 3);
			cpp.weightx = 1;
			//Etiquette lat/lon
			cpp.gridx = 0;
			cpp.gridy = 1;
			Ppoint.add(new JLabel("Latitude"),cpp);
			cpp.gridx = 0;
			cpp.gridy = 2;
			Ppoint.add(new JLabel("Longitude"),cpp);			

			cpp.gridx = 0;
			cpp.gridy = 4;
			Ppoint.add(new JLabel("Latitude"),cpp);
			cpp.gridx = 0;
			cpp.gridy = 5;
			Ppoint.add(new JLabel("Longitude"),cpp);	

			//points et champs
			cpp.gridx = 1;
			cpp.gridy = 0;
			Ppoint.add(Point1,cpp);
			cpp.gridx = 1;
			cpp.gridy = 1;
			Ppoint.add(TP1lat,cpp);
			cpp.gridx = 1;
			cpp.gridy = 2;
			Ppoint.add(TP1lon,cpp);	
					
			cpp.gridx = 2;
			cpp.gridy = 0;
			Ppoint.add(Point2,cpp);
			cpp.gridx = 2;
			cpp.gridy = 1;
			Ppoint.add(TP2lat,cpp);
			cpp.gridx = 2;
			cpp.gridy = 2;
			Ppoint.add(TP2lon,cpp);	

			cpp.gridx = 1;
			cpp.gridy = 3;
			Ppoint.add(Point3,cpp);
			cpp.gridx = 1;
			cpp.gridy = 4;
			Ppoint.add(TP3lat,cpp);
			cpp.gridx = 1;
			cpp.gridy = 5;
			Ppoint.add(TP3lon,cpp);

			cpp.gridx = 2;
			cpp.gridy = 3;
			Ppoint.add(Point4,cpp);
			cpp.gridx = 2;
			cpp.gridy = 4;
			Ppoint.add(TP4lat,cpp);
			cpp.gridx = 2;
			cpp.gridy = 5;
			Ppoint.add(TP4lon,cpp);		
		/*
			Contraintes d'agencement pour les différents champs hors points
		*/				
		GridBagConstraints contraintes = new GridBagConstraints();
		contraintes.fill = GridBagConstraints.BOTH;
		contraintes.insets = new Insets(3, 3, 3, 3);
		contraintes.weightx = 1;		
		contraintes.gridx = 0;		
		
		//taille
    		contraintes.gridy = 0;	
		add(Taille,contraintes);
    		contraintes.gridy = 1;
		add(TSize,contraintes);
		//zoom
    		contraintes.gridy = 2;
		add(Zoom,contraintes);
    		contraintes.gridy = 3;
		add(TZoom,contraintes);
		//espacement
    		contraintes.gridy = 4;
		add(Espace,contraintes);
		contraintes.gridy = 5;
		add(TSpace,contraintes);
		//Angle
		contraintes.gridy = 6;
		add(Arc,contraintes);
		contraintes.gridy = 7;
		add(TAngle,contraintes);
		//panel de points
		contraintes.gridy = 8;
		add(Ppoint,contraintes);
		//panel du dossier
		contraintes.gridy = 9;
		add(Psave,contraintes);
		//bouton d'enregistrement
		contraintes.gridy = 10;
		add(Sauvegarde,contraintes);
		/*
			on affiche la fenêtre
		*/		
		setVisible(true);				
	}

        /**
        * 	Capte les clics sur le bouton sauvegarde
	*
        *	@param e
        *	           Un évenement clic sur le bouton sauvegarde
        * 
        */
	public void actionPerformed(ActionEvent e){
	
		if(e.getActionCommand().equals("Sauvegarder"))
		{
			System.out.println("Vous avez appuyé sur sauvegarder");
			toString("Ressources/Conf/Itineraire.conf");
			setVisible(false);			
		}
		else if(e.getActionCommand().equals("Choisir")){
			chooseDestinationFolder();
		}
	}
	
        /**
        * 	Permet de charger les paramètres précédement enregistrés
        * 
        */
	public void readParameters(){
		try{		
			File monFichier = new File("Ressources/Conf/Itineraire.conf");
			if (monFichier.exists()){			
				FileReader fichierlu = new FileReader(monFichier);
				BufferedReader bufferlu = new BufferedReader(fichierlu);
				String ligne = "";
				String[] resultat = null;
	
				while ((ligne = bufferlu.readLine()) != null) {	
					System.out.println("lg " + ligne.length());
					resultat = ligne.split(" ");
					switch(resultat[0]){				
						case "Zoom" : 
								TZoom.setValue(Integer.parseInt(resultat[1]));
								break;
						case "Espace" : 
								TSpace.setValue(Integer.parseInt(resultat[1]));
								break;
						case "Taille"  : 
								TSize.setValue(Integer.parseInt(resultat[1]));
								break;
						case "Point1" :
								TP1lat.setText(resultat[2]);
								TP1lon.setText(resultat[4]);
								break;
						case "Point2" :
								TP2lat.setText(resultat[2]);
								TP2lon.setText(resultat[4]);
								break;
						case "Point3" :
								TP3lat.setText(resultat[2]);
								TP3lon.setText(resultat[4]);
								break;
						case "Point4" :
								TP4lat.setText(resultat[2]);
								TP4lon.setText(resultat[4]);
								break;
						case "Dossier" : Adresse.setText(resultat[1]);
								 break;
						case "Angle" : TAngle.setValue(Integer.parseInt(resultat[1]));
							       break;
						default : break;
					}
				}
			}
		} catch(Exception ei){
			ei.printStackTrace();
		}
	}
	
        /**
        * 	Permet d'écrire les paramètres de chaque champs dans un fichier texte
        * 
        * 	@param s
        *		la chaine de caractère.
        *	@return Une chaine montrant l'état du composant
        */
	public String toString(String s){
		try{		
			File monFichier = new File(s);
			BufferedWriter bw = new BufferedWriter(new FileWriter(monFichier)) ;
			String ligne = "";

			//Sauvegarde du zoom
			ligne = "Zoom " + TZoom.getValue();		
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();

			//Sauvegarde de l'espace
			ligne = "Espace " + TSpace.getValue();
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();

			//Sauvegarde de la taille
			ligne = "Taille " + TSize.getValue();			
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();
			
			//Angle inter-breakpoint pendans le demi-tours
			ligne = "Angle " + TAngle.getValue();
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();
		
			//Sauvegarde des points
			ligne = "Point1 " + "Lat " + TP1lat.getText() + " Lon " + TP1lon.getText();
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();
			
			ligne = "Point2 " + "Lat " + TP2lat.getText() + " Lon " + TP2lon.getText();
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();

			ligne = "Point3 " + "Lat " + TP3lat.getText() + " Lon " + TP3lon.getText();
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();

			ligne = "Point4 " + "Lat " + TP4lat.getText() + " Lon " + TP4lon.getText();
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();

			//sauvegarde du dossier d'enregistrement des données
			ligne = "Dossier "+ Adresse.getText();
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();
			
			bw.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		return "Fichier enregistre";
	}
	
	/**
	*	Fonction de sauvegarde
	*/
	public void chooseDestinationFolder(){
		JFileChooser chooser = new JFileChooser();
   		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
		chooser.setAcceptAllFileFilterUsed(false);
 		int returnVal = chooser.showOpenDialog(this.getParent());    			
    		if(returnVal == JFileChooser.APPROVE_OPTION){
    			Dossier = chooser.getSelectedFile().getAbsolutePath();
    			Adresse.setText(Dossier);
    		}
	}	
}
