import java.io.*;
import javax.imageio.ImageIO;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.* ;
import java.lang.* ;

/**
 * <b>Itinéraire est la classe qui permet de calculer les différents points de passage de l'avion.</b>
 * @see Configuration
 * @see Flight
 * 
 * @author benoit Franquet Corentin Floch
 * @version 1.0
 */
public class Itineraire extends Thread{
	/**
	*	 Un tableau à 2 éléments de latitude qui sera utilisé pour calculer les caps et distance
	*/	
	private double[] latitude;
	/**
	*	 Un tableau à 2 éléments de longitude 
	*/
	private double[] longitude;
	/**
	*	l'adresse qui sera envoyé à google
	*/
	private String url;
	/**
	*	L'image retournée par google
	*/	
	private BufferedImage map;
	/**
	*	Le facteur de zoom demandé à google
	*/
	private int zoom;
	/**
	*	Le type de vue demandée satellite par défaut
	*/
	private String vue;
	/**
	*	taille de l'image demandée
	*/
	private int size;
	/**
	*	la distance qui servira lors des calculs de breakpoints
	*/	
	private double distance;
	/**	
	*	Une arraylist qui contiendra des breakpoints intermédiaires
	*/
	private ArrayList<double[]> breakpoint;
	/**
	*	une arraylist qui contiendra les breakpoints d'un premier coté du champs
	*/
	private ArrayList<double[]> base1;
	/**
	*	une arraylist qui contient les breakpoints du deuxième coté
	*/
	private ArrayList<double[]> base2;
	/**
	*	l'arraylist finale qui contiendra le parcourt
	*	google peut refuser de retourner l'itinéraire si sa taille est trop importante
	*/
	private ArrayList<double[]> finale;
	/**
	*	Arraylist qui contient que le chemin liant les bords et ne contenant pas les breakpoints intermédiare
	*/
	private ArrayList<double[]> chemin;
	/**
	*	Arraylist pour les breakpoints de l'arc de cercle
	*/
	private ArrayList<double[]> arc;
	/**
	*	Tableau qui contiendra les latitudes lors du chargement du fichier de config	
	*/
	private double[] lat;
	/**
	*	Tableau qui contiendra les longitudes lors de la lecture du fichier de config
	*/
	private double[] lon;
	/**
	*	Espace inter-breakponts
	*/
	private double espace;	
	/**
	*	Espace inter-breakpont angulaire lors des demi-tours
	*/
	private int angle;
	/**
	*	nombre de breakpoint nécéssaire, calculé dans le constructeur
	*/
	private int nbkp;
	/**
	*	Permet de connaitre le sens de parcours
	*/
	private String sens;
	/**
	*	Enregistre l'ancien cap voir si utiie pour trouver les breakpoints dans les arcs de cercle
	*/
	private double oldcap;
	/**
	*	Cap actuel
	*/
	private double cap;
	/**
	*	Le dossier d'enregistement
	*/
	private String folder;
	/**
	* 	Savoir si le chargement a eu ieu
	*/
	private boolean chargement ;
	/**
	*	La map  ou afficher l'image
	*/
	private Map carte;
	
	/**
	*	aire de la zone
	*
	*/
	private int aireZone;
	
	/**
	*	Une liste représentant l'importance du point
	*
	*/
	private ArrayList<int[]> areaRisk;
	
	/**
	*	Zone d'importance en ligne droite
	*/
	private int distLine;
	/**
	*	Zone d'influence à l'amorce des demi-tours
	*/
	private int distKey;
	/**
	*	Zone d'influence dans les demis tours
	*/
	private int distArc;
	/**
	*	Importance des breakpoints dans les lignes droites
	*/
	private int infLine;
	/**	
	*	Importance des breakpoints à l'amorce des virages
	*/
	private int infKey;
	/**
	*	Importance des breakpoints dans les demi-tours
	*/
	private int infArc;
	/**
	*	Distance minimale entre deux lignes lors d'un demi-tours
	*	Peut être vue comme le diamètre minimal du cercle de demi tours
	*/
	private int minDiam;
	/**
	*	Tentative pour corriger un problème
	*
	*/
	private String iswrited;
	/**
	*	String pour l'internationalisation
	*
	*/
	private String erreur;
	private String errorGoogle;
	private String aire;
	private String Distance;
	private String Nombre;
        /**
         * Constructeur Itineraire
         * <p>
         * A la construction d'un objet Itinéraire, on charge les paramètres présent dans le fichier
	 * itinéraire.txt
	 * On construit une adresse web compréhensible par Google, regroupant le passage de l'avion path et 
	 * les markeurs.
	 * Le calcul de la position des markeurs se fait en interne, par calcul de cap et grâce à la distance
	 * demandée par l'utilisateur
         * </p>
         * 
         * @param m
         *	La map sur laquelle l'itinéraire va s'afficher
         *
         */
	Itineraire(){
		Locale currentLocale = Locale.getDefault();
		String locale = currentLocale.getLanguage();
		String country = currentLocale.getCountry();
        	ResourceBundle messages;
        	currentLocale = new Locale(locale, country);
        	String path = "resources/locales/" + locale + "/Itineraire"; 
        	messages = ResourceBundle.getBundle(path, currentLocale);
        			
		erreur = messages.getString("erreur");
		errorGoogle = messages.getString("errorGoogle");
		aire = messages.getString("aire");
		Distance = messages.getString("Distance");
		Nombre = messages.getString("Nombre");

		//4 points seront nécéssaires
		sens = "positif";
		vue = "satellite";
		iswrited = "false";
				
		latitude = new double[2];
		longitude = new double[2];
		lat = new double[4];
		lon = new double[4];
		chargement = loadConfig();
		correctMistake();
	}
	
	/**
	*	Redefinition de la fonction run de la clsse thread
	*	Cette fonction permet de calculer les breakpoints, ainsi que d'obtenir la map par Google
	*	tout ceci en arrière plan afin de ne pas paralyser l'interface graphique.
	*
	*	@see Fenetre
	*
	*/
	public void run(){
		/*
			Deux fichiers doivent être chargés
			Le fichier de configuration de l'itinéraire ie Itineraire.conf
			et le fichier de config de vol Flight.conf
		*/
		if(chargement){ 
			double[] bkp = new double[2];
			double[] p1 = new double[2];
			double[] p2 = new double[2];
			//Finale contient tout les breakpoints
			finale = new ArrayList<double[]>();
			//Chemin contient uniquement les breakpoints dans les demi-tours
			chemin = new ArrayList<double[]>();
			
			//obtention de la première base
			p1[0] = lat[0]; p1[1] = lon[0] ;
			p2[0] = lat[3]; p2[1] = lon[3] ;
			
			getBasePoint(p1,p2);
			base1 = new ArrayList<double[]>(breakpoint);		
			breakpoint.clear();
			
			//utile our le calcul de l'aire
			aireZone = (int)distance;
		
			//on obtient les points de la deuxième base
			p1[0] = lat[1] ; p1[1] = lon[1] ;
			p2[0] = lat[2]; p2[1] = lon[2] ;
			getBasePoint(p1,p2);
			base2 = new ArrayList<double[]>(breakpoint);		
			breakpoint.clear();
			
			areaRisk = new ArrayList<int[]>();	
			int[] influency = new int[2];
			//on a maintenant les deux bases ie les breakpionts de deux cotés opposés
			//pour chaque couple de breakpoint de la base on applique la méthode
			if (base1.size() == base2.size() && !base1.isEmpty()){
				//utile pour obtenir l'aire de la zone survolée
				int sizeB = 1;
				//les deux bases ont bien la même taille
				for (int i = 0;i<base1.size();i++){
					//on obtient les breakpoints entre deux éléments d'une bse
					getBasePoint(base1.get(i),base2.get(i));
					 		
					arc = new ArrayList<double[]>();
					//on ajoute les pins suivant le sens de parcourt

					if(sens == "positif" ){
						//on calcul les points de passage de l'arc
						getArcPoint(sens);

						//on ajoute les points de passage de l'arc au chemin
						for(int j = 0 ; j< arc.size();j++){
							chemin.add(arc.get(j));
							finale.add(arc.get(j));
							
							influency = new int[2];
							influency[0] = distArc;
							influency[1] = infArc;
							areaRisk.add(influency);
						}

						//on ajoute le point de la ligne droite
						chemin.add(breakpoint.get(0));
						finale.add(breakpoint.get(0));
						
						influency = new int[2];
						influency[0] = distKey;							
						influency[1] = infKey;
						areaRisk.add(influency);
						//on ajoute le tout à la liste finale
						for(int k = 1;k<breakpoint.size()-1;k++){
							finale.add(breakpoint.get(k));
						
							influency = new int[2];
							influency[0] = distLine;												
							influency[1] = infLine;
							areaRisk.add(influency);
						}
						//on ajoute le dernier point
						finale.add(breakpoint.get(breakpoint.size()-1));		
						chemin.add(breakpoint.get(breakpoint.size()-1));
						
						influency = new int[2];
						influency[0] = distKey;	
						influency[1] = infKey;
						areaRisk.add(influency);		
						sens = "négatif";
					}
					else
					{	
						getArcPoint(sens);	
			
						for(int j = arc.size()-1 ; j>=0 ;j--){
							chemin.add(arc.get(j));
							finale.add(arc.get(j));
							influency = new int[2];
							influency[0] = distArc;
							influency[1] = infArc;
							areaRisk.add(influency);							
						}				
						
						chemin.add(breakpoint.get(breakpoint.size()-1));
						finale.add(breakpoint.get(breakpoint.size()-1));
						influency=new int[2];
						influency[0] = distKey;							
						influency[1] = infKey;
						areaRisk.add(influency);	
											
						for(int k = breakpoint.size()-2;k>=1;k--){
							finale.add(breakpoint.get(k));
							influency = new int[2];
							influency[0] = distLine;												
							influency[1] = infLine;
							areaRisk.add(influency);							
						}
						finale.add(breakpoint.get(0));
						chemin.add(breakpoint.get(0));
						influency = new int[2];
						influency[0] = distKey;	
						influency[1] = infKey;
						areaRisk.add(influency);						
						sens = "positif";
					}
					sizeB = breakpoint.size() - 1;
					//on libère le breakpoint
					breakpoint.clear();
				}
				//Aire de la zone
				aireZone *=  distance*sizeB;
				
				//on construit l'adresse à envoyer à google mais google peut ne pas traiter l'adresse résultante
				computeUrl();
			}	
		}
		else
		{
			ShowError(erreur);	
		}
	}

	/**
	*	Construire l'adresse web pour obtenir via google map la zone
	*
	*/
	public void computeUrl(){
		url = "http://maps.googleapis.com/maps/api/staticmap?path=color:0x0000ff|weight:2|";		
		
		//on continue la construction de l'adresse
		for(int k = 0; k<chemin.size()-1;k++){
			url += (float)(chemin.get(k)[0]) + "," + (float)(chemin.get(k)[1]) + "|";
		}
					
		//on ajoute la dernière pin
		url += (float)(chemin.get(chemin.size()-1)[0]) + "," + (float)(chemin.get(chemin.size()-1)[1]) + "&";			
		if (zoom <= 19 && zoom>=1){
			url += "zoom=" + zoom;
		}
	

		url += "&" + "size=" + size + "x" + size + "&markers=label:P|";
	
		//on ajoute des marqeurs
		for(int k = 0; k<chemin.size()-1;k++){ 
			url += (float)(chemin.get(k)[0]) + "," + (float)(chemin.get(k)[1]) + "|";
		}
		url += (float)(chemin.get(chemin.size()-1)[0]) + "," + (float)(chemin.get(chemin.size()-1)[1]) +"&";			
			
		url +=  "&maptype=" + vue + "&sensor=true";
			
		try {
			//on doit d'abord vérifier la taille de l'url pour éviter les problème
			int tailleUrl = url.length();
			if(tailleUrl < 2048 ){
				//on récupère l'image envoyé par google
	 			map = ImageIO.read(new URL(url));
	 			//sauvegarde des données
	 			recordData();
	 			getTotalDistance();
	 		}
	 		else
	 		{
	 			recordData();
	 			ShowError(errorGoogle);
	 		}
		} catch(Exception e){
			System.out.println("taille " + url.length());
			e.printStackTrace();
		}
	}

	/**
	*	Calcul la distance entre 2 points grâce à leurs coordonnées
	*
	*/
	public void calculDistance(){
		//retourne la distance en mètre entre deux points
		distance = 1000*Math.acos(Math.sin(Math.PI/180*latitude[0])*Math.sin(Math.PI/180*latitude[1])+Math.cos(Math.PI/180*latitude[0])*Math.cos(Math.PI/180*latitude[1])*Math.cos(Math.PI/180*longitude[1]-Math.PI/180*longitude[0]))*6371;
	}

	/**
	*	Trouve le point milieu ente deux points
	*
	*/
	public void midPoint(){
		double lat1 = Math.PI/180*latitude[0];
		double lon1 = Math.PI/180*longitude[0];

		double lat2 = Math.PI/180*latitude[1];
		double lon2 = Math.PI/180*longitude[1];

		double Bx = Math.cos(lat2) * Math.cos(lon2-lon1);
		double By = Math.cos(lat2) * Math.sin(lon2-lon1);
		
		double lat3 = Math.atan2(Math.sin(lat1)+Math.sin(lat2), Math.sqrt((Math.cos(lat1)+Bx)*(Math.cos(lat1)+Bx) + By*By )); 
		double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);
	}

	/**
	*	Calcul le cap par rapport au Nord pour se rendre d'un point a à un point b
	*
	*/
	public double getBearing(){
		double lat1 = Math.PI/180*latitude[0];
		double lon1 = Math.PI/180*longitude[0];
		double lat2 = Math.PI/180*latitude[1];
		double lon2 = Math.PI/180*longitude[1];

		double dLon = lon2 - lon1;

		double y = Math.sin(dLon) * Math.cos(lat2);
		double x = Math.cos(lat1)*Math.sin(lat2) - Math.sin(lat1)*Math.cos(lat2)*Math.cos(dLon);
		double bearing = Math.atan2(y, x);
		return bearing;
	}

	/**
	*	Fonction pour obtenir les breakpoints entre deux points
	*	@param p1
	*		Le point numero 1
	*	@param p2
	*		Le point numero 2
	*
	*/	
	public void getBasePoint(double[] p1,double[] p2){
		latitude[0] = p1[0];
		longitude[0] = p1[1];
		latitude[1] = p2[0];
		longitude[1] = p2[1];
			
		//on créé la liste de breakpoint	
		breakpoint = new ArrayList<double[]>();
		
		double[] bkp = new double[2];
	
		bkp[0] = latitude[0];
		bkp[1] = longitude[0];		
		breakpoint.add(bkp);
		//on calcul la distance entre les deux points
		calculDistance();
		//on calcul le nombre de breakpoint nécéssaire en fonction de l'espace choisit
		nbkp = (int)(distance/espace);
		
		//on obtient la liste de breakpoint
		getBreakPoint();
	}

	/**
	*	Calcul la position des breakpoints
	*
	*/
	public void getBreakPoint(){
		double d = espace;
		double R = 6371*1000;
		double brng = getBearing();

		double lat0 = latitude[0];
		double lon0 = longitude[0];

		for(int i = 0;i<nbkp;i++){
			double lat1 = Math.PI/180*lat0;
			double lon1 = Math.PI/180*lon0;
		
			double lat2 = Math.asin(Math.sin(lat1)*Math.cos(d/R) + Math.cos(lat1)*Math.sin(d/R)*Math.cos(brng));
			double lon2 = lon1 + Math.atan2(Math.sin(brng)*Math.sin(d/R)*Math.cos(lat1), Math.cos(d/R)-Math.sin(lat1)*Math.sin(lat2));
			
			lat2 *= 180/Math.PI;
			lon2 *= 180/Math.PI;

			lat0 = lat2;
			lon0 = lon2;
				
			//pour finir on ajoute le breakpoint	
			double[] bkp = new double[2];
			bkp[0] = lat2;
			bkp[1] = lon2; 	

			breakpoint.add(bkp);
		}

		double[] bkp = new double[2];
		bkp[0] = latitude[1];
		bkp[1] = longitude[1]; 	
		breakpoint.add(bkp);
	}
	
	/**
	*	Tentative pour obtenir des markeurs formant un arc de cercle en bout de chemin
	* 	@param sens
	* 		Le sens de parcours de l'avion
	*
	*/
	public void getArcPoint(String sens){
		//on travail sur les deux jeux de points
		if(!finale.isEmpty() && !breakpoint.isEmpty()){
			//On recherche le centre du cercle 
			latitude[0] = finale.get(finale.size()-1)[0];
			longitude[0] = finale.get(finale.size()-1)[1];
		
			double brng = 0;
			double[] bk = new double[2];
			if( sens == "positif" ){
				bk = breakpoint.get(0);
				latitude[1] = bk[0];
				longitude[1] = bk[1];
				brng = getBearing();
			}
			else
			{
				bk = breakpoint.get(breakpoint.size()-1);
				latitude[1] = bk[0];
				longitude[1] = bk[1];
				brng = getBearing();
			}
			/*
				Orientation est l'angle (l'azimut) entre la droite formée par 
				une droite de référence ie le Nord et deux points
				Son calcul permet de déterminer l'angle à rajouter pour pouvoir tracer des
				ititnéraires pas uniquement horizontaux
			*/			
			int orientation = (int)(180/Math.PI*brng) % 180;
			System.out.println("Cap "+ orientation);	
			calculDistance();
			double d = distance/2;
			double R = 6371*1000;
			
			double lat0 = latitude[0];
			double lon0 = longitude[0];
			double lat1 = Math.PI/180*lat0;
			double lon1 = Math.PI/180*lon0;

			double clat = Math.asin(Math.sin(lat1)*Math.cos(d/R) + Math.cos(lat1)*Math.sin(d/R)*Math.cos(brng));
			double clon = lon1 + Math.atan2(Math.sin(brng)*Math.sin(d/R)*Math.cos(lat1), Math.cos(d/R)-Math.sin(lat1)*Math.sin(clat));
			
						
			//on obtient le centre du cercle idéal
			clat *= 180/Math.PI;
			clon *= 180/Math.PI;	
			
			int fin = 0;
			int debut = 0; 
			if(sens == "positif"){
				fin = orientation - 180;
				debut = orientation;
			}
			else
			{
				fin = -orientation;
				debut = -orientation -180;
			}
			
			/*
				Explication :
				Pour pouvoir travailler avec plusieurs modèles d'avion possédant chacun
				un angle maximale de 1/2 tours, si l'espace entre deux lignes s'avère
				inférieure à la distance minimale autorisée par l'avion on essai de faire un cercle plus 
				grand entre les lignes.			
			
			*/
			if(distance < minDiam){
				//Distance du centre du nouveau cercle à l'ancien centre de cercle
				d = Math.sqrt(Math.pow(minDiam/2,2)-Math.pow(distance/2,2));
				
				//Calcul le cap vers lequel doit être positionné le cap
				latitude[0] = finale.get(finale.size()-2)[0];
				longitude[0] = finale.get(finale.size()-2)[1];
				latitude[1] = finale.get(finale.size()-1)[0];
				longitude[1] = finale.get(finale.size()-1)[1];
				brng = getBearing();
				
				//Calcul la position exacte du cercle
				lat0 = clat;
				lon0 = clon;
				lat1 = Math.PI/180*lat0;
				lon1 = Math.PI/180*lon0;
				clat = Math.asin(Math.sin(lat1)*Math.cos(d/R) + Math.cos(lat1)*Math.sin(d/R)*Math.cos(brng));
				clon = lon1 + Math.atan2(Math.sin(brng)*Math.sin(d/R)*Math.cos(lat1), Math.cos(d/R)-Math.sin(lat1)*Math.sin(clat));							
				//on obtient le centre du cercle convenable
				clat *= 180/Math.PI;
				clon *= 180/Math.PI;
				distance = minDiam;
				//met à jours orientation
				latitude[0] = clat;
				longitude[0] = clon;
				bk = new double[2];
				if( sens == "positif" ){
					bk = breakpoint.get(0);	
				}
				else
				{
					bk = breakpoint.get(breakpoint.size()-1);
				}

				latitude[1] = bk[0];
				longitude[1] = bk[1];
				brng = getBearing();
				//Calcul du cap pour joindre le centre du cercle et le premier point
				int f1= (int)(180/Math.PI*brng) ;

				//met à jours l'angle de fin
				latitude[1] = finale.get(finale.size()-1)[0];
				longitude[1] = finale.get(finale.size()-1)[1];
				brng = getBearing();
				//Calcul du cap joignant le 2ème point et le centre
				int f2 = (int)(180/Math.PI*brng)  ;
				
				//Attribution des nouvelles valeurs debut et fin sur le cercle
				if( sens == "positif" ){
					if(f2<0){
						f2 = -f2;
					}
					debut = f1-angle;
					fin = f1 - 360 + Math.abs(f1-f2)+angle;
				}
				else
				{
					f2 = -f2 ;
					f1 = -f1;
					debut = f2 + angle;
					fin = f2+360-Math.abs(f2-f1);
				}
				System.out.println(f1 + " " + f2 + " " + debut + " " + fin + " "  + Math.abs(f1 - f2));
			}
		
			
					
			//on trouve des points sur le cercle
			double[]  n = null;
			if(sens == "positif"){
				for (int i = debut ; i > fin ; i -= angle){
					n = new double[2];
					n[0] = clat + (distance/(2*111000) * Math.cos(i * Math.PI / 180));
					n[1] = clon + (distance/(2*76000) * Math.sin(i * Math.PI / 180));
					arc.add(n);
				}
				Collections.reverse(arc);			
			}
			else
			{
				for (int i = debut ; i < fin ; i += angle){
					n = new double[2];
					n[0] = clat + (distance/(2*111000) * Math.cos(i * Math.PI / 180));
					n[1] = clon - (distance/(2*76000) * Math.sin(i * Math.PI / 180));
					arc.add(n);	
				}
				Collections.reverse(arc);
			}
		}
	}
	

	/**
	*	Charger la configuration si elle existe
	*	@return Un booléen montrant l'état du chargement
	*
	*/
	public boolean loadConfig(){
		boolean chargement=false;
		try{		
			File monFichier = new File("resources/Conf/Itineraire.conf");
			if (monFichier.exists()){			
				FileReader fichierlu = new FileReader(monFichier);
				BufferedReader bufferlu = new BufferedReader(fichierlu);
				String ligne = "";
				String[] resultat = null;
	
				while ((ligne = bufferlu.readLine()) != null) {	
					resultat = ligne.split(" ");
					switch(resultat[0]){				
						case "Zoom" : 
								zoom = Integer.parseInt(resultat[1]);
								break;
						case "Espace" : 
								espace = Integer.parseInt(resultat[1]);
								break;
						case "Taille"  : 
								size = Integer.parseInt(resultat[1]);
								break;
						case "Point1" :
								lat[0] = Double.parseDouble(resultat[2]);
								lon[0] = Double.parseDouble(resultat[4]);
								break;
						case "Point2" :
								lat[1] = Double.parseDouble(resultat[2]);
								lon[1] = Double.parseDouble(resultat[4]);
								break;
						case "Point3" :
								lat[2] = Double.parseDouble(resultat[2]);
								lon[2] = Double.parseDouble(resultat[4]);
								break;
						case "Point4" :
								lat[3] = Double.parseDouble(resultat[2]);
								lon[3] = Double.parseDouble(resultat[4]);
								break;
						case "Dossier" : folder =  resultat[1];
								break;
						
						case "Angle" : angle = Integer.parseInt(resultat[1]);
								break;								
						default : break;
					}
				}
			}
			else
			{
				chargement=false;
				return chargement;
			}
			
			//chargement des paramètres de vol
			monFichier = new File("resources/Conf/Flight.conf");
			if (monFichier.exists()){			
				FileReader fichierlu = new FileReader(monFichier);
				BufferedReader bufferlu = new BufferedReader(fichierlu);
				String ligne = "";
				String[] resultat = null;
				while ((ligne = bufferlu.readLine()) != null){	
					resultat = ligne.split(" ");
					switch(resultat[0]){
						case "ligne" : 
							infLine = Integer.parseInt(resultat[1]);
							distLine = Integer.parseInt(resultat[2]);
							break;
						case "inter" : 
							infKey = Integer.parseInt(resultat[1]);
							distKey = Integer.parseInt(resultat[2]);
							break;
						case "arc"  : 
							infArc = Integer.parseInt(resultat[1]);
							distArc = Integer.parseInt(resultat[2]);
							break;
						case "diametre" : 
							minDiam = Integer.parseInt(resultat[1]);
							break;
						default : break;
					}
				}	
			}
			else
			{
				chargement = false;
				return chargement;
			}
			
			chargement = true;
		} catch(Exception ei){
			ei.printStackTrace();
		}
		return chargement;		
	}

	/**
	*	Permet de retourner la liste de breakpoints
	*	@return La liste de breakpoints finale comportant les breakpoints situés en ligne droite
	*
	*/
	public ArrayList<double[]> getListBreakPoint(){
		return finale;
	}		

	/**
	*	Permet de retourner l'image Google Map
	*	@return l'image de Google Map
	*
	*/		
	public BufferedImage getImage(){
		return map;
	}
	/**
	*	Montrer les messages d'erreur
	*	@param erreur
	*		Le message d'erreur à afficher si il n'y a pas eu chargement
	*
	*/
	public void ShowError(String erreur){
		final JOptionPane Erreur = new JOptionPane();
		Erreur.showMessageDialog(null, erreur, "Erreur", JOptionPane.ERROR_MESSAGE);		
	}
	/**
	*	Sauvegarder les données
	*	@return une chaine montrant l'état du composant
	*
	*/
	public String recordData(){
		//enregistement automatique des données dans le dossier spécifié
		if(folder != "" && folder != null){
			//enregistrement des breakpoints et de l'image
			String listeBkp = folder + "/breakpoints.txt";
			String Carte =  folder + "/mapview.png";

			try{
				File ancien_iti = new File(Carte);
				if(ancien_iti.exists()){
					ancien_iti.delete();
				}

				//image
				FileOutputStream fos = new FileOutputStream(Carte);
				if(map != null){

					ImageIO.write(map,"png",fos);
					while( ! ancien_iti.exists()){
						//attendre que le thread ait fini d'écrire le fihcier'
					}
					iswrited = "true";
				}
				else
				{
					iswrited = "impossible";
				}
			
				//breakponts
				File monFichier = new File(listeBkp);
				BufferedWriter bw = new BufferedWriter(new FileWriter(monFichier)) ;
				String ligne = "";
				for(double[] p : finale){
					int i = finale.indexOf(p);
					double alpha = 0;
					if (i != finale.size()-1){
						latitude[0] = p[0];
						longitude[0] = p[1];
						latitude[1] = finale.get(i+1)[0];
						longitude[1] = finale.get(i+1)[1];
						alpha = getBearing();
					}
					else
					{
						latitude[0] = finale.get(i-1)[0];
						longitude[0] = finale.get(i-1)[1];
						latitude[1] = p[0];
						longitude[1] = p[1];					
						alpha = getBearing();
					}
					alpha *= 180/Math.PI;
					ligne = p[0] + " " + p[1] + " " + alpha + " " +  areaRisk.get(i)[0] + " " +areaRisk.get(i)[1];
					bw.write(ligne,0,ligne.length());
					bw.newLine();
					bw.flush();  
				}
				bw.close();
				
				//on enregistre en KML
				KmlWriter kmlw = new KmlWriter(finale,folder + "/map.kml",lat,lon);
			} catch(Exception e){
				e.printStackTrace();
			}	
		}
		return "fini";		
	}
	
	/**
	*	Fonction pour corriger les erreurs de saisie de l'utilisateur
	*	lors de la rentrée des coordonnnée des 4 points.
	*
	*/
	public void correctMistake(){
		if(lat[0] > lat[3]){
			//Les cotés sont inversé, on échange 0/3 et 1/2	
			System.out.println("point0 et point3");
			switchTab(0,3);
			switchTab(1,2);					
		}	
		else if(lon[0] > lon[1]){
			//on échange les points 0/1 2/3
			System.out.println("point0 et point1");
			switchTab(0,1);
			switchTab(2,3);						
		}		
	}

	/**
	*	Permet d'inverser deux élements d'un tableau
	*	@param i
	*		L'index du premier élément
	*	@param j
	*		L'index du second élément
	*
	*/	
	public void switchTab(int i, int j){
		double tempo = lon[i];
		lon[i] = lon[j];
		lon[j] = tempo;		
		tempo = lat[i];
		lat[i] = lat[j];
		lat[j] = tempo;
	}
	/**
	*	Afficher les infos
	*
	*/
	public void ShowInfo(String message){
		final JOptionPane info = new JOptionPane();
		info.showMessageDialog(null,message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	*	Fonction pour calculer la distance parcourue par l'avion
	*	et afficher diverses infos
	*	
	*/
	public void getTotalDistance(){
		int Dtotale = 0;
		for(int j = 0;j<finale.size()-1;j++){
			latitude[0] = finale.get(j)[0];
			longitude[0] = finale.get(j)[1];
			
			latitude[1] = finale.get(j+1)[0];
			longitude[1] = finale.get(j+1)[1];
			
			calculDistance();
			Dtotale += distance;
		}
		int nbligne = finale.size() - chemin.size()+2;
		float airePhoto = aireZone/nbligne;
		
		String info = aire +" " + aireZone +"\n" + Distance + " "+ Dtotale + "\n" + Nombre + " " + finale.size();		
		ShowInfo(info);
	}	
	
	/**
	*	Return le dossier d'enregistrment
	*
	*/
	public String getFolder(){
		return folder;
	}
	/**
	*	retourne ok si l'image a été écris
	*
	*/
	public String getWrited(){
		return iswrited;
	}			
}

