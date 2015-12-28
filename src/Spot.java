/** A spot on the board */
public class Spot {
	
	/** The spot's status (invalid, empty, or occupied) */
	private Status status;
	
	/** If the spot is invalid, its status is I;
	 * If it is empty, its status is E;
	 * If it contains a marble, its status is O (occupied)
	 * */
	public enum Status { I, E, O };
	
	/** Constructor */
	public Spot() {
		setStatus(Status.I);
	}

	/** Getter for the status */
	public Status getStatus() {
		return status;
	}

	/** Setter for the status */
	public void setStatus(Status status) {
		this.status = status;
	}
}
