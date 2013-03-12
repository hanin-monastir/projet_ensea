
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.lang.*;
import java.util.*;

public class StraightLine{
	
	//on ne crée pas de nouvelle instance de Point
	Point p1;
	Point p2;
	
	StraightLine(Point a, Point b){
	
		p1 = a;
		p2 = b;
	}

	public boolean matchWith(Point a, Point b){
	
		if(p1 == a && p2 == b){
		
			return true;
		}
		
		else if(p1 == b && p2 == a){
		     
		     	return true;
		     }
		    
		else{
			return false;
		}
	
	}
	
	public boolean own(Point a){
	
		if(p1 == a || p2 == a){
		
			return true;
		}
		
		else{
			return false;
		}
	}

}

