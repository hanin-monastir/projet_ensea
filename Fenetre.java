import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.Serializable;
import java.io.*;

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
         * Matrice utilisée pour la conversion des données Matlab 
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
		setIconImage(new ImageIcon(this.getClass().getResource("map_icone.png")).getImage());
		setLocation(0, 0);
		
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		int hauteur = (int)tailleEcran.getHeight();
		int largeur = (int)tailleEcran.getWidth();
		
		setSize(tailleEcran);
		
		
		JMenuBar m = new JMenuBar();
		//////////////////// Menu Fichier //////////////////////////////////
		JMenu menu1 = new JMenu("Fichier");

		JMenuItem ouvrir = new JMenuItem("Ouvrir");
		ouvrir.addActionListener(this);
		menu1.add(ouvrir);
		
		JMenuItem creer = new JMenuItem("Créer");
		creer.addActionListener(this);
		menu1.add(creer);	
	
		JMenuItem charger = new JMenuItem("Charger");
		charger.addActionListener(this);
		menu1.add(charger);
		
		JMenuItem sauver = new JMenuItem("Sauvegarder l'image");
		sauver.addActionListener(this);
		menu1.add(sauver);

		JMenuItem sauverpin = new JMenuItem("Sauvegarder les positions");
		sauverpin.addActionListener(this);
		menu1.add(sauverpin);

		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.addActionListener(this);
		menu1.add(quitter);		

		//////////////////// Menu Édition /////////////////////////////////
		JMenu menu2 = new JMenu("Édition");

		JMenuItem annulertout = new JMenuItem("Annuler tout");
		annulertout.addActionListener(this);
		menu2.add(annulertout);			

		JMenuItem annulerun = new JMenuItem("Annuler");
		annulerun.addActionListener(this);
		menu2.add(annulerun);			
	
		/////////////////// ITINERAIRE /////////////////////////////////////
		JMenu menu3 = new JMenu("Itinéraire");	
	
		JMenuItem itineraire = new JMenuItem("Tracer itinéraire");
		itineraire.addActionListener(this);
		menu3.add(itineraire);

		JMenuItem configitineraire = new JMenuItem("Configurer itinéraire");
		configitineraire.addActionListener(this);
		menu3.add(configitineraire);

		//////////////////// Ajout /////////////////////////////////////////
		m.add(menu1);
		m.add(menu2);
		m.add(menu3);
		setJMenuBar(m);
			
		contentPane = getContentPane();
		
		///Essai de disposition : Panneau Recherche
		panel_search = new Recherche();
		recherche = panel_search.Bouton;
		recherche.addActionListener(this);
		panorama = new Map(panel_search);
		
		

		progressebarre = new ProgressWindows();
		progressebarre.setVisible(false);		
		progressebarre.setLocationRelativeTo(null);
		//contentPane.add(progressBar,"North");
		contentPane.add(panorama,"Center");		
		contentPane.add(panel_search, "West");
		
		panel_search.setVisible(true);				
		
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
		else if(e.getActionCommand().equals("Configurer itinéraire")){
			Configuration conf = new Configuration();
		}
		else if(e.getActionCommand().equals("Tracer itinéraire")){
			/*System.out.println("Google va retourner une image");
			double[] latt = new double[4];
			double[] lont = new double[4];

				latt[0] = 45.8535567;
				lont[0] = 6.615413;

				latt[1] = 45.8535567; 
				lont[1] = 6.617413;

				latt[2] = 45.8545567; 
				lont[2] = 6.617413;

				latt[3] = 45.8545567; 
				lont[3] = 6.615413;
			*/
			Itineraire itn = new Itineraire();
			if(itn.getImage() !=  null){
				panorama.loadImage(itn.getImage());
			}
		}
		else if(e.getActionCommand().equals("Annuler tout")){
			panorama.cancelAll();
		}
		else if(e.getActionCommand().equals("Annuler")){
			panorama.cancelOne();
		}
		else if(e.getActionCommand().equals("Sauvegarder l'image")){
			//panel_search.toString("positions.txt");
			panorama.saveImage();
			ShowInfo("Le panorama a été placé dans le dossier courant");
		}
		
		else if(e.getActionCommand().equals("Sauvegarder les positions")){
			panorama.savePin();
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
				panel_search.setVisible(true);		
				panorama =  new Map(panel_search,path);
				
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
				try{
					File fphoto = new File (Photo);
					File fpin = new File(pin);
				
					if (fphoto.exists() && fpin.exists()){
						//les deux dossiers nécéssaires existent tout est ok
						panorama.loadImage(Photo);
						panorama.readWork(pin);
					}
				} catch(Exception ek){
					ek.printStackTrace();
				}								
			}
		}	
		/////////// Matlab actions mcr requis ici ///////////////////
		else if(e.getActionCommand().equals("Créer"))
		{
			Panorama mypano = null;		
			Object[] coord = null;
			MWArray ltarray = null;
			MWArray lnarray = null;

			//Création d'un nouveau panorama avec les paramètres par défaut
			JFileChooser chooser1 = new JFileChooser(); 
			chooser1.setCurrentDirectory(new java.io.File("."));
			chooser1.setDialogTitle("Séléctionner le dossier contenant les photos et le gps");
			chooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
			chooser1.setAcceptAllFileFilterUsed(false);

			if(chooser1.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){ 
				String Photo = chooser1.getSelectedFile().getAbsolutePath() + "/Photo";
				String Gps = chooser1.getSelectedFile().getAbsolutePath() + "/Gps";
				Object folderphoto[] = new Object[1];
				Object foldergps[] = new Object[1];
				Object extension[] = new Object[1];				

				try{
					File fphoto = new File (Photo);
					File fgps = new File(Gps);
				
					if (fphoto.exists() && fgps.exists()){
						//les deux dossiers nécéssaires existent tout est ok
						folderphoto[0] = Photo;
						foldergps[0] = Gps;
						extension[0] = "*.png";

						String mosaique = Photo + "/mosaique.png";
						progressebarre.setVisible(true);			
						repaint();
						try{
							System.out.println("Début");
							mypano = new Panorama();							
							mypano.Panorama(folderphoto[0],extension[0],foldergps[0]);
							coord = mypano.GetMatrix(2);
							ltarray = (MWNumericArray) coord[0];
							lnarray = (MWNumericArray) coord[1];
							System.out.println("ligne " + ltarray.getDimensions()[0] + " Colonne " + lnarray.getDimensions()[1]);				
							int i = 0;
							int j = 0;
							int ligne = ltarray.getDimensions()[0];
							int colonne = ltarray.getDimensions()[1];
							LATITUDE = new double[ligne][colonne];
							LONGITUDE = new double[ligne][colonne];
							for (i=2;i<ligne;i++){
								for (j=1;j<colonne;j++){
									int[] idx = new int[]{i,j};
									LATITUDE[i][j] = (double) ltarray.get(idx);
									LONGITUDE[i][j] = (double) lnarray.get(idx); 
								}
							}
						} catch(MWException ei){
							ei.printStackTrace();
						} finally{
							mypano.dispose();
						}
				
						progressebarre.setVisible(false);
						panel_search = new Recherche();
						panel_search.setVisible(true);
						recherche = null;
						recherche = panel_search.getButton();
						recherche.addActionListener(this);			
						panorama =  new Map(panel_search,mosaique);
						panorama.RecordCoord(LATITUDE,LONGITUDE,ltarray,lnarray);
			
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
     			System.out.println("Ca marche !!!!");
     			
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
}

