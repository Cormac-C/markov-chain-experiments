import java.io.*;
//Looks three letters back	
public class MarkovChainDepthThree {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();;
		
		char letter1 = ' ';
		char letter2 = ' ';
		char letter3 = ' ';
		//char nextLetter = ' ';
		int stateLocation1 = -1;
		int stateLocation2 = -1;
		int stateLocation3 = -1;
		//Probabilities of moving from the state on the top to the state on the right
		char[]possibleStates = {' ','.','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		int[][][][] probabilities = OccurancesToProbabilities(LetterOccurancesFinder());
		
		//Initial choice
		boolean nextProbabilitiesExist = false;
		int randNum = (int)(Math.random()*(possibleStates.length));
		
		while (!nextProbabilitiesExist) {
			randNum = (int)(Math.random()*(possibleStates.length));
			stateLocation1 = randNum;
			letter1 = possibleStates[stateLocation1];
			
			
			randNum = (int)(Math.random()*(possibleStates.length));
			stateLocation2 = randNum;
			letter2 = possibleStates[stateLocation2];
			
			
			randNum = (int)(Math.random()*(possibleStates.length));
			stateLocation3 = randNum;
			letter3 = possibleStates[stateLocation3];//changed stateLocation2 to stateLocation3
			
			
			int total = 0;
			for (int i = 0; i < probabilities.length; i ++) {
				total += probabilities[stateLocation1][stateLocation2][stateLocation3][i];
			}
			if (total > 0) {
				nextProbabilitiesExist = true;
			}
		}
		System.out.print(letter1);	
		System.out.print(letter2);
		System.out.print(letter3);
		
		//Length controls number of characters listed
		int length = 10000;
		//Printing of characters
		
		for (int i = 0; i < length; i ++) {
			randNum = (int)(Math.random()*1000);
			//System.out.println(randNum);
			int total = 0;
			boolean stateFound = false;
			//Deciding which character to print, generates a number between 0 and 999(inclusive)
			//Each percentage gives the character a number and 
			//if the random number is within the range of the character then it is printed
			for (int j = 0; j < possibleStates.length && !stateFound; j++) {
				if (randNum < total + probabilities[stateLocation1][stateLocation2][stateLocation3][j] /*&& stateLocation1+stateLocation2>0*/) {
					
					letter1 = letter2;
					letter2 = letter3;
					letter3 = possibleStates[j];
					
					stateLocation1 = stateLocation2;
					stateLocation2 = stateLocation3;
					stateLocation3 = j;
					stateFound = true;
					System.out.print(possibleStates[stateLocation3]);
				}
				total += probabilities[stateLocation1][stateLocation2][stateLocation3][j];
			}
			
			if ((i+1) % 150 == 0) {
				System.out.println("");
			}
		}
	    long end = System.currentTimeMillis();;
	    System.out.println("Runtime");
	    System.out.println((end - start) + " ms");
		//PrintNiceTable (probabilities, possibleStates1);
	}
	//So bad below
	public static void PrintNiceTable (int[][][]array, char[] label) {
		char[]possibleStates = {' ','.','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		for (int i = 0; i < possibleStates.length; i++) {
			for(int j = 0; j < possibleStates.length; j++) {
				System.out.print(possibleStates[i]);
				System.out.print(", ");
				System.out.println(possibleStates[j]);
				for (int k = 0; k < possibleStates.length; k++) {
					System.out.print(possibleStates[k]);
					System.out.print(" ");
				}
				System.out.println("");
				for (int k = 0; k < possibleStates.length; k++) {
					System.out.print(array[i][j][k]);
					System.out.print(" ");
				}
				System.out.println("");	
			}
		}
	}
	public static int[][][][]OccurancesToProbabilities (int[][][][]occurancesArray){
		int[][][][]probabilities = new int[occurancesArray.length][occurancesArray.length][occurancesArray.length][occurancesArray.length];
		//Changed to three up to here
		for (int i = 0; i < occurancesArray.length; i++) {
			for (int j = 0; j <occurancesArray.length; j++) {
				for (int k = 0; k<occurancesArray.length; k++) {
					double totalLettersAfter = 0.0;
					//Find total
					for (int l = 0; l < occurancesArray.length; l ++) {
						totalLettersAfter += occurancesArray[i][j][k][l];
					}
					//Divide by total, then turn to percentage
					
					for (int l = 0; l < occurancesArray.length; l ++) {
						if (totalLettersAfter>0) {
							probabilities[i][j][k][l] = ((int)((occurancesArray[i][j][k][l]/totalLettersAfter)*1000));	
						}else {
							probabilities[i][j][k][l] = 0;
						}
					}
				}
			}
		}
		
		return probabilities;
	}
	public static int[][][][] LetterOccurancesFinder () {
		String allCharacters = " .ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		//char[]possibleStates = {' ','.','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		int[][][][] occurancesAfterLetter = new int[54][54][54][54];
		for (int i = 0; i < 54; i++) {
			for(int j = 0; j < 54; j++) {
				for(int k = 0; k < 54; k++) {
					for(int l = 0; l < 54; l ++) {
						occurancesAfterLetter[i][j][k][l] = 0;
					}
				}
			}
		}
		try {
			String entireFileText = "";
			//RandomAccessFile f = new RandomAccessFile("C://Users//mrcor//Desktop/Text Files/MobyDick.txt", "r");
			//RandomAccessFile f = new RandomAccessFile("C://Users//mrcor//Desktop/Text Files/TheOdyssey.txt", "r");
			//RandomAccessFile f = new RandomAccessFile("C://Users//mrcor//Desktop/Text Files/GermanBook.txt", "r");
			//RandomAccessFile f = new RandomAccessFile("C://Users//mrcor//Desktop/Text Files/EnglishLiterature.txt", "r");
			RandomAccessFile f = new RandomAccessFile("C://Users//Owner//Documents//Text Files/EnglishLiterature.txt", "r");

			String line = f.readLine();
            while (line != null) {
                entireFileText += line;
                line = f.readLine();
            }
			f.close();
			for (int i = 0; i < entireFileText.length()-3; i++) {
				if(//Filtering for only letters, spaces, and periods
				((int)(entireFileText.charAt(i))>=65 && (int)(entireFileText.charAt(i))<=90)||
				((int)(entireFileText.charAt(i))>=97 && (int)(entireFileText.charAt(i))<=122)||
				((int)(entireFileText.charAt(i))==32)||((int)(entireFileText.charAt(i))==46)
				){
					//Finding frequency
					int letter1 = -1;
					int letter2 = -1;
					int letter3 = -1;
					int letter4 = -1;
					letter1 = allCharacters.indexOf(entireFileText.charAt(i));
					letter2 = allCharacters.indexOf(entireFileText.charAt(i+1));
					letter3 = allCharacters.indexOf(entireFileText.charAt(i+2));
					letter4 = allCharacters.indexOf(entireFileText.charAt(i+3));
					if(letter1>=0 && letter2>=0 && letter3>=0 && letter4>=0) {
						occurancesAfterLetter[letter1][letter2][letter3][letter4] ++;
					}
				}
			}	
		}
		catch(IOException error) {
			System.out.println("Error opening the text file");
		}
		return occurancesAfterLetter;
	}

}
