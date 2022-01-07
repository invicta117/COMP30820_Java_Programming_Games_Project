package game;

public class Player {
private int score; // Initialize new private instance variable score type int
private String name; // initialize new private instance variable name type string

public Player(String name){ // constructer for player class with one argument name
	this.name = name; // set the instance variable name to the passed argument name
	score = 50; // set instance variable score to 50
}

public Player(String name, int score){ // Constructor for player class with two arguments name and score
	this.name = name; // set the instance variable name to the passed argument name
	this.score = score; // set instance variable score to the passed argument score
}

public String getName() { // Create new getter method getName
	return name; // Return the instance value name
}

public int getScore() { // Create new getter method get score
	return score; // Return the instance value score
}

public void setScore(int score) { // Create new setter method set score
		this.score += score; // Increase the instance variable score by the passed argument score
	}
}



