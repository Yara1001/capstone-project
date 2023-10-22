import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SpaceMan extends JFrame {
	
	JPanel cardPanel;
	private RunningGameScreen panel1;
	private HangmanScreen panel2;
	
	public SpaceMan(String title) {
		super(title);
		setBounds(100, 100, 640, 480);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    cardPanel = new JPanel();
	    CardLayout cl = new CardLayout();
	    cardPanel.setLayout(cl);
	    
		panel1 = new RunningGameScreen(this);    
	    panel2 = new HangmanScreen(this);
	    
	
	    cardPanel.add(panel1,"1"); // Card is named "1"
	    cardPanel.add(panel2,"2"); // Card is named "2"
	    
	    add(cardPanel);
	    addKeyListener(panel1);
	
	    setVisible(true);
	    
	}

	public static void main(String[] args) {
		
		SpaceMan w = new SpaceMan("SpaceMan");
		
	}
	
	public int getScore() {
		return panel1.score;
	}
	public void changePanel(String name) {
		
		if (name.equals("2")) {
			panel2.setPaused(false);
			panel2.t.start();
			panel1.setPaused(true);
			
		} else {
			panel2.setPaused(true);
			panel1.setPaused(false);
			
		}
		
		((CardLayout)cardPanel.getLayout()).show(cardPanel,name);
		requestFocus();
	}
	
	public HangmanScreen getPanel2() {
		return panel2;
	}

}