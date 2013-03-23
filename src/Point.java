public class Point{

	private int x;
	private int y;
	
/**
* Construit un point de coordonnees (a, b)
*
* @param a abscisse du point
* @param b ordonnée du point
*/
	public Point(int a, int b){
	x = a;
	y = b;
	
	}

	public Point(){
	}
	
/**
* retourne l'abscisse du point
*/
	public int getX(){
		return x;
	}

/**
* retourne l'ordonnee du point
*/
	public int getY(){
		return y;
	}


	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public String toString(){
		return "("+x+";"+y+")";
	}

}

