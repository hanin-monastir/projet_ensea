import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class ProgressWindows extends JFrame{

	ProgressWindows(){
		super();
		Locale currentLocale = Locale.getDefault();
		String locale = currentLocale.getLanguage();
		String country = currentLocale.getCountry();
        	ResourceBundle messages;
        	currentLocale = new Locale(locale, country);
        	String path = "resources/locales/" + locale + "/ProgressWindows"; 
        	messages = ResourceBundle.getBundle(path, currentLocale);	
		String titre = messages.getString("titre");
		String creation = messages.getString("creation");
	
		setTitle(titre);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(400,30);
		
		JProgressBar progressBar;
		progressBar = new JProgressBar(0,100);
		
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);
		progressBar.setString(creation);	
		progressBar.setVisible(true);
		
		Container contentPane = getContentPane();
		contentPane.add(progressBar,"Center");
		
	}

}
