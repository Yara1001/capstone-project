import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HangmanGame {
	
	 public static String[] spaceWords = {"comet", "cosmos", "crater", "galaxy", "meteor", "moon", "planet", "orbit", "nebula", "gravity", "lunar", "space", "star", "eclipse", "crescent", "flare", "void", "stellar", "rocket", "shuttle", "halo", "ring", "solar", "capsule"};
	 public static String[] hardSpaceWords = {"asteroid", "astronaut", "meteorite", "satellite", "hemisphere", "universe", "telescope", "atmosphere", "equinox", "starburst", "interstellar", "celestial", "solstice", "constellation", "fusion", "vacuum", "stardust", "galactic", "cosmology", "wavelength", "nuetron", "dimension", "supernova"};
	 
	 private String spaceWord;
	 private String shownWord;
	 
	 private boolean isGameEasy;
	 
	 public HangmanGame() { 
		 isGameEasy = true;
		 setWord(spaceWords);
	 }
	 
	 public void setWord(String[] array) {
		 int index = (int)(Math.random()*array.length);
		 spaceWord = array[index];
		 shownWord = "_".repeat(spaceWord.length());
	 }
	 
	 public String getWord() {
		 return spaceWord;
	 }
	 
	 public String getShownWord() {
		 return shownWord;
	 }
	 
	 public boolean isLetterInWord(char letter) {
		 return spaceWord.contains("" + letter);
	 }
	 
	 public void revealLetter(char letter) {
		 int index;
		 String lettersNotGuessed = spaceWord;
		 if (spaceWord.indexOf(letter) != -1) {
			 while(lettersNotGuessed.indexOf(letter) != -1) {
 				 index = lettersNotGuessed.indexOf(letter);
 				 if(index == 0) {
 					shownWord = letter + shownWord.substring(index + 1);
 	 				lettersNotGuessed = "*" + lettersNotGuessed.substring(index + 1);
 				 } else if(index == spaceWord.length()-1) {
 					shownWord = shownWord.substring(0, index) + letter;
 	 				lettersNotGuessed = lettersNotGuessed.substring(0, index) + "*";
 				 } else {
 				 	 shownWord = shownWord.substring(0, index) + letter + shownWord.substring(index + 1);
 				 	 lettersNotGuessed = lettersNotGuessed.substring(0, index) + "*" + lettersNotGuessed.substring(index + 1);
 				 }
			 }
		 }
	 }
	 
	 public void setToHard() {
		 isGameEasy = false;
	 }
	 
	 public void removeWord() {
		 if (isGameEasy) {
			 List<String> list = new ArrayList<String>(Arrays.asList(spaceWords));
			 list.remove(spaceWord);
			 spaceWords = list.toArray(new String[0]);
		 } else {
			 List<String> list = new ArrayList<String>(Arrays.asList(hardSpaceWords));
			 list.remove(spaceWord);
			 hardSpaceWords = list.toArray(new String[0]);
		 }
	 }
}
