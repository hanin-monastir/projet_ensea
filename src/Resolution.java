import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class Resolution extends JFrame implements ActionListener{

	JSlider resolutionSlider;
	JButton validate;
	Container contentPane;
	Map carte;
	
	Resolution(Map m){
		
		super("Choix résolution");
		
		carte = m;
		
		setResizable(false);
		setSize(500,120);
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((tailleEcran.width - getWidth())/2, (tailleEcran.height - getHeight())/2);
		
		
		
		resolutionSlider = new JSlider(JSlider.HORIZONTAL,20,100,100);
		resolutionSlider.addChangeListener(new SliderListener());
		
		validate = new JButton("Ok");
		//affichage des labels	sur les sliders
		resolutionSlider.setMajorTickSpacing(10);
		resolutionSlider.setMinorTickSpacing(5);
		resolutionSlider.setPaintTicks(true);
		resolutionSlider.setPaintLabels(true);
		

		JPanel pan1 = new JPanel();
		pan1.setLayout(new GridLayout(3,1));
		pan1.add(new JLabel("Facteur % :"));
		pan1.add(resolutionSlider);
		
		JPanel pan2 = new JPanel();
		pan2.setLayout(new GridBagLayout());
		
		GridBagConstraints cpp = new GridBagConstraints();
		cpp.fill = GridBagConstraints.BOTH;
		cpp.insets = new Insets(1, 1, 1, 1);
		//cpp.weightx = 1;
		
		cpp.gridx = 1;
		cpp.gridy = 0;
		pan2.add(validate,cpp);
		validate.addActionListener(this);
		
		pan1.add(pan2);
		
		add(pan1);
		setVisible(false);
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getActionCommand().equals("Ok"))
		{
			System.out.println("Appui sur Ok");
			setVisible(false);	
			/*
			//Zone ou l'on va afficher l'image
			*/	
			
		}
	}			
}
