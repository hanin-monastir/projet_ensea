import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.* ;
import java.lang.* ;

/**
* Constructeur de fichier Kml pour le partage de carte et des coordonnées des breakpoints
* <p>
* Cette classe permet de créer un fichier kml comportant tout les breakpoints calculés dans la classe
* Itinéraire. Elle permet une meilleure collaboration car le fichier kml généré peut directement être ouvert
* dans google earth ou google map.
* Autre force de ce type de partage, elle permet une sauvegarde et l'outrepassage de la limite de caractère 
* imposé par Google.
* </p>
* 
*/
public class KmlWriter{
	/**
	*	Constructeur de la classe
	*	@param f
	*		la liste de breakpoints
	*	@param file
	*		le fichier où sera enregistré les données
	*/
	KmlWriter(ArrayList<double[]> f, String file,double[] lat,double[] lon){
		try { 
			/*
				Création du document kml
			*/
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			//création de l'entete kml avec un attribut xmlns
			Element rootElement1 = doc.createElement("kml");
			doc.appendChild(rootElement1);
			Attr atk = doc.createAttribute("xmlns");
			atk.setValue("http://www.opengis.net/kml/2.2");
			rootElement1.setAttributeNode(atk);
			
			//création de l'élement document
			Element rootElement2 = doc.createElement("Document");
			rootElement1.appendChild(rootElement2);
			
			//création des placemarks
			for(double[] p : f){
				Element root3 = doc.createElement("Placemark");
				rootElement2.appendChild(root3);
				//ajout du nom
				Element nm = doc.createElement("name");
				String nom = "Breakpoint " + f.indexOf(p);
				nm.appendChild(doc.createTextNode(nom));
				root3.appendChild(nm);
				
				//ajout des données additionnelles
				Element exdata = doc.createElement("ExtendedData");
				root3.appendChild(exdata);
				//ajout des sous champs lat/lon
				Element dtname = doc.createElement("Data");
				Attr atd = doc.createAttribute("name");
				String lt = "Latitude";
				atd.setValue(lt);
				dtname.setAttributeNode(atd);
				Element lati = doc.createElement("value");
				String vallat = p[0] + "";
				lati.appendChild(doc.createTextNode(vallat));
				dtname.appendChild(lati);
				
				Element dtname1 = doc.createElement("Data");
				Attr ate = doc.createAttribute("name");
				String lo = "Longitude";
				ate.setValue(lo);
				dtname1.setAttributeNode(ate);
				Element longi = doc.createElement("value");
				String vallon = p[1] + "";
				longi.appendChild(doc.createTextNode(vallon));
				dtname1.appendChild(longi);
				
				exdata.appendChild(dtname);
				exdata.appendChild(dtname1);
				//ajout du marqueur
				Element root4 = doc.createElement("Point");
				root3.appendChild(root4);
				Element root5 = doc.createElement("coordinates");
				String co = p[1] + ","+ p[0];
				root5.appendChild(doc.createTextNode(co));
				root4.appendChild(root5);
			}
			//écriture d'un rectanglesur la zone de survole
			Element region = doc.createElement("Placemark");
			Element nRegion = doc.createElement("name");
			nRegion.appendChild(doc.createTextNode("Region survolée"));
			region.appendChild(nRegion);
			Element polyRegion = doc.createElement("Polygon");
			region.appendChild(polyRegion);
			
			//def de la région
			Element extrude = doc.createElement("extrude");
			extrude.appendChild(doc.createTextNode("1"));
			polyRegion.appendChild(extrude);
		
			Element altitudeMode = doc.createElement("altitudeMode");
			altitudeMode.appendChild(doc.createTextNode("relativeToGround"));
			polyRegion.appendChild(altitudeMode);
			
			Element outBoundaryIs = doc.createElement("outerBoundaryIs");
			polyRegion.appendChild(outBoundaryIs);
			//definition des lignes
			Element linearRing = doc.createElement("LinearRing");
			outBoundaryIs.appendChild(linearRing);
			
			Element coord = doc.createElement("coordinates");
			String ligne = "";

			ligne = lon[0] + "," + lat[0] + "," + 0 + " ";
			coord.appendChild(doc.createTextNode(ligne));
			ligne = lon[3] + "," + lat[3] + "," + 0 + " ";
			coord.appendChild(doc.createTextNode(ligne));
			ligne = lon[2] + "," + lat[2] + "," + 0 + " ";
			coord.appendChild(doc.createTextNode(ligne));
			ligne = lon[1] + "," + lat[1] + "," + 0 + " ";
			coord.appendChild(doc.createTextNode(ligne));
			ligne = lon[0] + "," + lat[0] + "," + 0;
			coord.appendChild(doc.createTextNode(ligne));
			linearRing.appendChild(coord);
			rootElement2.appendChild(region);
			
			//dessin du chemin entre les breakpoints
			Element path = doc.createElement("Placemark");
			Element npath = doc.createElement("name");
			npath.appendChild(doc.createTextNode("Chemin parcouru"));
			path.appendChild(npath);
			Element dpath = doc.createElement("description");
			dpath.appendChild(doc.createTextNode("Le chemin parcouru par l'avion"));
			path.appendChild(dpath);
			Element styleUrl = doc.createElement("styleUrl");
			styleUrl.appendChild(doc.createTextNode("#yellowLineGreenPoly"));			
			path.appendChild(styleUrl);
			Element LineString = doc.createElement("LineString");		
			path.appendChild(LineString);
			Element Extrude = doc.createElement("extrude");
			Extrude.appendChild(doc.createTextNode("1"));		
			LineString.appendChild(Extrude);
			Element Tessellate = doc.createElement("tessellate");		
			Tessellate.appendChild(doc.createTextNode("1"));
			Element altitude = doc.createElement("absolute");
			LineString.appendChild(Tessellate);
			Element coords = doc.createElement("coordinates");
			String l = "";
			for(int j = 0;j<f.size()-1;j++){
				l = f.get(j)[1] + "," + f.get(j)[0] + "," + 0 + " ";
				coords.appendChild(doc.createTextNode(l));
			}
			l = f.get(f.size()-1)[1] + "," + f.get(f.size()-1)[0] + "," + 0;
			coords.appendChild(doc.createTextNode(l));
			
			LineString.appendChild(coords);
			rootElement2.appendChild(path);	
			//fin de l'écriture
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(file));

			transformer.transform(source, result);
 		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
