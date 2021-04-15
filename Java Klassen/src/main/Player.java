package main;

import model.CityBuildPlace;
import model.Map;
import model.RoadBuildPlace;
import model.Status;
import model.VillageBuildPlace;
import model.plan.Plan;

/**
 * Klasse, die den Spieler als KI darstellt.
 * 
 * @author tjark
 *
 */
public class Player {
	public Map map;
	
    public int brick;
    public int wheat;
    public int stone;
    public int wood;
    public int sheep;
    
    public boolean isFirstTurn, isSecondTurn;
    public boolean hasTurn;
    
    public boolean freeBuild;
    public boolean freeBuildRoad;
    
    public int longestRoad;
    public int victoryPoints;
    
    private PlayerManager manager;
    
    private Plan plan;
    
    private boolean first, second, third, forth, fifth;

	private boolean allowedToRollDice;
	private int triesCity, triesVillage, triesRoad;
    
	/**
	 * Konstruktor, der den Spieler erzeugt
	 * 
	 * @param manager
	 * 					Spielermanager
	 * @param map
	 * 					Die Karte zum Zeitpunkt der Erstellung
	 * @param hasTurn
	 * 					Ist der Spieler gerade dran
	 */
    public Player(PlayerManager manager, Map map, boolean hasTurn) {
    	this.map = map;
    	this.hasTurn = hasTurn;
    	
    	this.triesCity = 0;
    	this.triesVillage = 0;
    	this.triesRoad = 0;
    	
    	this.brick = 0;
    	this.wheat = 0;
    	this.stone = 0;
    	this.wood = 0;
    	this.sheep = 0;
    	
    	this.isFirstTurn = false;
    	this.isSecondTurn = false;
    	
    	this.longestRoad = 0;
    	this.victoryPoints = 0;	
    	
    	this.manager = manager;
    	this.first = true;
    	this.second = true;
    	this.third = true;
    	this.forth = true;
    	this.fifth = true;
    	
    }
    
    /**
     * Methode, die einen Zug plant
     */
    public void Turn() {
    	plan = new Plan();
    	if (hasTurn) {
    		if (isFirstTurn) {
    			if(first) {
    				first = false;
    				ActivateVillagePlaces();
    			} else if (second) {
    				second = false;
    				BuildVillage(map.getRandomVillagePlace());
    			} else if (third) {
    				third = false;
    				ActivateVillagePlaces();
    				ActivateRoadPlaces();
    			} else if (forth) {
    				forth = false;
    				BuildRoad(map.getRandomRoadPlace());
    				ActivateRoadPlaces();
    			} else if (fifth) {	
    				fifth = false;
    				first = true;
    				EndTurn();
    			}
    		} else if (isSecondTurn) {
    			if(first) {
    		    	first = false;
    		    	second = true;
    		    	third = true;
    		    	forth = true;
    		    	fifth = true;
    				ActivateVillagePlaces();
    			} else if (second) {
    				second = false;
    				BuildVillage(map.getRandomVillagePlace());
    			} else if (third) {
    				third = false;
    				ActivateVillagePlaces();
    				ActivateRoadPlaces();
    			} else if (forth) {
    				forth = false;
    				BuildRoad(map.getRandomRoadPlace());
    				ActivateRoadPlaces();
    			} else if (fifth) {	
    				fifth = false;
    				first = true;
    				EndTurn();
    			}
    		} else {
    			if (allowedToRollDice) {
    				RollDice();
    				first = true;
    			} else if (HasResourcesForCity() || HasResourcesForVillage() || HasResourcesForRoad()){
    				if (HasResourcesForCity() && first && map.cityPlacesExist()) {
    					triesCity++;
    					first = false;
    					ActivateCityPlaces();
    				} else if (HasResourcesForCity() && !first && map.cityPlacesExist() && triesCity <= 3) {
    					triesRoad = 0;
    					triesCity = 0;
    					triesVillage = 0;
    					first = true;
	    				BuildCity(map.getRandomCityPlace());
	    				ActivateCityPlaces();
    				} else if (HasResourcesForVillage() && (first || triesVillage == 0)) {
    					triesVillage++;
    					first = false; 
	    				ActivateVillagePlaces();
    				} else if (HasResourcesForVillage() && !first && map.villagePlacesActive() && triesVillage <= 3) {
    					triesRoad = 0;
    					triesCity = 0;
    					triesVillage = 0;
    					first = true; 
	    				BuildVillage(map.getRandomVillagePlace());
	    				ActivateVillagePlaces();
    				} else if (HasResourcesForRoad() && (first || triesRoad == 0)) {
    					triesRoad++;
    					first = false;
    					ActivateRoadPlaces();
    				} else if (HasResourcesForRoad() && !first && triesRoad <= 3) {
    					triesRoad = 0;
    					triesCity = 0;
    					triesVillage = 0;
    					first = true;
	    				BuildRoad(map.getRandomRoadPlace());
	    				ActivateRoadPlaces();
    				}
    			} else {
    				EndTurn();
    			}
    		}
    	}
    }



	/**
     * Methode zur Prüfung, ob genügend Ressourcen für eine Siedlung vorhanden sind
     * @return boolean
     */
    private boolean HasResourcesForCity() {
        if (stone >= 3 && wheat >= 2)
            return true;
        return false;
    }
    
    /**
     * Methode zur Prüfung, ob genügend Ressourcen für eine Siedlung vorhanden sind
     * @return boolean
     */
    private boolean HasResourcesForVillage() {
        if (brick >= 1 && wood >= 1 && sheep >= 1 && wheat >= 1)
            return true;
        return false;
    }

    /**
     * Methode zur Prüfung, ob genügend Ressourcen für eine Straße vorhanden sind
     * @return boolean
     */
    private boolean HasResourcesForRoad() {
        if (brick >= 1 && wood >= 1)
            return true;
        return false;
    }
    
    public void BuildVillage() {
    	plan.addAction(new model.plan.BuildVillage());
    }
    
	public void BuildVillage(VillageBuildPlace randomVillagePlace) {
		plan.addAction(new model.plan.BuildVillage(randomVillagePlace));
	}
    
    public void BuildCity() {
    	plan.addAction(new model.plan.BuildCity());
    }
    
	private void BuildCity(CityBuildPlace randomCityPlace) {
		plan.addAction(new model.plan.BuildCity(randomCityPlace));
	}
   
	private void BuildRoad(RoadBuildPlace randomRoadPlace) {
		plan.addAction(new model.plan.BuildRoad(randomRoadPlace));
	}

    public void BuildRoad() {
    	plan.addAction(new model.plan.BuildRoad());
    }
    
    public void RollDice() {
    	plan.addAction(new model.plan.RollDice());
    }
    
    public void ActivateVillagePlaces() {
    	plan.addAction(new model.plan.ActivateVillagePlaces());
    }
    
    public void ActivateCityPlaces() {
    	plan.addAction(new model.plan.ActivateCityPlaces());
    }
    
    public void ActivateRoadPlaces() {
    	plan.addAction(new model.plan.ActivateRoadPlaces());
    }
    
    public void EndTurn() {
    	plan.addAction(new model.plan.EndTurn());
    }

    /**
     * Methode zum Updaten des Status des Spielers
     */
	public void Update(Status playerStatus) {
 
		//this.map = playerStatus.getMap();
		
    	this.brick = playerStatus.bricks;
    	this.wheat = playerStatus.wheat;
    	this.stone = playerStatus.stone;
    	this.wood = playerStatus.wood;
    	this.sheep = playerStatus.sheep;
    	
    	this.isFirstTurn = playerStatus.isFirstTurn;
    	this.isSecondTurn = playerStatus.isSecondTurn;
    	
    	this.freeBuild = playerStatus.freeBuild;
    	this.freeBuildRoad = playerStatus.freeBuildRoad;
    	
    	this.longestRoad = playerStatus.longestRoad;
    	this.victoryPoints = playerStatus.victoryPoints;
    	
    	this.allowedToRollDice = playerStatus.allowedToRollDice;
	}

	public Plan getPlan() {
		return this.plan;
	}
}
