import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/* Daisha Braxton
 * CSCI 1302 - Final Project (updated)
 * 5/4/2016
 */

public class Player {
	
	private String name; //players name
	private boolean fwd = true; //player moves forward
	private int pXSpace; // player x's spacing
	private int pYSpace; // player x's spacing
	private int pXPlace; // player x's spot/place
	private int pYPlace; // player y's spot/place
	private int pSquare; // square that player is on
	private int maxSpaces; //max number of spaces
	
	Rectangle pRec; //circle object

	Player() {

	}

	Player(Rectangle pRec, String name) {
		this.pRec = pRec;
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFwd(boolean fwd) {
		this.fwd = fwd;
	}

	public void setPXSpace(int pXSpace) {
		this.pXSpace = pXSpace;
	}

	public void setPYSpace(int pYSpace) {
		this.pYSpace = pYSpace;
	}

	public void setYPlace(int yPlace) {
		this.pYPlace = yPlace;
	}

	public void setXPlace(int xPlace) {
		this.pXPlace = xPlace;
	}

	public void setPSquare(int pSquare) {
		this.pSquare = pSquare;
	}

	public void setMaxSpaces(int maxSpaces) {
		this.maxSpaces = maxSpaces;
	}

	public void setPRec(Rectangle pRec) {
		this.pRec = pRec;
	}

	public String getName() {
		return name;
	}

	public boolean getFwd() {
		return fwd;
	}

	public int getPXSpace() {
		return pXSpace;
	}

	public int getPYSpace() {
		return pYSpace;
	}

	public int getYPlace() {
		return pYPlace;
	}

	public int getXPlace() {
		return pXPlace;
	}

	public int getPSquare() {
		return pSquare;
	}

	public int getMaxSpaces() {
		return maxSpaces;
	}

	public Rectangle getPRec() {
		return pRec;
	}

}
