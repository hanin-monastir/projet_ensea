import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.Serializable;
import java.io.*;

import java.awt.Desktop.*;
//Matlab import
import com.mathworks.toolbox.javabuilder.* ;
import Panorama.* ;
import java.util.* ;
import java.lang.* ;


/**
 * <b>Fenetre est la classe qui crée l'interface graphique</b>
 * <p>
 * Elle permet la festion et l'interface des différents composants
 * <ul>
 * <li>Une barre de menu est disponible/li>
 * <li>Un panneau de recherche permet de faire des recherches</li>
 * <li>Une map permet d'afficher le panorama</li>
 * <li>Un menu clic droit permet d'éffectuer des actions</li>
 * </ul>
 * </p>
 * <p>
 * L'interfaçage avec le MCR se retrouve dans la barre de menu option Créer
 * </p>
 * 
 * @author benoit Franquet Corentin Floch
 * @version 1.0
 */
public class Fenetre extends JFrame implements ActionListener{
	
        /**
         * La progresse barre qui sera affichée lors de la création du panorama
         * 
         */
	ProgressWindows progressebarre;
        /**
         * Le panneau recherche
         * 
         */
	Recherche panel_search;
        /**
         * La Map qui accueillera le panorama
         * 
         */
	Map panorama;
	//Java TP2 variable déclarée dans le constructeur :    
	Container contentPane;

        /**
         * Matrices utilisées pour la conversion des données Matlab 
         * 
         */
	double[][] LATITUDE;
	double[][] LONGITUDE;
	//test bouton Recherche
        /**
         * Bouton recherche référence du bouton du panneau recherche sur lequel sera placé des écouteurs
	 * <p>
	 * utile pour communiquer avec la Map
         * </p> 
	 *
         */
	JButton recherche;
	      
         /**
         * ArrayList de menu
         *
         */
         ArrayList<JMenuItem> activableMenu;
         
        /**
         * Constructeur Fenetre.
         * <p>
         * Construction de la fenetre principale
         * </p>
         * 
         * @param titre
         *            Le titre de la fenêtre
         * 
         */
	Fenetre(String titre){
	
		super(titre);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//choisit l'icone de la fenetre
		setIconImage(new ImageIcon(this.getClass().getResource("resources/Images/map_icone.png")).getImage());
		setLocation(0, 0);
		
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		int hauteur = (int)tailleEcran.getHeight();
		int largeur = (int)tailleEcran.getWidth();
		
		setSize(tailleEcran);
				
		activableMenu = new ArrayList<JMenuItem>();
		
		JMenuBar m = new JMenuBar();
		//////////////////// Menu Fichier //////////////////////////////////
		JMenu menu1 = new JMenu("Fichier");

		JMenuItem ouvrir = new JMenuItem("Ouvrir");
		ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		ouvrir.addActionListener(this);
		menu1.add(ouvrir);
		
		
		JMenuItem creer = new JMenuItem("Créer");
		creer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));	
		creer.addActionListener(this);
		menu1.add(creer);	
	
		JMenuItem charger = new JMenuItem("Charger");
		charger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));	
		charger.addActionListener(this);
		menu1.add(charger);
		
		JMenuItem sauver = new JMenuItem("Sauvegarder l'image");
		sauver.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));	
		sauver.addActionListener(this);
		activableMenu.add(sauver);
		menu1.add(sauver);

		JMenuItem sauverpin = new JMenuItem("Sauvegarder les positions");
		sauverpin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));		
		sauverpin.addActionListener(this);
		activableMenu.add(sauverpin);
		menu1.add(sauverpin);

		menu1.addSeparator();
		JMenuItem confPanorama = new JMenuItem("Configurer le panorama");
		confPanorama.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));		
		confPanorama.addActionListener(this);
		menu1.add(confPanorama);


		menu1.addSeparator();

		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));	
		quitter.addActionListener(this);
		menu1.add(quitter);		

		//////////////////// Menu Édition /////////////////////////////////
		JMenu menu2 = new JMenu("Édition");

		JMenuItem annulertout = new JMenuItem("Annuler tout");
		annulertout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));		
		annulertout.addActionListener(this);
		activableMenu.add(annulertout);
		menu2.add(annulertout);			

		JMenuItem annulerun = new JMenuItem("Annuler");
		annulerun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		annulerun.addActionListener(this);
		activableMenu.add(annulerun);
		menu2.add(annulerun);			
	
		/////////////////// Menu Itinéraire /////////////////////////////////////
		JMenu menu3 = new JMenu("Itinéraire");	
	
		JMenuItem itineraire = new JMenuItem("Tracer itinéraire");
		itineraire.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));		
		itineraire.addActionListener(this);
		menu3.add(itineraire);

		JMenu sousmenu3 = new JMenu("Configuration");
		JMenuItem configitineraire = new JMenuItem("Configurer itinéraire");
		configitineraire.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));		
		configitineraire.addActionListener(this);
		sousmenu3.add(configitineraire);
		
		JMenuItem configflight = new JMenuItem("Configurer les paramètres de vol");
		configflight.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));		
		configflight.addActionListener(this);
		sousmenu3.add(configflight);
		menu3.add(sousmenu3);
		//////////////////// Ajout /////////////////////////////////////////
		enableMenu(false);
		
		m.add(menu1);
		m.add(menu2);
		m.add(menu3);
//		m.add(menu4);
		
		setJMenuBar(m);
			
		contentPane = getContentPane();
		
		///Essai de disposition : Panneau Recherche
		panel_search = new Recherche();
		panel_search.setVisible(false);
		recherche = panel_search.Bouton;
		recherche.addActionListener(this);
		panorama = null;
		
		String textLabel = "<html><b><big><big>Bienvenue sur ENSEAMap</big></big></b></html>";
		JLabel provisoire = new JLabel(textLabel);
		provisoire.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); 
		contentPane.add(provisoire,"Center");
		
		progressebarre = new ProgressWindows();
		progressebarre.setVisible(false);		
		progressebarre.setLocationRelativeTo(null);	
		contentPane.add(panel_search, "West");				
				
		setVisible(true);
	}
	
        /**
         * Gestion des actions liées au menu 
         * <p>
         * Permet de gérer le menu de la barre de menu et du menu clic droit
         * </p>
         * 
         * @param e
         *           Un évènement
         * 
         */
	public void actionPerformed(ActionEvent e){
	
		if(e.getActionCommand().equals("Quitter"))
		{
			System.out.println("Vous avez appuyé sur Quitter");
			System.exit(0);
		}
		else if(e.getActionCommand().equals("Configurer le panorama")){
			optionMatlab option = new optionMatlab();
		}
		else if(e.getActionCommand().equals("Configurer les paramètres de vol")){
			Flight paramVol = new Flight();
		}
		else if(e.getActionCommand().equals("Configurer itinéraire")){
			Configuration conf = new Configuration();			
		}
		else if(e.getActionCommand().equals("Tracer itinéraire")){
			
			panel_search = new Recherche();
			panel_search.setVisible(false);			

			Itineraire itn = new Itineraire();
			itn.start();
			while(itn.getWrited() == false){
			}			
			
			String folder = itn.getFolder();
			String im = folder + "/mapview.png";
			File Im = new File(im);

			panorama = new Map(panel_search,im);
			panorama.setVisible(true);
			panorama.setMode("Visualisation");
			panorama.repaint();
			contentPane.removeAll(); 
			contentPane.add(panorama,"Center");
			contentPane.add(panel_search,"West");
			enableMenu(true);
			setVisible(true);	
		}		
		else if(e.getActionCommand().equals("Annuler tout")){
			if(panorama.getMode() == "Panorama"){
				panorama.cancelAll();
			}
		}
		else if(e.getActionCommand().equals("Annuler")){
			if(panorama.getMode() == "Panorama"){
				panorama.cancelOne();
			}
		}
		else if(e.getActionCommand().equals("Sauvegarder l'image")){
			panorama.saveImage();
			ShowInfo("Le panorama a été placé dans le dossier courant");
		}
		
		else if(e.getActionCommand().equals("Sauvegarder les positions")){
			if(panorama.getMode() == "Panorama"){
				panorama.savePin();
			}
		}
		
		else if(e.getActionCommand().equals("Ouvrir"))
		{
			System.out.println("Ouvrir");
				
			JFileChooser chooser = new JFileChooser();
    			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
    			chooser.setFileFilter(filter);
   			
   			int returnVal = chooser.showOpenDialog(this.getParent());
    			
    			if(returnVal == JFileChooser.APPROVE_OPTION)
    			{
       				System.out.println("Vous avez choisi d'ouvrir le fichier : " +chooser.getSelectedFile().getName());
       				
       				String path = chooser.getSelectedFile().getAbsolutePath();
       				System.out.println(path);
       				
				panel_search = new Recherche();
				panel_search.setVisible(false);		
				panorama =  new Map(panel_search,path);
				panorama.setMode("Visualisation");
				enableMenu(true);
				contentPane.removeAll(); 
				contentPane.add(panorama,"Center");
				contentPane.add(panel_search,"West");				
        			
        			setVisible(true);
    			}				
		}
		else if(e.getActionCommand().equals("Charger"))
		{
			//sélection du dossier
			JFileChooser chooser1 = new JFileChooser(); 
			chooser1.setCurrentDirectory(new java.io.File("."));
			chooser1.setDialogTitle("Séléctionner le dossier contenant les photos et le gps");
			chooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
			chooser1.setAcceptAllFileFilterUsed(false);
			if(chooser1.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){ 
				String Photo = chooser1.getSelectedFile().getAbsolutePath() + "/map.jpg";
				String pin = chooser1.getSelectedFile().getAbsolutePath() + "/positions.txt";
				String Lat = chooser1.getSelectedFile().getAbsolutePath() + "/latitude.txt";
				String Lon = chooser1.getSelectedFile().getAbsolutePath() + "/longitude.txt";

				Panorama pano = null;
				try{
					File fphoto = new File (Photo);
					File fpin = new File(pin);
					File flat = new File(Lat);
					File flon = new File(Lon);
					
					if (fphoto.exists() && fpin.exists()){
						//les deux dossiers nécéssaires existent tout est ok
						if ( panorama == null ){
							panel_search = new Recherche();
							panel_search.setVisible(true);		
							recherche = null;
							recherche = panel_search.getButton();
							recherche.addActionListener(this);
							panorama =  new Map(panel_search,Photo);
							panorama.setVisible(true);
							panorama.repaint();
							contentPane.removeAll(); 
							contentPane.add(panorama,"Center");
							contentPane.add(panel_search,"West");
							enableMenu(true);
							setVisible(true);	
						}
						else
						{
							panorama.loadImage(Photo);
						}
						panorama.readWork(pin);
					}
					
					readData(flat,flon,Lat,Lon);		
				} catch(Exception ek){
					ek.printStackTrace();
				}								
			}
		}	
		/////////// Matlab actions mcr requis ici ///////////////////
		else if(e.getActionCommand().equals("Créer"))
		{
			Panorama mypano = null;		
			//contentPane.removeAll();			
			//Création d'un nouveau panorama avec les paramètres par défaut
			JFileChooser chooser1 = new JFileChooser(); 
			chooser1.setCurrentDirectory(new java.io.File("."));
			chooser1.setDialogTitle("Séléctionner le dossier contenant les photos et le gps");
			chooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
			chooser1.setAcceptAllFileFilterUsed(false);

			if(chooser1.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){ 
				String Photo = chooser1.getSelectedFile().getAbsolutePath(); //+ "/Photo";
				Object folderphoto[] = new Object[1];
				Object configpano[] = new Object[1];
				String Lat = chooser1.getSelectedFile().getAbsolutePath() + "/latitude.txt";
				String Lon = chooser1.getSelectedFile().getAbsolutePath() + "/longitude.txt";
				String Config = "resources/Conf/Panorama.conf";	
			
				try{
					File fphoto = new File (Photo);
					File flat = new File(Lat);
					File flon = new File(Lon);					
					if (fphoto.exists()){
						progressebarre.setVisible(true);
						//les deux dossiers nécéssaires existent tout est ok
						folderphoto[0] = Photo;
						configpano[0] = Config;
						
						String mosaique = Photo + "/mosaique.png";		
						try{
							System.out.println("Accès au Matlab Computer Runtime");
							mypano = new Panorama();	
							System.out.println("Accès réussi");									
							mypano.stitch(folderphoto[0],configpano[0]);
							progressebarre.setVisible(false);
						} catch(MWException ei){
							ei.printStackTrace();
						} finally{
							mypano.dispose();
						}
				
						panel_search = new Recherche();
						panel_search.setVisible(true);
						recherche = null;
						recherche = panel_search.getButton();
						recherche.addActionListener(this);			
						panorama =  new Map(panel_search,mosaique);
						readData(flat,flon,Lat,Lon);
						panorama.setMode("Panorama");
						enableMenu(true);
						
						contentPane.removeAll();
						contentPane.add(panorama,"Center");
						contentPane.add(panel_search,"West");				
	        				setVisible(true);
					}
					else
					{
						System.out.println("Impossible de trouver les dossiers nécéssires");
						//Boîte du message d'erreur
						ShowError("Le dossier choisit ne contient pas les dossiers\nPHoto et Gps permettant de reconstruire la zone.");			
					}
				} catch(Exception ea){
					ea.printStackTrace();
				}				
			}
		}     		
     		else if(e.getActionCommand().equals("Recherche"))
		{     			
     			String latitude = "";
     			String longitude = "";
     			
     			latitude = panel_search.getLatitude();
     			longitude = panel_search.getLongitude();
     			
     			double lat = Double.parseDouble(latitude);
     			double lon = Double.parseDouble(longitude);
     			
     			System.out.println("latitude : "+lat+" longitude : "+lon);
     			
     			panorama.searchResult(lat, lon);	
     		}		
	}

        /**
         * Permet d'afficher un message d'erreur
         *
	 * @param erreur
         *                Le message d'erreur à afficher
         * 
         */
	public void ShowError(String erreur){
		JOptionPane Erreur = new JOptionPane();
		Erreur.showMessageDialog(null, erreur, "Erreur", JOptionPane.ERROR_MESSAGE);		
	}

        /**
         * Permet d'afficher un message d'information
         * 
         * @param message
         *            Le message a afficher
         * 
         */
	public void ShowInfo(String message){
		JOptionPane info = new JOptionPane();
		info.showMessageDialog(null,message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	*	Activer / Désactiver les menus
	*	@param b
	*		un boolean pour savoir si on doit activer certains éléments
	*/
	public void enableMenu(boolean b){
		//Tous les menus ne doivent pas être activés, cela dépend du mode
		for(JMenuItem m : activableMenu ){
			m.setEnabled(b);
		}
	}
	/**
	*	Lire les fichiers de coordonnée
	*	@param flat
	*		le fichier de latitude
	*	@param flon
	*		le fichier de longitude
	*	@param LAT
	*		Le nom du fichier latitude
	*	@param LON
	*		Le nom du fichier longitude	
	*/
	public void readData(File flat,File flon,String LAT,String LON){
		if (flat.exists() && flon.exists()){
			convertTab coord = new convertTab(flat,flon);
			Thread tcoord = new Thread(coord);		
			tcoord.start();
			while(tcoord.isAlive()){
			}
				
			int ligne = coord.getLigne();
			int colonne = coord.getColonne();
			LATITUDE = new double[ligne][colonne];
			LONGITUDE = new double[ligne][colonne];						
			LATITUDE = coord.getLAT();
			LONGITUDE = coord.getLON();
			
			panorama.RecordCoord(LATITUDE,LONGITUDE,LAT,LON);
			panorama.setMode("Panorama");
		}	
	}
}

