package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;
public class menu {
public static void main(String[] args) {
	char choice; // Create a new character choice to represent the users menu choice
	Scanner input = new Scanner(System.in); // Create a new scanner called input
	LinkedList<Player> players = new LinkedList<Player>(); // Create a new empty link list that will contain elements of the player class
	do { // Start a do while loop
		System.out.println("Please choose an option:" + "\n" // Display to the user a menu with 2  options, create a new player or quit
				+ "1. New Player" + "\n"
				+ "2. Quit");
		choice = input.next().charAt(0); // Assign choice the next character at position 0
		if (choice == '1') { // If choice equals character 1 then
			System.out.println("Please enter a name:"); // Prompt the user to enter a name
			String name = input.next(); // Read the next name from the console
			System.out.println("Please choose a difficulty:" + "\n" // Prompt the user to choose their difficulty easy medium or hard
					+ "1. VIP (easy)" + "\n"
					+ "2. Player (medium) - default" + "\n"
					+ "3. Limited (hard)");
			char diff = input.next().charAt(0); // Assign character diff the first character read from input
			Player p; // Initialize a new player p
			if (diff == '1') { // If the user enters character 1 then
				p = new VIPPlayer(name); // Assign p to a new VIP player
			} else if (diff == '3'){// else If the user enters character 3 then
				p = new LimitedPlayer(name); // Assign p to a new limited player
			} else{ // otherwise
				if (diff != '2') { // If the user does not enter character 2 then
					System.out.println("Invalid choic selecting default difficulty.."); // Indicate to the user that the default difficulty will be used
				}
				p = new Player(name); // Assign p to a new normal player
			}
			players.add(p); // Add the new player to the list of players
			String game; // Declare a new string game
			do { // Create another do while loop
				System.out.println("Hello " + p.getName() + ". Please choose a game, or -1 to quit:" + "\n" // Prompt the user to choose from one of the two games
						 + "1. Connect Four" + "\n"
						 + "2. X's and O's" + "\n");
				game = input.next(); // Read the next string from the console
				if (game.charAt(0) == '1') { // If the first character of the string game is 1 then
					connectFour(p, input); // Launch the connect four game
				} else if (game.charAt(0) == '2'){ // else If the first character of the string game is 2 then
					xsAndOs(p, input); // Launch the X’s and O’s game
				} else if (!game.equals("-1")){ // else If the first character of the string is not -1 then
					System.out.println("Invalid input, please try again."); // Display it to the user that this is an invalid input
				}	
			} while (!game.equals("-1")); // Continue to do while loop while the string does not equal -1
		}
	} while(choice != '2'); // Continue to do while loop while choice does not equal 2
	input.close(); // close the input scanner
	
	File file = new File("leaderboard.txt"); // Create a new file leaderboard 
	try { // Start a try catch block
		Scanner readfile = new Scanner(file); // Create a new scanner read file with input file
		while (readfile.hasNextLine()) { // Loop while  file has new line
			String line = readfile.nextLine(); // Assign line to the next line in the file
			String[] playerScore = line.split(" : ", 2); // Split the line at “ : “
			String player = playerScore[0]; // Set player to index 0
			int score = Integer.parseInt(playerScore[1]); // Set score to an integer at index one
			Player p = new Player(player, score); // Create a new player P with name player and score score using the two argument constructor
			players.add(p); // Add this new player to the players list
			
		}
		readfile.close(); // Close the scanner
	} catch (FileNotFoundException e) { // Catch exception file not found
		System.out.println("Leaderboard file does not exist, creating new leaderboard file..."); // Warn the user that the leaderboard file does not currently exist and a new one will be created
	}
	Collections.sort(players, new Sortbyscore()); // Start the players list by score using sort by score class
	System.out.println("Player : Points"); // Print leaderboard header
	String leaderboard = ""; // Assign leaderboard to an empty string
	for (int i = 0; i < players.size(); i++) { // Loop for each player in leaderboard list
		leaderboard += players.get(i).getName() + " : " + players.get(i).getScore() + "\n"; // Add the player name and player score to leaderboard string
	}
	System.out.println(leaderboard); // Print the leaderboard to console
	
	//  The following originates from https://www.w3schools.com/java/java_files_create.asp
	try { // Create a new try catch block
	      FileWriter myWriter = new FileWriter("leaderboard.txt"); // create a new file writer for leaderboard
	      myWriter.write(leaderboard); // Right the leaderboard string to file
	      myWriter.close(); // Close the file writer
    } catch (IOException e) { // Catch ioexception
      e.printStackTrace(); // Print the exception to console to determine cause
    }
}


public static void xsAndOs(Player player, Scanner input){ // Create a new public static method to represent the X’s and O’s game
	Boolean play = true; // Create a new boolean play and set to true for the do while loop
	int score = 0; // create a new integer score and set to 0 to represent the player score
	do { // Create a new do while loop
		System.out.println("Welcome to X's and O's!!"); // print a welcome message to the user
		String[][] board = {{" ", " ", " "},{" ", " ", " "},{" ", " ", " "}}; // Create a new multidimensional string array to represent the board
		displayBoardXsOs(board); // Use the display board method to print the board
		int guesses = 0;//  Set the number of guesses to 0
		do { // Create a new do while loop
			int row = 0; //Initialize new integer row
			int col = 0; // Initialize new integer col
			do { // Create a new do while loop
				boolean validrow=false; // set valid row boolean to false
				do { // new do while loop
					try { // new try catch clause to catch input mismatch
						System.out.print("Please select row (int): "); //Prompt the user to select a row
						row = input.nextInt(); // read row form console	
						validrow = true; // set valid row to true
					}catch(InputMismatchException e) {
						// from https://stackoverflow.com/questions/12702076/try-catch-with-inputmismatchexception-creates-infinite-loop
						input.next();// remove newline from scanner
					}
				}while(!validrow); // loop while row is not valid
				
				boolean validcol=false; // set valid col boolean to false
				do { // new do while loop
					try { // new try catch clause to catch input mismatch
						System.out.print("Please select col (int): "); //Prompt the user to select a col
						col = input.nextInt();	// read col form console	
						validcol = true; // set valid col to true
					}catch(InputMismatchException e) {
						// from https://stackoverflow.com/questions/12702076/try-catch-with-inputmismatchexception-creates-infinite-loop
						input.next();// remove newline from scanner
					}
				}while(!validcol); // loop while col is not valid
				
				if (!validGuessXsOs(board, row, col)){ // If the row column is not a valid guess then
					System.out.println("Not a valid guess please try again"); // Warn user not valid
				}
			} while(!validGuessXsOs(board, row, col)); // loop while the guess is not valid
			guesses++; // increment guesses by 1
			board = updateBoardXsOs(board, row, col, "X"); // Update the board with the new move
			displayBoardXsOs(board); // Use the display board method to print the board 
			if (winnerXsOs(board)) { // If a winner has been identified on the board then
				System.out.println("Congratulations you win!"); //Tell the player the won
				score = 4; // Set the winning score to 4 
				break; //  Break out of the while loop
			}
			if (guesses>= 9 && !winnerXsOs(board)) { //Is the number of guesses is larger or equal to 9 and there is no winner then
				System.out.println("Draw game!"); // Tell the user is a draw game
				score = 2; // Set the  score to 2
				break;  //  Break out of the while loop
			}
			int[] cRowCol = computerGuessXsOs(board); // Create a new single dimensional array for computer guess
			guesses++; // increment guesses by 1
			System.out.println("Computer guesses:\nRow: " + cRowCol[0] + "\nCol: " + cRowCol[1]); // print computer guess values
			board = updateBoardXsOs(board, cRowCol[0], cRowCol[1], "O"); // add the computer guess to the board
			displayBoardXsOs(board);// show the board
			if (winnerXsOs(board)) { // if there is a winner then
				System.out.println("You Loose!"); // tell the user they have lost
			}
		} while(guesses < 9 && !winnerXsOs(board)); // loop while the number of guesses is less than 9 and there is no winner
		
	int oldScore = player.getScore(); // get the players old score
	player.setScore(score); // add the new score achieved by the player
	int newScore = player.getScore(); // set the new score achieved by the player to int newScore
	
	System.out.println("You have earned " + (newScore - oldScore) + " points!"); // tell the player the score achieved, depends on the type of player class
	System.out.println("Your current score is " + newScore + " points!"); // tell the player their new score
	System.out.println("Play again? y/n"); // ask the player to play again

	char again = input.next().charAt(0); // read character from console
	again = Character.toLowerCase(again); // convert char to lowercase
	if (again != 'y') { // if character is not y then
		play = false; // set play to false
	}
} while(play); // loop while play is true
}

public static String[][] updateBoardXsOs(String[][] board, int row, int col, String val){ // create a new method to update X's and O's board
	board[row][col] = val; // set the row col of the board to the string val
	return board; // return the new board
}

public static void displayBoardXsOs(String[][] board) { // new void method to display the X's and O's board
	// display board
	for (int i = 0; i < board.length; i++) { // loop through each i in range of the rows
		if (i == 0) { // if the row is zero then
			System.out.println("  0 | 1 | 2 "); // print the column numbers for convience
		}
		for (int j = 0; j < board[i].length; j++) { // loop through each j in range of the cols in the row
			if (j == 0) { // if the column is zero then 
				System.out.print(i + " ");  // print the row number and space
			}
			System.out.print(board[i][j]); // print the board value at row i column hj
			if (j < 2) { // if the col number is less than 2 then
				System.out.print(" | "); // print | as seperator for cols
			}
		}
	System.out.println(); // print a newline for the end of the row
	if (i < 2) { // if row is less than 2 then
		System.out.println("-----------"); // print seperator for the rows
	}
	}
	
}

public static int[] computerGuessXsOs(String[][] board) { // create a new method for the computer guess for X's and O's
	int row; // create a new integer row
	int col; // create a new integer col
	do { // create a new do while loop
		row = (int)(Math.random() * 3); // new computer guess in range 0 to 2
		col = (int)(Math.random() * 3); // new computer guess in range 0 to 2
	} while(!validGuessXsOs(board, row, col)); // loop until computer guesses a valid guess
	int[] guess = {row, col}; // create a single dimentional array for the guess row and col
	return guess; // return the single dimentional array
}

public static boolean validGuessXsOs(String[][] board, int row, int col) { // create a method to determine if a guess for X's and O's is valid
	if (row >=0 && col >=0 && row <3 && col < 3 && board[row][col].equals(" ")) { // if the guess is positive, less than 3 and the corresponding board value is not " " then
		return true; // return this is a valid guess
	}
	return false; // return this is not a valid guess
}

public static boolean winnerXsOs(String[][] board) { // create a new method to determine if there is a winner
	for (int i = 0; i<board.length; i++) { // for each i in range row in the board 
		if(board[i][0] != " " && board[i][0] == board[i][1] && board[i][1] == board[i][2]) { // if all values in one row equal each other
			return true; // return winner true
		}
		if(board[0][i] != " " && board[0][i] == board[1][i] && board[1][i] == board[2][i]) { // if all values in one column equal each other
			return true; // return winner true
		}
	}
	if (board[0][0] != " " && board[0][0] == board[1][1] && board[1][1] == board[2][2]) { // if the diagonal values top left to bottom right all equal each other
		return true; // return winner true
	}
	
	if (board[0][2] != " " && board[0][2] == board[1][1] && board[1][1] == board[2][0]) { // if the diagonal values bottom left to top right all equal each other
		return true; // return winner true
	}
	
	return false; // otherwise no winner
}

public static void connectFour(Player player, Scanner input) { //create a method to run the game connect 4
	boolean play = true; // create a new boolean for the below do while loop
	int score = 0; // create a new int for player score achieved
	do { // create a new do while loop
		String[][] board = new String[4][6]; // create a new multidimentional array to represent the connect 4 board
		for (int i = 0; i < board.length; i++) { // loop for each i in range row in the board
			for (int j = 0; j < board[i].length; j++) { // loop for each j in range col in the board
				board[i][j] = " "; // assign row i col j to " " 
			}
		}
		int attempts = 0; // initialize number of attempts to zero
		displayConnectFourBoard(board); // display the connect 4 board using the method
		do { // create a new do while loop
			int col = 0; // create a new integer representing column
			do { // create a new do while loop
				boolean validcol=false;// create boolean to represent valid integer type choice 
				do { // create new do while loop
					try { // create new try except clasuse to catch input mismatch
						System.out.print("Please select col (int): "); //Prompt the user to select a col
						col = input.nextInt();	// read col form console	
						validcol = true; // set the valid column boolean to true
					}catch(InputMismatchException e) {
						// from https://stackoverflow.com/questions/12702076/try-catch-with-inputmismatchexception-creates-infinite-loop
						input.next();// remove newline from scanner
					}
				}while(!validcol);
				if (!validConnectFour(board, col)) { // if the column is not valid then
					System.out.println("Invalid choice"); // warn the user
				}
			} while(!validConnectFour(board, col)); // loop while the column is not valid
			attempts++; // increase attempts by 1
			board = insertDisc(board, col, "R"); // insert R into board at position col using method
			displayConnectFourBoard(board); // display the connect 4 board
			if (winnerConnectFour(board)) { // if there is a winner on the board then
				System.out.println("Congratulations you won!"); // tell the user they won
				score = 4; // set score to 4
				break; // break out of the do while loop 
			}
			int computer = computerConnectFour(board); // generate a new computer guess for the connect 4 board
			System.out.println("Computer chooses column " + computer); // display to the user the computer choice
			attempts++; // increment attempts by 1
			board = insertDisc(board, computer, "B"); // insert the B into the board at the computer guess
			displayConnectFourBoard(board); // display the connect 4 column
			if (winnerConnectFour(board)) { // if there is a winner on the board then
				System.out.println("Unlucky you loose!"); // tell the user they have lost
			}
		} while(attempts < 6 * 4 && !winnerConnectFour(board)); // while the number of attemps is less than the number of positions on the board and the there is no winner
		
		if (attempts >= 6 * 4) { // if the attemps are equal to or greater than the number of spaces on the board
			System.out.println("Draw game"); // tell the user there has been a draw game
			score = 2; // set score to 2
		} 
		
		int oldScore = player.getScore(); // get the players old score
		player.setScore(score); // update the score on the player object
		int newScore = player.getScore(); // get the players new score
		
		System.out.println("You have earned " + (newScore - oldScore) + " points!"); // tell the player how many points they have earned, depends on the player class
		System.out.println("Your current score is " + newScore + " points!"); // tell the user their current score
		
		System.out.println("Play again? y/n"); // ask the user if they want to play again
		char again = input.next().charAt(0); // read the first char the user enters and assign to again
		again = Character.toLowerCase(again); // convert again to lowercase
		if (again != 'y') { // if again char is not y then
			play = false; // set play to false
		}
	}while(play); // loop while play is true
	
}

public static void displayConnectFourBoard(String[][] board) {// create a new method to display the connect 4 board
	for (int i = 0; i < board.length; i++) { // loop for each i in range row in the board
		if (i == 0) { // if row is 0 then
			String top = " "; // create a new string top and assign it the value " "
			for (int j = 0; j < board[0].length;j++) {// loop for each j in range col in the row
				top += " " + j; // add space j the col number to the top header of the board
				if (j < 5) { // if the column number is less than 5 then
					top +=" |"; // add | to the board header
				}
			}
			System.out.println(top); // print the board header
		}	
		
		for (int j = 0; j < board[i].length; j++) { // for each j in range col in the board
			if (j == 0) { // if the column is 0 then
				System.out.print("  "); // print " "
			}
			System.out.print(board[i][j]); // print the board value at row i col j
			if (j < 5) { // if col is less than 5 then
				System.out.print(" | "); // print col seperator
			}
			
		}
		System.out.println(); // print newline to go to next row
	}
	System.out.println(" ---------------------------"); // print bottom of board identifier
}

public static String[][] insertDisc(String[][] board, int col, String color){ // create a new method to insert a value into the connect 4 board
	for (int i = board.length - 1; i >=0; i--) { // loop from range the bottom of the connect 4 column to the top
		if (board[i][col] == " ") { // if we find a position where there is value " "
			board[i][col] = color;  // set the position to the passed disc color (from the real game)
			break; // break from the for loop
		}
	}
	return board; // return the updated board
}

public static int computerConnectFour(String[][] board){ // create a method to represent the computer guess for connect 4
	int guess; // create a new integer to represent the computer guess
	do { // create a new do while loop
		guess = (int)(Math.random() * 6); // create a new integer in the range 0 to 5	
	} while(!validConnectFour(board, guess)); // check that the guess is a valid guess
	return guess; // return the integer guess
}

public static boolean validConnectFour(String[][] board, int col) { // create a new method to determine if a guess is valid for a board
	return (col >= 0 && col < board[0].length && board[0][col] == " ")? true: false; // if the guess is less than 0 of greater than the number of cols and the position is " " then return ture, else false
}


public static boolean winnerConnectFour(String[][] board) { // create a new method to determine if there is a winner on the board
	for (int i = 0; i<board.length; i++) { // row check, for each i in range row in the board
		for (int j = 0; j < board[i].length - 3; j++) { // for each j in range lss than number cols - 3
			if(board[i][j] != " " && board[i][j] == board[i][j+1] && board[i][j+1] == board[i][j+2] && board[i][j+2] == board[i][j+3]) { // check rows and cols for winner horizontally
				return true; // return winner true
			}	
		}
		
	}
	for (int j = 0; j < board[0].length; j++) {  // for j in range col 
		if (board[0][j] != " " && board[0][j] == board[1][j] && board[1][j] == board[2][j] && board[2][j] == board[3][j]) { // check rows and cols for winner vertically
			return true; // return winner true
		}	
	}
	
	for (int j = 0; j < board[0].length - 3; j++) { // for j in range col - 3 
		if (board[0][j] != " " && board[0][j] == board[1][j+1] && board[1][j+1] == board[2][j+2] && board[2][j+2] == board[3][j+3]) { // check if diag top right to bottom left for winner then
			return true; // return winner true
		}	
	}
	
	for (int j = 0; j < board[0].length - 3; j++) { // for j in range col - 3
		if (board[3][j] != " " && board[3][j] == board[2][j+1] && board[2][j+1] == board[1][j+2] && board[1][j+2] == board[0][j+3]) { // check if diag top left to bottom right for winner then
			return true; // return winner true
		}	
	}
	
	return false; // otherwise return winner false
}

}
