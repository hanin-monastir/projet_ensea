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
 * <b>optionMatlab est la classe qui permet de configurer la création du panorama.</b>
 * <p>
 * Elle permet de régler plusieurs paramètres importants lors de la création du panorama.
 * <ul>
 * <li>Les limites en hauteur et largeur des images</li>
 * <li>La zone geographique en UTM</li>
 * <li>Le choix d'utiliser ou non le recollement optimal'</li>
 * </ul>
 * </p>
 * <p>
 * Les paramètres sont enregistrés dans un fichier Panorama.conf
 * </p>
 * 
 * @author benoit Franquet Corentin Floch
 * @version 1.0
 */
 
public class optionMatlab extends JFrame implements ActionListener{
	/**
	*	Les sliders pour définir les tailles limites des images pour le redimensionnement	
	*/
	private JSlider LimitX;
	private JSlider LimitY; 
	/**
	*	La zone UTM a renseignée
	*
	*/
	private JTextField ZoneUTM;
	/**
	*	Savoir si on doit utiliser ou non la reconstruction optimale
	*/
	private JCheckBox OptimalBuild;
	
	/**
	*	String pour l'internationalisation
	*/
	private String sauver;
	
	/**
	*	Constucteur de la classe optionMatlab
	*	
	*/
	optionMatlab(){
		super();
		Locale currentLocale = Locale.getDefault();
		String locale = currentLocale.getLanguage();
		String country = currentLocale.getCountry();
        	ResourceBundle messages;
        	currentLocale = new Locale(locale, country);
        	String path = "resources/locales/" + locale + "/optionMatlab"; 
        	messages = ResourceBundle.getBundle(path, currentLocale);
        		
		String titre = messages.getString("titre");
		String coordUTM = messages.getString("coordUTM");
		String hauteur = messages.getString("hauteur");
		String largeur = messages.getString("largeur");
		String build = messages.getString("build");
		sauver = messages.getString("sauvegarde");
		
		setTitle(titre);
		setResizable(false);
		setSize(440,250);
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((tailleEcran.width - this.getWidth())/2, (tailleEcran.height - this.getHeight())/2);
		
		setIconImage(new ImageIcon(this.getClass().getResource("resources/Images/map_icone.png")).getImage());
		
		/**
		*	Les différents labels
		*/
		JLabel CoordUTM = new JLabel(coordUTM);
		JLabel Hauteur = new JLabel(hauteur);
		JLabel Largeur = new JLabel(largeur);
		String Build = build;
		
		/**
		*	Les champs de texte
		*/			
		ZoneUTM = new JTextField();
		
		/**
		*	Les checkBox et boutons 
		*/
		OptimalBuild = new JCheckBox(Build);
		OptimalBuild.setSelected(false);
		
		JButton sauvegarde = new JButton(sauver);
		sauvegarde.addActionListener(this);
		
		/**
		*	Les sliders
		*/
		LimitX = new JSlider(JSlider.HORIZONTAL,100,2500,1350);
		LimitY = new JSlider(JSlider.HORIZONTAL,100,2000,1100);		 		
		
		LimitX.setMajorTickSpacing(500);
		LimitX.setMinorTickSpacing(250);
		LimitX.setPaintTicks(true);
		LimitX.setPaintLabels(true);
		
		LimitY.setMajorTickSpacing(500);
		LimitY.setMinorTickSpacing(250);
		LimitY.setPaintTicks(true);
		LimitY.setPaintLabels(true);	
		/*
			Lecture des paramètres précédement enregistrés
		*/
		readParameters();
		
		//Agencement
		/*
			Réglage du layout
		*/
		setLayout(new GridBagLayout());
		GridBagConstraints contraintes = new GridBagConstraints();
		contraintes.fill = GridBagConstraints.BOTH;
		contraintes.insets = new Insets(3, 3, 3, 3);
		contraintes.weightx = 1;		
		contraintes.gridx = 0;	
		contraintes.gridy = 0;
		add(CoordUTM,contraintes);
		contraintes.gridy = 1;
		add(ZoneUTM,contraintes);
		contraintes.gridy = 2;
		add(Hauteur,contraintes);
		contraintes.gridy = 3;
		add(LimitY,contraintes);
		contraintes.gridy = 4;
		add(Largeur,contraintes);
		contraintes.gridy = 5;	
		add(LimitX,contraintes);
		contraintes.gridy = 6;			
		add(OptimalBuild,contraintes);	
		contraintes.gridy = 7;
		add(sauvegarde,contraintes);	
		/*
			on affiche la fenêtre
		*/		
		setVisible(true);		
	}		
	
	/**
	*	Capter l'appui sur un bouton
	*	@param e
	*		Un évènement
	*/
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals(sauver))
		{
			toString("resources/Conf/Panorama.conf");
			setVisible(false);			
		}
	}
	
	/**
	*	Enregistrer l'état du composant
	*	@param s
	*		Le nom du fichier où les donnees seront enregistrées
	*/
	public String toString(String s){
		try{
			File monFichier = new File(s);
			BufferedWriter bw = new BufferedWriter(new FileWriter(monFichier)) ;
			String ligne = "";
		
			ligne = "" + LimitX.getValue();
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();	
	
			ligne = "" + LimitY.getValue();
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush();			
			
			//enregistement de la zone utm
			String Zone = ZoneUTM.getText();
			String[] resultat = null;
			resultat = Zone.split(" ");
			bw.write(resultat[0],0,resultat[0].length());
			bw.newLine();
			bw.flush();
			
			int ascii = (int)(resultat[1].charAt(0));
			System.out.println(ascii);
			ligne = "" + ascii;
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush(); 
			
			int num = 0;
			if(OptimalBuild.isSelected()){
				num = 1;
			}
			ligne = "" + num;
			bw.write(ligne,0,ligne.length());
			bw.newLine();
			bw.flush(); 
		} catch(Exception e){
			e.printStackTrace();
		}		
		
		return "ok";		
	}		
	
	/**
	*	Lecture de la configuration
	*
	*/
	public void readParameters(){
		try{	
			File monFichier = new File("resources/Conf/Panorama.conf");
			if (monFichier.exists()){	
				FileReader fichierlu = new FileReader(monFichier);
				BufferedReader bufferlu = new BufferedReader(fichierlu);
				String ligne = "";
				String[] resultat = null;
				//1 ligne LimitX
				ligne = "" + bufferlu.readLine();
				LimitX.setValue(Integer.parseInt(ligne));
				System.out.println(Integer.parseInt(ligne));
				//2 ligne LimitY
				ligne = "" + bufferlu.readLine();
				LimitY.setValue(Integer.parseInt(ligne));
				//3 et 4 ligne ZoneUTM
				ligne = "" + bufferlu.readLine();
				int num = Integer.parseInt(ligne);
				ligne = bufferlu.readLine();
				char c = ((char) Integer.parseInt(ligne));
				String Zone = "" + num + " " + c;
				ZoneUTM.setText(Zone);
				//5 ligne reconstruction optimale
				ligne = "" + bufferlu.readLine();
				if(Integer.parseInt(ligne) == 1){
					OptimalBuild.setSelected(true);
				}
				else
				{
					OptimalBuild.setSelected(false);
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}	
}
