import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import com.mathworks.toolbox.javabuilder.* ;
import Panorama.* ;

class SliderListener implements ChangeListener {

	private int pourcentage;
	private String image;
	private String newimage;
	
	public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();
	        if (!source.getValueIsAdjusting()) {
			pourcentage = (int)(100/source.getValue());
            		System.out.println(pourcentage+" "+image );
            		//Appel à la fonction matlab
            		/*
            		Object facteur[] = new Object[1];
			Object nom[] = new Object[1];
			Object path[] = new Object[1];
			
            		Panorama pano = null;	
            		try{
            			pano = new Panorama();
            			facteur[0] = pourcentage;
            			nom[0] = image;
            			path[0] = pano.SubImage(facteur[0],nom[0]);
            			newimage = (String)path[0];
            		} catch(MWException ei){
            			ei.printStackTrace();
            		} finally{
            			pano.dispose();            	
            		}
            		*/
        	}    
    	}
    	
    	public void setImage(String name){
    		image = name;
    	}
 	
 	public int getCurrentValue(){
 		return pourcentage;
 	}  
 	
 	public String getNewImage(){
 		return newimage;
 	} 	
}
