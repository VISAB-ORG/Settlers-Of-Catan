package model;

import model.plan.Plan;

/**
 * 
 * Diese Klasse bietet eine Datenstruktur, um eine Antwort (Response) darstellen zu k�nnen.
 * 
 * @author Tjark Harjes
 *
 */
public class Response {

	/**
	 * Die Situation, in der sich der Spieler befindet.
	 */
	private Situation situation;
	/**
	 * Der Plan, der vom Spieler ausgef�hrt werden soll.
	 */
	private Plan plan;

	/**
	 * Default-Konstruktor
	 */
	public Response() {
		this(new Situation(), new Plan());
	}

	/**
	 * Konstruktor zur Erzeugung eines Response-Objekts mit einer Situation.
	 * 
	 * @param situation
	 */
	public Response(Situation situation) {
		this(situation, new Plan());
	}

	/**
	 * Konstruktor zur Erzeugung eines Response-Objekts mit einer Situation.
	 * 
	 * @param situation
	 * @param plan
	 */
	public Response(Situation situation, Plan plan) {
		this.situation = situation;
		this.plan = plan;
	}

	/**
	 * Setter f�r die Situation.
	 * 
	 * @param situation
	 */
	public void setSituation(Situation situation) {
		this.situation = situation;
	}

	/**
	 * Getter f�r die Situation.
	 * 
	 * @return Die aktuelle Situation der Anfrage.
	 */
	public Situation getSituation() {
		return situation;
	}

	/**
	 * Getter f�r den Plan.
	 * 
	 * @return Der aktuelle Plan.
	 */
	public Plan getPlan() {
		return plan;
	}

	/**
	 * Setter f�r den Plan.
	 * 
	 * @param plan
	 */
	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	@Override
	public String toString() {
		return "Response [situation=" + situation.toString() + "]";
	}

}
