package game;

public class VIPPlayer extends Player {

public VIPPlayer(String name){ // Constructor for VIP player class with one argument name
	super(name, 100); // Call the superclass constructor that has two arguments and pass values name and score of value 100
}

@Override
public void setScore(int score) { // Override the superclass method set score
	super.setScore(score * 2);  // Use the superclass method set score but increase the score by 2
}

@Override
public String getName() { // Override the superclass method get name
	return super.getName() + " (VIP)"; // Use the superclass method get name and append VIP to name
}
}
