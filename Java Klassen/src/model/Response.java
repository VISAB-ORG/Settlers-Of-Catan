package model;

import model.plan.Plan;

/**
 * 
 * Diese Klasse bietet eine Datenstruktur, um eine Antwort (Response) darstellen zu können.
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
	 * Der Plan, der vom Spieler ausgeführt werden soll.
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
	 * Setter für die Situation.
	 * 
	 * @param situation
	 */
	public void setSituation(Situation situation) {
		this.situation = situation;
	}

	/**
	 * Getter für die Situation.
	 * 
	 * @return Die aktuelle Situation der Anfrage.
	 */
	public Situation getSituation() {
		return situation;
	}

	/**
	 * Getter für den Plan.
	 * 
	 * @return Der aktuelle Plan.
	 */
	public Plan getPlan() {
		return plan;
	}

	/**
	 * Setter für den Plan.
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
