package model;

import java.util.List;

/**
 * Klasse, die den Status eines Spielers speichert
 */
public class Status {

	//Alle Attribute, die zwischen KI und Spiel über die Situation des Spielers übermittelt werden müssen
	
	/**
	 * Die Karte des Spiels
	 */
	private Map map;
	
	public boolean isFirstTurn;
	public boolean isSecondTurn;

	public int victoryPoints;
	public int longestRoad;
	public boolean hasLongestRoad;

	public int bricks;
	public int wheat;
	public int stone;
	public int wood;
	public int sheep;

	public boolean freeBuild;
	public boolean freeBuildRoad;

	public List<Object> villages;
	public List<Object> roads;

	public boolean isAbledToEndTurn;
	public boolean allowedToRollDice;
	
	public boolean villagePlacesActive; 
	public boolean roadPlacesActive; 
	public boolean cityPlacesActive; 
	
	public boolean isAbledToBuildCity;
	public boolean isAbledToBuildVillage;
	public boolean isAbledToBuildRoad;
	
	public boolean villagePlacesAvailable;
	public boolean roadPlacesAvailable;
	public boolean cityPlacesAvailable;
	
	public String preference; 



    /**
     * Konstruktor, wenn der Status des Spielers aus dem Spiel abgeleitet werden kann
     */
    public Status(Map map, boolean isFirstTurn, boolean isSecondTurn, int victoryPoints, int longestRoad, boolean hasLongestRoad, int bricks, int wheat, int stone, int wood, int sheep, 
    		boolean freeBuild, boolean freeBuildRoad, List<Object> villages, List<Object> roads, boolean isAbledToEndTurn, boolean allowedToRollDice, boolean villagePlacesAvailable,
    		boolean roadPlacesAvailable) {
    	this.map = map;
    	this.villagePlacesActive = map.villagePlacesActive();
    	this.roadPlacesActive = map.roadPlacesActive();
    	this.cityPlacesActive = map.cityPlacesActive();
        this.isFirstTurn = isFirstTurn;
        this.isSecondTurn = isSecondTurn;
        this.victoryPoints = victoryPoints;
        this.longestRoad = longestRoad;
        this.hasLongestRoad = hasLongestRoad;
        this.bricks = bricks;
        this.wheat = wheat;
        this.stone = stone;
        this.wood = wood;
        this.sheep = sheep;
        this.freeBuild = freeBuild;
        this.freeBuildRoad = freeBuildRoad;
        this.villages = villages;
        this.roads = roads;
        this.isAbledToEndTurn = isAbledToEndTurn;
        this.allowedToRollDice = allowedToRollDice;
    	this.isAbledToBuildCity = hasResourcesForCity();
    	this.isAbledToBuildCity = hasResourcesForVillage();
    	this.isAbledToBuildCity = hasResourcesForRoad();
    	this.preference = "";
    	this.villagePlacesAvailable = villagePlacesAvailable;
    	this.roadPlacesAvailable = roadPlacesAvailable;
    	this.cityPlacesAvailable = cityBuildPlacesAvailable();
    }
	

    public Status() {
		// TODO Auto-generated constructor stub
	}

	public void setMap(Map map) {
		this.map = map;
	}
	
	public Map getMap() {
		return map;
	}
	
	public String getPreference() {
		return this.preference;
	}
	
	public boolean cityBuildPlacesAvailable() {
		if (villages.size() > 0) {
			return true;
		}
		return false;
	}
	
	public String calculatePreferencePlayer1() {
		String preference = "";
		if (isFirstTurn && freeBuild) {
			preference = "wood";
		} else if (isSecondTurn && freeBuild) {
			preference = "brick";
		} else if (isAbledToBuildCity) {
			preference = "";
		} else if (isAbledToBuildVillage) {
			if (wheat == 2 && stone == 2) {
				preference = "rock";
			} else if (stone == 3 && wheat == 1) {
				preference = "wheat";
			}
		} else {
			preference = "";
		}
		this.preference = preference;
		return preference;
	}
	
	public String calculatePreferencePlayer2() {
		String preference = "";
		if (isFirstTurn && freeBuild) {
			preference = "wood";
		} else if (isSecondTurn && freeBuild) {
			preference = "brick";
		} else if (isAbledToBuildCity) {
			preference = "";
		} else if (isAbledToBuildVillage) {
			if (bricks < wood || bricks == wood) {
				if (bricks < sheep || bricks == sheep) {
					if (bricks < wheat || bricks == wheat) {
						preference = "brick";
					} else {
						if (wheat < sheep || wheat == sheep) {
							preference = "wheat";
						} else {
							preference = "sheep";
						}
					}
				} else {
					if (sheep < wheat) {
						preference = "sheep";
					} else {
						preference = "wheat";
					}
				}
			} else {
				if (wood < sheep || wood == sheep) {
					preference = "wood";
				} else {
					if (wheat < sheep || wheat == sheep) {
						preference = "wheat";
					} else {
						preference = "sheep";
					}
				}
			}
		} else {
			preference = "";
		}
		this.preference = preference;
		return preference;
	}
	
	/**
     * Methode zur Prüfung, ob genügend Ressourcen für eine Siedlung vorhanden sind
     * @return boolean
     */
    public boolean hasResourcesForCity() {
        if (stone >= 3 && wheat >= 2)
            return true;
        return false;
    }
    
    /**
     * Methode zur Prüfung, ob genügend Ressourcen für eine Siedlung vorhanden sind
     * @return boolean
     */
    public boolean hasResourcesForVillage() {
        if (bricks >= 1 && wood >= 1 && sheep >= 1 && wheat >= 1)
            return true;
        return false;
    }

    /**
     * Methode zur Prüfung, ob genügend Ressourcen für eine Straße vorhanden sind
     * @return boolean
     */
    public boolean hasResourcesForRoad() {
        if (bricks >= 1 && wood >= 1)
            return true;
        return false;
    }
	

	@Override
    public String toString()
    {
        return "Status [Map: " + map.toString() + "isFirstTurn=" + isFirstTurn + ", isSecondTurn=" + isSecondTurn + ", victoryPoints="
            + victoryPoints + ", longestRoad=" + longestRoad + ", hasLongestRoad=" + hasLongestRoad + ", bricks="
            + bricks + ", wheat=" + wheat + ", stone=" + stone
            + ", wood=" + wood + ", sheep=" + sheep
            + ", freeBuild=" + freeBuild + ", freeBuildRoad=" + freeBuildRoad + ", villages=" + villages.size()
            + ", roads=" + roads.size() + ", isAbledToEndTurn=" + isAbledToEndTurn
            + ", cityPlacesActive=" + cityPlacesActive
            + ", villagePlacesActive=" + villagePlacesActive
            + ", roadPlacesActive=" + roadPlacesActive
            + ", allowedToRollDice=" + allowedToRollDice + "]";
    }
	
    public String toString2()
    {
        return "Status [isFirstTurn=" + isFirstTurn + ", isSecondTurn=" + isSecondTurn + ", hasLongestRoad=" + hasLongestRoad + ", bricks="
            + bricks + ", wheat=" + wheat + ", stone=" + stone
            + ", wood=" + wood + ", sheep=" + sheep
            + ", freeBuild=" + freeBuild + ", freeBuildRoad=" + freeBuildRoad
            + ", isAbledToEndTurn=" + isAbledToEndTurn
            + ", cityPlacesActive=" + cityPlacesActive
            + ", villagePlacesActive=" + villagePlacesActive
            + ", roadPlacesActive=" + roadPlacesActive
            + ", allowedToRollDice=" + allowedToRollDice + "]";
    }

}