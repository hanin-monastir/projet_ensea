import java.util.*;

public class Test{

	public static void main(String [] args){
				Locale currentLocale = Locale.getDefault();
			String locale = currentLocale.getLanguage();
			String country = currentLocale.getCountry();	
			System.out.println(locale+" "+country);
		Fenetre fenetre = new Fenetre();
	}
}


