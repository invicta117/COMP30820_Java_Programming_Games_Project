package game;

public class LimitedPlayer extends Player {

public LimitedPlayer(String name){  // Constructor for Limited player class with one argument name
	super(name, 0);  // Call the superclass constructor that has two arguments and pass values name and score of value 0
}

@Override
public void setScore(int score) { // Override the superclass method set score
	super.setScore((int)(score * 0.5)); // Use the superclass method set score but decrease the score by half
}

@Override
public String getName() { // Override the superclass method get name
	return super.getName() + " (LIMITED)"; // Use the superclass method get name and append LIMITED to name
}
	
}
