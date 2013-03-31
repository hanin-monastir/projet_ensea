import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


class SliderListener implements ChangeListener {

	private float pourcentage;
	
	public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();
	        if (!source.getValueIsAdjusting()) {
			pourcentage = source.getValue();
			pourcentage /= 100;
            		System.out.println(pourcentage +" ");
        	}    
    	}
    	 	
 	public float getCurrentValue(){
 		return pourcentage;
 	}  	
}
