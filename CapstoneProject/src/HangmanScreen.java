import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;


public class HangmanScreen extends JPanel implements CaretListener, ActionListener
{
	
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;

	private boolean paused;
	
	private JTextField guessLetterField;
	private JLabel guessLetterLabel;
	private String guessLetter;
	private JLabel timerLabel;
	
	private JPanel guessPanel;
	
	private JLabel shownWordLabel;
	private String shownWord;

	private HangmanGame game;
	public Timer t;
	private int timerCount;
	private int maxTime;
	
	private Sprite background;
	private Sprite alien1, alien2;
	private Sprite deadAstronaut;
	private Sprite correctMessage, wrongMessage;
	
	private int messageCount;
	
	private boolean isCorrect, isWrong;
	private boolean isGameOver;
	private boolean isWordGuessed;
	
	private SpaceMan m;

	public HangmanScreen (SpaceMan m) {
		super();
		
		this.m = m;
		
		game = new HangmanGame();
		shownWord = game.getShownWord();
		
		paused = true;
		maxTime = 45;
		
		background = new Sprite("background2.jpg", 0, 0, 800, 600);
		
		alien1 = new Sprite("alien.png", 700, 400, 75, 150);
		alien2 = new Sprite("alien.png", 60, 375, 75, 150);
		correctMessage = new Sprite("speechbubble_correct.png", 600, 260, 197, 170);
		wrongMessage = new Sprite("speechbubble_wrong.png", 600, 260, 197, 170);
		messageCount = -1;
		
		deadAstronaut = new Sprite("dead_astronaut.png", 350, 100, 103, 135);
		
		guessLetterLabel = new JLabel("Guess a Letter: ");
		guessLetterLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
		guessLetterLabel.setHorizontalAlignment(JTextField.CENTER);
		
		guessLetterField = new JTextField(2);
		guessLetterField.setFont(new Font("Monospaced", Font.BOLD, 20));
		guessLetterField.addCaretListener(this);
		guessLetterField.setHorizontalAlignment(JTextField.CENTER);
		
		guessPanel = new JPanel();
		guessPanel.setLayout(new FlowLayout());
		guessPanel.add(guessLetterLabel);
		guessPanel.add(guessLetterField);
		
		shownWordLabel = new JLabel(getShownWordWithSpacesBetween());
		shownWordLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
		shownWordLabel.setHorizontalAlignment(JTextField.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(guessPanel, BorderLayout.PAGE_START);
		this.add(shownWordLabel, BorderLayout.CENTER);
		
		t = new Timer(1000, (ActionListener) this); //Timer fires every 50 milliseconds
		t.setInitialDelay(1000);
		t.setRepeats(true);
		
		timerLabel = new JLabel(" 0:" + maxTime);
		timerLabel.setFont(new Font("Monospaced", Font.BOLD, 45));
		timerLabel.setHorizontalAlignment(JTextField.CENTER);
	
		this.add(timerLabel, BorderLayout.PAGE_END);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);  

		int width = getWidth();
		int height = getHeight();
		
		Graphics2D g2 = ((Graphics2D)g);

		double ratioX = (double)width/DRAWING_WIDTH;
		double ratioY = (double)height/DRAWING_HEIGHT;
		AffineTransform at = g2.getTransform();

		g2.scale(ratioX,ratioY);  
		
		background.draw(g, this);
		alien1.draw(g, this);
		alien2.draw(g, this);
		
		if (isCorrect) {
			correctMessage.draw(g, this);
		}
		
		if (isWrong) {
			wrongMessage.draw(g,  this);
		}
		
		if (isGameOver) {
			deadAstronaut.draw(g, this);
		}
		
		g2.setTransform(at);

	}

	public void caretUpdate(CaretEvent e) {
		guessLetter = guessLetterField.getText();
		if (!guessLetter.isEmpty() && guessLetter.length() == 1) {
			if (game.isLetterInWord(guessLetter.charAt(0))) {
				game.revealLetter(guessLetter.charAt(0));
				shownWord = game.getShownWord();
				shownWordLabel.setText(getShownWordWithSpacesBetween());
				
				isCorrect = true;
				isWrong = false;
				messageCount = 0;
			} else {
				isWrong = true;
				isCorrect = false;
				messageCount = 0;
				
				maxTime -= 1;
			}
		}
		
		if(game.getWord().equals(shownWord)) {
			maxTime = 45;
			isWordGuessed = true;
			t.stop();
			t.setInitialDelay(1500);
			t.restart();
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {	
		
		if (isWordGuessed) {
			t.stop();
			game.removeWord();
			setWord();
			shownWord = game.getShownWord();
			shownWordLabel.setText(getShownWordWithSpacesBetween());
			
			timerCount = 0;
			timerLabel.setText(" 0:" + maxTime);
			
			isWordGuessed = false;
			guessLetterField.setText("");
			
			messageCount = -1;
			isCorrect = false;
			isWrong = false;
			repaint();
			
			m.changePanel("1");

		} else {
			timerCount += 1;		
			if (timerCount == maxTime || timerCount > maxTime) {
				t.stop();
				
				messageCount = -1;
				isCorrect = false;
				isWrong = false;
				isGameOver = true;
				repaint();
				
				guessLetterField.setText("");
				guessLetterField.setEditable(false);
				guessLetterField.setVisible(false);
				
				timerLabel.setVisible(false);
				
				shownWordLabel.setText("<html>&nbsp;&nbsp;You Lost!<br/>The Word Was:<br/>" + getWordWithSpacesBefore  () + "</html>");
				guessLetterLabel.setText("Score: " + m.getScore());
			} else {
				if(messageCount == 0) {
					repaint();
					messageCount++;
				} else if(messageCount == 1) {
					isCorrect = false;
					isWrong = false;
					repaint();
					messageCount = -1;
				}
				
				if(maxTime - timerCount < 10) {
					timerLabel.setText(" 0:0" + (maxTime - timerCount));
				} else {
					timerLabel.setText(" 0:" + (maxTime - timerCount));
				}
			}
		}
	}
	
	public void setPaused(boolean p) {
		this.paused = p;
	}
	
	public void setWord() {
		if (m.getScore() <= 90) {
			game.setWord(HangmanGame.spaceWords);
		} else {
			game.setToHard();
			game.setWord(HangmanGame.hardSpaceWords);
		}
	}
	
	public String getWordWithSpacesBefore() {
		int numOfSpaces;
		int length = game.getWord().length();
		if(length % 2 == 0) {
			numOfSpaces = 6 - length/2;
		} else {
			numOfSpaces = (int)(6.5 - length/2);
		}
		return "&nbsp;".repeat(numOfSpaces) + game.getWord();
	}
	
	public String getShownWordWithSpacesBetween() {
		String shownWordWithSpaces = "";
		for (int i = 0; i < shownWord.length(); i++) {
			shownWordWithSpaces += shownWord.charAt(i) + " ";
		}
		return shownWordWithSpaces.trim();
	}

}
