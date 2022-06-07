
/**
 *
 * @author Abdallah Abouelatta
 */
public class Player {

	private String name;
	private int points;
	private boolean hasTurn = false;

	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public boolean hasTurn() {
		if (this.hasTurn == true) {
			return true;
		} else
			return false;
	}

	public void setHisTurn(boolean dran) {
		this.hasTurn = dran;
	}

	public int getPoints() {
		return points;
	}

	public void increasePoints() {
		this.points++;
	}
}