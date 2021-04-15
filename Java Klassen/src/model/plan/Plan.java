package model.plan;

import java.util.ArrayList;

/**
 * Klasse, die den Plan eines KI-Spielers speichern kann.
 * 
 * @author Tjark Harjes
 */
public class Plan {


	private String actionsAsString;
	
	/**
	 * Default-Konstruktor.
	 */
	public Plan() {
		actionsAsString = "";
	}

    /**
     * Diese Methode fügt Aktionen zur Liste der Aktionen hinzu. 
     */
	public void addActions(Action[] actions) {
		for (Action action : actions) {
			addAction(action);
		}
	}

    /**
     * Diese Methode fügt eine Aktion dem Plan hinzu
     */
	public void addAction(Action action) {
		if (action.getClass().getSimpleName().equals("BuildVillage")) {
			BuildVillage buildvillage = (BuildVillage) action;
			actionsAsString += action.getClass().getSimpleName() + ":" + buildvillage.getRow() + ":" + buildvillage.getColumn() + ":;";
		} else if (action.getClass().getSimpleName().equals("BuildRoad")) {
			BuildRoad buildroad = (BuildRoad) action;
			actionsAsString += action.getClass().getSimpleName() + ":" + buildroad.getRow() + ":" + buildroad.getColumn() + ":;";
		} else if (action.getClass().getSimpleName().equals("BuildCity")) {
			BuildCity buildcity = (BuildCity) action;
			actionsAsString += action.getClass().getSimpleName() + ":" + buildcity.getRow() + ":" + buildcity.getColumn() + ":;";
		} else {
			actionsAsString += action.getClass().getSimpleName() + ";";
		}
	}

	/**
	 * Formatierung des String ins Json
	 */
	public void trimStringForJson() {
		actionsAsString = actionsAsString.substring(0, actionsAsString.length() - 1).trim();
	}


	public String getActionsAsString() {
		return actionsAsString;
	}

	public void setActionsAsString(String actionsAsString) {
		this.actionsAsString = actionsAsString;
	}
}
