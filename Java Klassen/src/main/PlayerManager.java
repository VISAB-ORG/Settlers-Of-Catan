package main;

import javax.swing.JTextArea;

import cbr_util.DefaultCases;
import model.Map;
import model.Request;
import model.Response;
import model.Situation;


/**
 * Klasse, die die Spieler verwaltet und diese ansteuert
 * @author tjark
 *
 */
public class PlayerManager {

	private Player player1, player2;
	private Map map;
	private Request request;
	//private static JTextArea jtf;
	
	/**
	 * Konstruktor
	 * @param jtf
	 */
	public PlayerManager(/*JTextArea jtf*/) {
		player1 = new Player(this, map, true);
		player2 = new Player(this, map, false);
		//this.jtf = jtf;
	}	
	
	/**
	 * Methode, um einen Spielerwechsel vorzunehmen
	 * @param isPlayer1Turn
	 */
	public void ChangePlayer(boolean isPlayer1Turn) {
		if (isPlayer1Turn) {
			player1.hasTurn = true;
			player2.hasTurn = false;
			player1.Turn();
		} else {
			player1.hasTurn = false;
			player2.hasTurn = true;
			player2.Turn();
		}
	}
	
	/**
	 * Methode, um eine Anfrage zu beantworten
	 * @param request
	 * @return
	 */
	public Response answerRequest(Request request) {
		this.request = request;
		Situation situation = request.getSituation();
		//this.jtf.setText(jtf.getText() + "\n" + situation.toString());
		Response response = new Response();
		setMap(situation.getPlayerStatus().getMap());
		
		if (situation.getPlayer().equals("Player1")) {
			player1.hasTurn = true;
			player1.Update(request.getSituation().getPlayerStatus());
			player1.Turn();
			response = new Response(situation, player1.getPlan());
			//this.jtf.setText("Spieler 1");
		} else if (situation.getPlayer().equals("Player2")) {
			player2.hasTurn = true;
			player2.Update(request.getSituation().getPlayerStatus());
			player2.Turn();
			response = new Response(situation, player2.getPlan());
			//this.jtf.setText("Spieler 2");
		} else {
			//this.jtf.setText("Kagge");
			System.exit(1);
		}
		//this.jtf.setText(jtf.getText() + "\n" + response.toString());
		return response;
	}
	
	/**
	 * Methode, die die Karte für die Spieler setzt
	 * @param map
	 */
	public void setMap(Map map) {
		this.map = map;
		player1.map = map;
		player2.map = map;
	}
}
