package model;


/**
 * Diese Klasse bietet eine Datenstruktur, um eine Anfrage (Request) darstellen zu können.
 * 
 * @author Tjark Harjes
 */
public class Request {

	/**
	 * Die Situation, in der sich der Spieler befindet.
	 */
	private Situation situation;

    /**
     * Eine Anfrage besteht aus der Situation.
     */
	public Request() {
		this(new Situation());
	}

	/**
	 * Konstruktor, der eine Situation als Parameter erwartet.
	 * 
	 * @param situation
	 */
	public Request(Situation situation) {
		this.situation = situation;
	}

	/**
	 * Setter für die Situation.
	 */
	public void setSituation(Situation situation) {
		this.situation = situation;
	}

	/**
	 * Getter für die Situation.
	 * 
	 * @return
	 */
	public Situation getSituation() {
		return situation;
	}

	@Override
	public String toString() {
		return "Request [situation=" + situation.toString() + "]";
	}

}
