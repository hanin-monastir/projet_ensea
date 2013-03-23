import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class ProgressWindows extends JFrame{

	ProgressWindows(){
		super("Traitement en cours...");
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(400,30);
		
		JProgressBar progressBar;
		progressBar = new JProgressBar(0,100);
		
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);
		progressBar.setString("Création du panorama en cours. Veuillez patienter");	
		progressBar.setVisible(true);
		
		Container contentPane = getContentPane();
		contentPane.add(progressBar,"Center");
		
	}

}
