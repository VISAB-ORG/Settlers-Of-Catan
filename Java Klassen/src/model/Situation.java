package model;

/**
 * 
 * Klasse zur Darstellung der Situation, mit der die KIs arbeiten
 * 
 * @author Tjark Harjes
 *
 */
public class Situation {
	/**
	 * Der Name des aktuellen Spielers.
	 */
	private String player;
	/**
	 * Der aktuelle Status des Spielers.
	 */
	private Status playerStatus;

	/**
	 * Default-Konstruktor
	 */
	public Situation() {

	}

	/**
	 * Konstruktor, der die Karte, den Namen und den Status des aktuellen Spielers erhält.
	 * @param map
	 * 			  Die Karte des Spiels
	 * @param player
	 *            Der Name des Spielers.
	 * @param playerStatus
	 *            Der Status des Spielers.
	 */
	public Situation(String player, Status playerStatus) {
		this.player = player;
		this.playerStatus = playerStatus;
	}


	public String getPlayer() {
		return player;
	}


	public Status getPlayerStatus() {
		return playerStatus;
	}


	public void setPlayer(String player) {
		this.player = player;
	}


	public void setPlayerStatus(Status playerStatus) {
		this.playerStatus = playerStatus;
	}
	

	@Override
	public String toString() {
		return "Situation [player=" + player + ", playerStatus=" + playerStatus.toString() + "]";
	}

}
