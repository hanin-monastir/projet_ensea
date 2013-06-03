import java.io.Serializable;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* <b>Permet d'afficher la fenetre de configuration des paramètres de vol</b>
* @see Itineraire
*
* @author Franquet Benoit Corentin Floch
* @version 1.0
*
*/
public class Flight extends JFrame implements ActionListener {
	/**
	*	Les différents Sliders permetant de configurer les paramètres
	*
	*/
	private JSlider dline;
	private JSlider dkey;
	private JSlider darc;
	private JSlider iline;
	private JSlider ikey;
	private JSlider iarc;
	private JSlider diam;
	
	/**
	*	Constructeur de la classe
	*
	*/
	Flight(){
		super();
		//tentative internationalisation
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
        	String path = "resources/locales/" + locale + "/Flight"; 
        	messages = ResourceBundle.getBundle(path, currentLocale);
        			
	
		String titre = messages.getString("titre");
					
		setTitle(titre);
		setResizable(true);
		setSize(430,280);
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((tailleEcran.width - this.getWidth())/2, (tailleEcran.height - this.getHeight())/2);

		setIconImage(new ImageIcon(this.getClass().getResource("resources/Images/map_icone.png")).getImage());
		
		//bouton de sauvegarde
		String save = messages.getString("sauvegarde");
		JButton Sauvegarde = new JButton(save);
		Sauvegarde.addActionListener(this);	
		
		//Les différents labels
		String tarc = messages.getString("arc");
		String tkey = messages.getString("key");
		String tligne = messages.getString("ligne");
		String tinfluence = messages.getString("influence");
		String timportance = messages.getString("importance");
		String tdiametre = messages.getString("diametre");
		JLabel larc = new JLabel(tarc);
		JLabel lkey = new JLabel(tkey);
		JLabel lligne = new JLabel(tligne);
		JLabel linfluence = new JLabel(tinfluence);
		JLabel limportance = new JLabel(timportance);
		JLabel ldiametre = new JLabel(tdiametre);
		
		//Les différents paramètes de taille
		dline = new JSlider(JSlider.HORIZONTAL,20,100,20);
		dkey = new JSlider(JSlider.HORIZONTAL,20,100,20);
		darc = new JSlider(JSlider.HORIZONTAL,20,100,20);	
		iline = new JSlider(JSlider.HORIZONTAL,1,3,1);
		ikey = new JSlider(JSlider.HORIZONTAL,1,3,1);
		iarc = new JSlider(JSlider.HORIZONTAL,1,3,1);
		diam = new JSlider(JSlider.HORIZONTAL,10,100,10);
		
		//affichage des textes
		dline.setMajorTickSpacing(20);
		dline.setPaintTicks(true);
		dline.setPaintLabels(true);
		
		dkey.setMajorTickSpacing(20);
		dkey.setPaintTicks(true);
		dkey.setPaintLabels(true);
		
		darc.setMajorTickSpacing(20);
		darc.setPaintTicks(true);
		darc.setPaintLabels(true);
		
		iline.setMajorTickSpacing(1);
		iline.setPaintTicks(true);
		iline.setPaintLabels(true);
		
		iarc.setMajorTickSpacing(1);
		iarc.setPaintTicks(true);
		iarc.setPaintLabels(true);
		
		ikey.setMajorTickSpacing(1);
		ikey.setPaintTicks(true);
		ikey.setPaintLabels(true);
		
		diam.setMajorTickSpacing(20);
		diam.setMinorTickSpacing(10);
		diam.setPaintTicks(true);
		diam.setPaintLabels(true);
					
		//Lecture des paramètres
		readParameters();			
		//réglage du layout
		setLayout(new GridBagLayout());
		
		//JPanel influence
		JPanel PInfluence = new JPanel();
			PInfluence.setLayout(new GridBagLayout());
			GridBagConstraints cps = new GridBagConstraints();	
			cps.fill = GridBagConstraints.BOTH;
			cps.insets = new Insets(3, 3, 3, 3);
			cps.weightx = 1;
			cps.gridx = 1;
			cps.gridy = 0;
			PInfluence.add(limportance,cps);
			cps.gridx = 2;
			PInfluence.add(linfluence,cps);
			cps.gridx = 0;
			cps.gridy = 1;
			PInfluence.add(lligne,cps);
			cps.gridy = 2;
			PInfluence.add(lkey,cps);
			cps.gridy = 3;
			PInfluence.add(larc,cps);
			cps.gridx = 1;
			cps.gridy = 1;
			PInfluence.add(iline,cps);
			cps.gridx = 2;
			PInfluence.add(dline,cps);
			cps.gridx = 1;
			cps.gridy = 2;
			PInfluence.add(ikey,cps);
			cps.gridx = 2;
			PInfluence.add(dkey,cps);
			cps.gridx = 1;
			cps.gridy = 3;
			PInfluence.add(iarc,cps);
			cps.gridx = 2;
			PInfluence.add(darc,cps);
		
		//Panel Diamètre
		JPanel Diametre = new JPanel();	
		Diametre.setLayout(new GridBagLayout());
		GridBagConstraints cpp = new GridBagConstraints();
		
		cpp.fill = GridBagConstraints.BOTH;
		cpp.insets = new Insets(3, 3, 3, 3);
		cpp.weightx = 1;
		cpp.gridx = 0;
		cpp.gridy = 0;
		Diametre.add(ldiametre,cpp);
		cpp.gridy = 1;
		Diametre.add(diam,cpp);
		
		//Organisation générale
		GridBagConstraints generale = new GridBagConstraints();
		generale.fill = GridBagConstraints.BOTH;
		generale.insets = new Insets(3, 3, 3, 3);
		generale.weightx = 1;
		generale.gridx = 0;
		generale.gridy = 0;	
		add(PInfluence,generale);
		generale.gridy = 1;
		add(Diametre,generale);	
		generale.gridy = 2;
		generale.fill = GridBagConstraints.VERTICAL;
		add(Sauvegarde,generale);			
		setVisible(true);
	}
	
	/**
	*	Fonction qui permet de capter les évènements 
	*
	* 	@param e
	*		Un évènement
	*
	*/
	public void actionPerformed(ActionEvent e){
		toString("resources/Conf/Flight.conf");
		setVisible(false);
	}
	
	/**
	*	Permet d'enregistrer les données
	*	@param f
	*		Le fichier ou sera enregisté les données
	*/
	public String toString(String f){
		try{		
			File monFichier = new File(f);
			BufferedWriter bw = new BufferedWriter(new FileWriter(monFichier)) ;
			String ligne = "";
			//Sauvegarde de l'influence et diamètre des lignes
			ligne = "ligne " + iline.getValue() + " " + dline.getValue();
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();
			
			//Sauvegarde des keys
			ligne = "inter " + ikey.getValue() + " " + dkey.getValue();
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();
			
			//Sauvegarde des arcs
			ligne =  "arc " + iarc.getValue() + " " + darc.getValue();
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();
			
			//Sauvegarde du diamètre
			ligne = "diametre " + diam.getValue();
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
        * 	Permet de charger les paramètres précédement enregistrés
        * 
        */
	public void readParameters(){
		try{		
			File monFichier = new File("resources/Conf/Flight.conf");
			if (monFichier.exists()){			
				FileReader fichierlu = new FileReader(monFichier);
				BufferedReader bufferlu = new BufferedReader(fichierlu);
				String ligne = "";
				String[] resultat = null;
	
				while ((ligne = bufferlu.readLine()) != null) {	
					System.out.println("lg " + ligne.length());
					resultat = ligne.split(" ");
					switch(resultat[0]){				
						case "ligne" : 
								iline.setValue(Integer.parseInt(resultat[1]));
								dline.setValue(Integer.parseInt(resultat[2]));
								break;
						case "inter" : 
								ikey.setValue(Integer.parseInt(resultat[1]));
								dkey.setValue(Integer.parseInt(resultat[2]));
								break;
						case "arc"  : 
								iarc.setValue(Integer.parseInt(resultat[1]));
								darc.setValue(Integer.parseInt(resultat[1]));
								break;
						case "diametre" : 
								diam.setValue(Integer.parseInt(resultat[1]));
								break;
						default : break;
					}
				}
			}
		} catch(Exception ei){
			ei.printStackTrace();
		}
	}	
}
