package game;

import java.util.Comparator;
// origionated from https://www.geeksforgeeks.org/comparator-interface-java/
class Sortbyscore implements Comparator<Player> { // create a new class that implements the comparator

    public int compare(Player a, Player b) // create a new method compare that compares two player classes
    {
        return b.getScore() - a.getScore(); // return b score - a score
    }
}
