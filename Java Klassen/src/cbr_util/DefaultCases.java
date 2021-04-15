package cbr_util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Map;
import model.Status;

/**
 * @author Tjark Harjes
 *
 */
public final class DefaultCases {

	/**
	 * Map zur Speicherung von einem Plan zu einem Status.
	 */
	private static HashMap<Status, String> status = null;
	private static Map map;

	static {
		initList();
		fillList();
	}

	/**
	 * Methode zur Initialisierung der Map.
	 */
	private static void initList() {
		status = new HashMap<>();
	}
	
	/**
	 * In dieser Methode werden die Defaultcases erzeugt und der Map
	 * hinzugef&uuml;gt.
	 */
	/**
	 * 
	 */
	private static void fillList() {

		String plan = "";


		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf, Bauplaetze aktivieren
		Status firstTurnActivateVillagePlaces = new Status();

		//Wichtig
		firstTurnActivateVillagePlaces.isFirstTurn = true;
		firstTurnActivateVillagePlaces.isSecondTurn = false;
		firstTurnActivateVillagePlaces.freeBuild = true;
		firstTurnActivateVillagePlaces.freeBuildRoad = false;
		
		
		//Unwichtig
		firstTurnActivateVillagePlaces.victoryPoints = 0;
		firstTurnActivateVillagePlaces.longestRoad = 0;
		firstTurnActivateVillagePlaces.hasLongestRoad = false;
		
		/*firstTurnActivateVillagePlaces.bricks = 0;
		firstTurnActivateVillagePlaces.wheat = 0;
		firstTurnActivateVillagePlaces.stone = 0;
		firstTurnActivateVillagePlaces.wood = 0;
		firstTurnActivateVillagePlaces.sheep= 0;*/
	
		//firstTurnVillage.villages = new ArrayList<~>();
		//firstTurnVillage.roads = new ArrayList<~>();
		
		firstTurnActivateVillagePlaces.isAbledToEndTurn = false;
		firstTurnActivateVillagePlaces.allowedToRollDice = false;
		
		firstTurnActivateVillagePlaces.cityPlacesAvailable = false;
		firstTurnActivateVillagePlaces.villagePlacesAvailable = true;
		firstTurnActivateVillagePlaces.roadPlacesAvailable = false;
		
		firstTurnActivateVillagePlaces.preference = "";
		
		plan = "ActivateVillagePlaces;";
		status.put(firstTurnActivateVillagePlaces, plan);

		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status firstTurnVillage = new Status();

		//Wichtig
		firstTurnVillage.villagePlacesActive = true;
		firstTurnVillage.isFirstTurn = true;
		firstTurnVillage.isSecondTurn = false;
		firstTurnVillage.freeBuild = true;
		firstTurnVillage.freeBuildRoad = false;
		
		
		//Unwichtig
		firstTurnVillage.victoryPoints = 0;
		firstTurnVillage.longestRoad = 0;
		firstTurnVillage.hasLongestRoad = false;
		
		/*firstTurnVillage.bricks = 0;
		firstTurnVillage.wheat = 0;
		firstTurnVillage.stone = 0;
		firstTurnVillage.wood = 0;
		firstTurnVillage.sheep= 0;*/
	
		//firstTurnVillage.villages = new ArrayList<~>();
		//firstTurnVillage.roads = new ArrayList<~>();
		
		firstTurnVillage.isAbledToEndTurn = false;
		firstTurnVillage.allowedToRollDice = false;
		
		firstTurnVillage.cityPlacesAvailable = false;
		firstTurnVillage.villagePlacesAvailable = true;
		firstTurnVillage.roadPlacesAvailable = false;
		
		firstTurnVillage.preference = "";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(firstTurnVillage, plan);
		
		
		
		
		//Konkreter Fall für den ersten Zug, wenn eine kostenlose Straße gebaut werden darf
		Status firstTurnActivateRoadPlaces = new Status();

		//Wichtig
		firstTurnActivateRoadPlaces.isFirstTurn = true;
		firstTurnActivateRoadPlaces.isSecondTurn = false;
		firstTurnActivateRoadPlaces.freeBuild = false;
		firstTurnActivateRoadPlaces.freeBuildRoad = true;
		
		
		//Unwichtig
		firstTurnActivateRoadPlaces.victoryPoints = 0;
		firstTurnActivateRoadPlaces.longestRoad = 0;
		firstTurnActivateRoadPlaces.hasLongestRoad = false;
		
		/*firstTurnActivateRoadPlaces.bricks = 0;
		firstTurnActivateRoadPlaces.wheat = 0;
		firstTurnActivateRoadPlaces.stone = 0;
		firstTurnActivateRoadPlaces.wood = 0;
		firstTurnActivateRoadPlaces.sheep= 0;*/
	
		//firstTurnRoad.villages = new ArrayList<~>();
		//firstTurnRoad.roads = new ArrayList<~>();
		
		firstTurnActivateRoadPlaces.isAbledToEndTurn = false;
		firstTurnActivateRoadPlaces.allowedToRollDice = false;
		
		firstTurnActivateRoadPlaces.cityPlacesAvailable = true;
		firstTurnActivateRoadPlaces.villagePlacesAvailable = true;
		firstTurnActivateRoadPlaces.roadPlacesAvailable = true;
		
		firstTurnActivateRoadPlaces.preference = "";
		
		plan = "ActivateRoadPlaces;";
		status.put(firstTurnActivateRoadPlaces, plan);
		
		
		//Konkreter Fall für den ersten Zug, wenn eine kostenlose Straße gebaut werden darf
		Status firstTurnRoad = new Status();

		//Wichtig
		firstTurnRoad.roadPlacesActive = true;
		firstTurnRoad.isFirstTurn = true;
		firstTurnRoad.isSecondTurn = false;
		firstTurnRoad.freeBuild = false;
		firstTurnRoad.freeBuildRoad = true;
		
		
		//Unwichtig
		firstTurnRoad.victoryPoints = 0;
		firstTurnRoad.longestRoad = 0;
		firstTurnRoad.hasLongestRoad = false;
		
		/*firstTurnRoad.bricks = 0;
		firstTurnRoad.wheat = 0;
		firstTurnRoad.stone = 0;
		firstTurnRoad.wood = 0;
		firstTurnRoad.sheep= 0;*/
	
		//firstTurnRoad.villages = new ArrayList<~>();
		//firstTurnRoad.roads = new ArrayList<~>();
		
		firstTurnRoad.isAbledToEndTurn = false;
		firstTurnRoad.allowedToRollDice = false;
		
		firstTurnRoad.cityPlacesAvailable = true;
		firstTurnRoad.villagePlacesAvailable = true;
		firstTurnRoad.roadPlacesAvailable = true;
		
		firstTurnRoad.preference = "";
		
		plan = "BuildRoad;ActivateRoadPlaces;";
		status.put(firstTurnRoad, plan);
		
		
		
		//Konkreter Fall für den ersten Zug, wenn beendet werden soll
		Status firstTurnEnd = new Status();

		//Wichtig
		firstTurnEnd.isFirstTurn = true;
		firstTurnEnd.isSecondTurn = false;
		firstTurnEnd.freeBuild = false;
		firstTurnEnd.freeBuildRoad = false;
		firstTurnEnd.isAbledToEndTurn = true;
		
		
		//Unwichtig
		firstTurnEnd.victoryPoints = 0;
		firstTurnEnd.longestRoad = 1;
		firstTurnEnd.hasLongestRoad = false;
		
		/*firstTurnEnd.bricks = 0;
		firstTurnEnd.wheat = 0;
		firstTurnEnd.stone = 0;
		firstTurnEnd.wood = 0;
		firstTurnEnd.sheep= 0;*/
	
		//secondTurnRoad.villages = new ArrayList<~>();
		//secondTurnRoad.roads = new ArrayList<~>();	

		firstTurnEnd.allowedToRollDice = false;
		
		firstTurnEnd.cityPlacesAvailable = true;
		firstTurnEnd.villagePlacesAvailable = true;
		firstTurnEnd.roadPlacesAvailable = true;
		
		firstTurnEnd.preference = "";
		
		plan = "EndTurn;";
		status.put(firstTurnEnd, plan);
		
		
		/*
		 * Zweiter Zug
		 */
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf, Bauplaetze aktivieren
		Status secondTurnActivateVillagePlaces = new Status();

		//Wichtig
		secondTurnActivateVillagePlaces.isFirstTurn = false;
		secondTurnActivateVillagePlaces.isSecondTurn = true;
		secondTurnActivateVillagePlaces.freeBuild = true;
		secondTurnActivateVillagePlaces.freeBuildRoad = false;
		//Unwichtig
		secondTurnActivateVillagePlaces.victoryPoints = 0;
		secondTurnActivateVillagePlaces.longestRoad = 1;
		secondTurnActivateVillagePlaces.hasLongestRoad = false;
		/*secondTurnActivateVillagePlaces.bricks = 0;
		secondTurnActivateVillagePlaces.wheat = 0;
		secondTurnActivateVillagePlaces.stone = 0;
		secondTurnActivateVillagePlaces.wood = 0;
		secondTurnActivateVillagePlaces.sheep= 0;*/
		//secondTurnActivateVillagePlaces.villages = new ArrayList<~>();
		//secondTurnActivateVillagePlaces.roads = new ArrayList<~>();
		secondTurnActivateVillagePlaces.isAbledToEndTurn = false;
		secondTurnActivateVillagePlaces.allowedToRollDice = false;
		
		secondTurnActivateVillagePlaces.cityPlacesAvailable = true;
		secondTurnActivateVillagePlaces.villagePlacesAvailable = true;
		secondTurnActivateVillagePlaces.roadPlacesAvailable = true;
		
		secondTurnActivateVillagePlaces.preference = "";
		
		plan = "ActivateVillagePlaces;";
		status.put(secondTurnActivateVillagePlaces, plan);
		
		
		
		
		//Konkreter Fall für den ersten Zug, wenn eine kostenlose Straße gebaut werden darf
		Status secondTurnVillage = new Status();

		//Wichtig
		secondTurnVillage.villagePlacesActive = true;
		secondTurnVillage.isFirstTurn = false;
		secondTurnVillage.isSecondTurn = true;
		secondTurnVillage.freeBuild = true;
		secondTurnVillage.freeBuildRoad = false;
		
		
		//Unwichtig
		secondTurnVillage.victoryPoints = 0;
		secondTurnVillage.longestRoad = 1;
		secondTurnVillage.hasLongestRoad = false;
		
		/*secondTurnVillage.bricks = 0;
		secondTurnVillage.wheat = 0;
		secondTurnVillage.stone = 0;
		secondTurnVillage.wood = 0;
		secondTurnVillage.sheep= 0;*/
	
		//secondTurnVillage.villages = new ArrayList<~>();
		//secondTurnVillage.roads = new ArrayList<~>();
		
		secondTurnVillage.isAbledToEndTurn = false;
		secondTurnVillage.allowedToRollDice = false;
		
		secondTurnVillage.cityPlacesAvailable = true;
		secondTurnVillage.villagePlacesAvailable = true;
		secondTurnVillage.roadPlacesAvailable = true;
		
		secondTurnVillage.preference = "";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(secondTurnVillage, plan);
		
		
		
		
		//Konkreter Fall für den ersten Zug, wenn eine kostenlose Straße gebaut werden darf
		Status secondTurnActivateRoadPlaces = new Status();

		//Wichtig
		secondTurnActivateRoadPlaces.isFirstTurn = false;
		secondTurnActivateRoadPlaces.isSecondTurn = true;
		secondTurnActivateRoadPlaces.freeBuild = false;
		secondTurnActivateRoadPlaces.freeBuildRoad = true;
		
		
		//Unwichtig
		secondTurnActivateRoadPlaces.victoryPoints = 0;
		secondTurnActivateRoadPlaces.longestRoad = 0;
		secondTurnActivateRoadPlaces.hasLongestRoad = false;
		
		/*secondTurnActivateRoadPlaces.bricks = 0;
		secondTurnActivateRoadPlaces.wheat = 0;
		secondTurnActivateRoadPlaces.stone = 0;
		secondTurnActivateRoadPlaces.wood = 0;
		secondTurnActivateRoadPlaces.sheep= 0;*/
	
		//firstTurnRoad.villages = new ArrayList<~>();
		//firstTurnRoad.roads = new ArrayList<~>();
		
		secondTurnActivateRoadPlaces.isAbledToEndTurn = false;
		secondTurnActivateRoadPlaces.allowedToRollDice = false;
		
		secondTurnActivateRoadPlaces.cityPlacesAvailable = true;
		secondTurnActivateRoadPlaces.villagePlacesAvailable = true;
		secondTurnActivateRoadPlaces.roadPlacesAvailable = true;
		
		secondTurnActivateRoadPlaces.preference = "";
		
		plan = "ActivateRoadPlaces;";
		status.put(secondTurnActivateRoadPlaces, plan);				
	
		
		//Konkreter Fall für den ersten Zug, wenn eine kostenlose Straße gebaut werden darf
		Status secondTurnRoad = new Status();

		//Wichtig
		secondTurnRoad.roadPlacesActive = true;
		secondTurnRoad.isFirstTurn = false;
		secondTurnRoad.isSecondTurn = true;
		secondTurnRoad.freeBuild = false;
		secondTurnRoad.freeBuildRoad = true;
		
		
		//Unwichtig
		secondTurnRoad.victoryPoints = 0;
		secondTurnRoad.longestRoad = 0;
		secondTurnRoad.hasLongestRoad = false;
		
		/*secondTurnRoad.bricks = 0;
		secondTurnRoad.wheat = 0;
		secondTurnRoad.stone = 0;
		secondTurnRoad.wood = 0;
		secondTurnRoad.sheep= 0;*/
	
		//secondTurnRoad.villages = new ArrayList<~>();
		//secondTurnRoad.roads = new ArrayList<~>();
		
		secondTurnRoad.isAbledToEndTurn = false;
		secondTurnRoad.allowedToRollDice = false;
		
		secondTurnRoad.cityPlacesAvailable = true;
		secondTurnRoad.villagePlacesAvailable = true;
		secondTurnRoad.roadPlacesAvailable = true;
		
		secondTurnRoad.preference = "";
		
		plan = "BuildRoad;ActivateRoadPlaces;";
		status.put(secondTurnRoad, plan);
		
		
		//Konkreter Fall für den zweiten Zug, wenn beendet werden soll
		Status secondTurnEnd = new Status();

		//Wichtig
		secondTurnEnd.isFirstTurn = false;
		secondTurnEnd.isSecondTurn = true;
		secondTurnEnd.freeBuild = false;
		secondTurnEnd.freeBuildRoad = false;
		secondTurnEnd.isAbledToEndTurn = true;
		
		
		//Unwichtig
		secondTurnEnd.victoryPoints = 0;
		secondTurnEnd.longestRoad = 1;
		secondTurnEnd.hasLongestRoad = false;
		
		/*secondTurnEnd.bricks = 0;
		secondTurnEnd.wheat = 0;
		secondTurnEnd.stone = 0;
		secondTurnEnd.wood = 0;
		secondTurnEnd.sheep= 0;*/
	
		//secondTurnRoad.villages = new ArrayList<~>();
		//secondTurnRoad.roads = new ArrayList<~>();	

		secondTurnEnd.allowedToRollDice = false;
		
		secondTurnEnd.cityPlacesAvailable = true;
		secondTurnEnd.villagePlacesAvailable = true;
		secondTurnEnd.roadPlacesAvailable = true;
		
		secondTurnEnd.preference = "";
		
		plan = "EndTurn;";
		status.put(secondTurnEnd, plan);
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status activateCityBuildPlaces = new Status();

		//Wichtig
		activateCityBuildPlaces.isFirstTurn = false;
		activateCityBuildPlaces.isAbledToBuildCity = true;
		activateCityBuildPlaces.isAbledToBuildVillage = true;
		activateCityBuildPlaces.isAbledToBuildRoad = true;
		
		activateCityBuildPlaces.isSecondTurn = false;
		activateCityBuildPlaces.freeBuild = false;
		activateCityBuildPlaces.freeBuildRoad = false;
		
		activateCityBuildPlaces.isAbledToEndTurn = true;
		activateCityBuildPlaces.allowedToRollDice = false;
		
		activateCityBuildPlaces.cityPlacesAvailable = true;
		activateCityBuildPlaces.villagePlacesAvailable = false;
		activateCityBuildPlaces.roadPlacesAvailable = true;
		
		
		//Unwichtig
		activateCityBuildPlaces.victoryPoints = 0;
		activateCityBuildPlaces.longestRoad = 0;
		activateCityBuildPlaces.hasLongestRoad = false;
		
		activateCityBuildPlaces.preference = "";	
		
		plan = "ActivateCityPlaces;";
		status.put(activateCityBuildPlaces, plan);
		
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status activateCityBuildPlaces2 = new Status();

		//Wichtig
		activateCityBuildPlaces2.isFirstTurn = false;
		activateCityBuildPlaces2.isAbledToBuildCity = true;
		activateCityBuildPlaces2.isAbledToBuildVillage = true;
		activateCityBuildPlaces2.isAbledToBuildRoad = true;
		activateCityBuildPlaces2.isSecondTurn = false;
		activateCityBuildPlaces2.freeBuild = false;
		activateCityBuildPlaces2.freeBuildRoad = false;
		
		activateCityBuildPlaces2.isAbledToEndTurn = true;
		activateCityBuildPlaces2.allowedToRollDice = false;
		
		activateCityBuildPlaces2.cityPlacesAvailable = true;
		activateCityBuildPlaces2.villagePlacesAvailable = true;
		activateCityBuildPlaces2.roadPlacesAvailable = true;
		
		//Unwichtig
		activateCityBuildPlaces2.victoryPoints = 0;
		activateCityBuildPlaces2.longestRoad = 0;
		activateCityBuildPlaces2.hasLongestRoad = false;
		
		activateCityBuildPlaces.preference = "";	
		
		plan = "ActivateCityPlaces;";
		status.put(activateCityBuildPlaces2, plan);
		
		
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status activateVillageBuildPlaces = new Status();

		//Wichtig
		activateVillageBuildPlaces.isFirstTurn = false;
		activateVillageBuildPlaces.isAbledToBuildVillage = true;
		activateVillageBuildPlaces.isAbledToBuildRoad = true;
		activateVillageBuildPlaces.isSecondTurn = false;
		activateVillageBuildPlaces.freeBuild = false;
		activateVillageBuildPlaces.freeBuildRoad = false;
		
		activateVillageBuildPlaces.cityPlacesAvailable = true;
		activateVillageBuildPlaces.villagePlacesAvailable = true;
		activateVillageBuildPlaces.roadPlacesAvailable = true;
		
		activateVillageBuildPlaces.isAbledToEndTurn = true;
		activateVillageBuildPlaces.allowedToRollDice = false;
		
		//Unwichtig
		activateVillageBuildPlaces.victoryPoints = 0;
		activateVillageBuildPlaces.longestRoad = 0;
		activateVillageBuildPlaces.hasLongestRoad = false;
	
		//activateVillageBuildPlaces.villages = new ArrayList<~>();
		//activateVillageBuildPlaces.roads = new ArrayList<~>();
		
		activateVillageBuildPlaces.preference = "";
		
		plan = "ActivateVillagePlaces;";
		status.put(activateVillageBuildPlaces, plan);
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status activateVillageBuildPlaces2 = new Status();

		//Wichtig
		activateVillageBuildPlaces2.isFirstTurn = false;
		activateVillageBuildPlaces2.isAbledToBuildVillage = true;
		activateVillageBuildPlaces2.isAbledToBuildRoad = true;
		activateVillageBuildPlaces2.isSecondTurn = false;
		activateVillageBuildPlaces2.freeBuild = false;
		activateVillageBuildPlaces2.freeBuildRoad = false;
		
		activateVillageBuildPlaces2.cityPlacesAvailable = false;
		activateVillageBuildPlaces2.villagePlacesAvailable = true;
		activateVillageBuildPlaces2.roadPlacesAvailable = true;
		
		activateVillageBuildPlaces2.isAbledToEndTurn = true;
		activateVillageBuildPlaces2.allowedToRollDice = false;
		
		//Unwichtig
		activateVillageBuildPlaces2.victoryPoints = 0;
		activateVillageBuildPlaces2.longestRoad = 0;
		activateVillageBuildPlaces2.hasLongestRoad = false;
	
		//activateVillageBuildPlaces.villages = new ArrayList<~>();
		//activateVillageBuildPlaces.roads = new ArrayList<~>();
		
		activateVillageBuildPlaces2.preference = "";
		
		plan = "ActivateVillagePlaces;";
		status.put(activateVillageBuildPlaces2, plan);
		
		
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status activateRoadBuildPlaces = new Status();

		//Wichtig
		activateRoadBuildPlaces.isFirstTurn = false;
		activateRoadBuildPlaces.isAbledToBuildRoad = true;
		activateRoadBuildPlaces.isSecondTurn = false;
		activateRoadBuildPlaces.freeBuild = false;
		activateRoadBuildPlaces.freeBuildRoad = false;
		
		activateRoadBuildPlaces.cityPlacesAvailable = true;
		activateRoadBuildPlaces.villagePlacesAvailable = true;
		activateRoadBuildPlaces.roadPlacesAvailable = true;
		
		activateRoadBuildPlaces.isAbledToEndTurn = true;
		activateRoadBuildPlaces.allowedToRollDice = false;
		
		//Unwichtig
		activateRoadBuildPlaces.victoryPoints = 0;
		activateRoadBuildPlaces.longestRoad = 0;
		activateRoadBuildPlaces.hasLongestRoad = false;
		activateRoadBuildPlaces.preference = "";
		
		plan = "ActivateRoadPlaces;";
		status.put(activateRoadBuildPlaces, plan);
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status activateRoadBuildPlaces2 = new Status();

		//Wichtig
		activateRoadBuildPlaces2.isFirstTurn = false;
		activateRoadBuildPlaces2.isAbledToBuildRoad = true;
		activateRoadBuildPlaces2.isSecondTurn = false;
		activateRoadBuildPlaces2.freeBuild = false;
		activateRoadBuildPlaces2.freeBuildRoad = false;
		
		activateRoadBuildPlaces2.cityPlacesAvailable = true;
		activateRoadBuildPlaces2.villagePlacesAvailable = false;
		activateRoadBuildPlaces2.roadPlacesAvailable = true;
		
		activateRoadBuildPlaces2.isAbledToEndTurn = true;
		activateRoadBuildPlaces2.allowedToRollDice = false;
		
		//Unwichtig
		activateRoadBuildPlaces2.victoryPoints = 0;
		activateRoadBuildPlaces2.longestRoad = 0;
		activateRoadBuildPlaces2.hasLongestRoad = false;
		activateRoadBuildPlaces2.preference = "";
		
		plan = "ActivateRoadPlaces;";
		status.put(activateRoadBuildPlaces2, plan);
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status activateRoadBuildPlaces4 = new Status();

		//Wichtig
		activateRoadBuildPlaces4.isFirstTurn = false;
		activateRoadBuildPlaces4.isAbledToBuildVillage = true;
		activateRoadBuildPlaces4.isAbledToBuildRoad = true;
		activateRoadBuildPlaces4.isSecondTurn = false;
		activateRoadBuildPlaces4.freeBuild = false;
		activateRoadBuildPlaces4.freeBuildRoad = false;
		
		activateRoadBuildPlaces4.cityPlacesAvailable = false;
		activateRoadBuildPlaces4.villagePlacesAvailable = false;
		activateRoadBuildPlaces4.roadPlacesAvailable = true;
		
		activateRoadBuildPlaces4.isAbledToEndTurn = true;
		activateRoadBuildPlaces4.allowedToRollDice = false;
		
		//Unwichtig
		activateRoadBuildPlaces4.victoryPoints = 0;
		activateRoadBuildPlaces4.longestRoad = 0;
		activateRoadBuildPlaces4.hasLongestRoad = false;
		activateRoadBuildPlaces4.preference = "";
		
		plan = "ActivateRoadPlaces;";
		status.put(activateRoadBuildPlaces4, plan);
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status activateRoadBuildPlaces3 = new Status();

		//Wichtig
		activateRoadBuildPlaces3.isFirstTurn = false;
		activateRoadBuildPlaces3.isAbledToBuildRoad = true;
		activateRoadBuildPlaces3.isSecondTurn = false;
		activateRoadBuildPlaces3.freeBuild = false;
		activateRoadBuildPlaces3.freeBuildRoad = false;
		
		activateRoadBuildPlaces3.cityPlacesAvailable = false;
		activateRoadBuildPlaces3.villagePlacesAvailable = false;
		activateRoadBuildPlaces3.roadPlacesAvailable = true;
		
		activateRoadBuildPlaces3.isAbledToEndTurn = true;
		activateRoadBuildPlaces3.allowedToRollDice = false;
		
		//Unwichtig
		activateRoadBuildPlaces3.victoryPoints = 0;
		activateRoadBuildPlaces3.longestRoad = 0;
		activateRoadBuildPlaces3.hasLongestRoad = false;
		activateRoadBuildPlaces3.preference = "";
		
		plan = "ActivateRoadPlaces;";
		status.put(activateRoadBuildPlaces3, plan);
		
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildCity = new Status();

		//Wichtig
		buildCity.cityPlacesActive = true;
		buildCity.isAbledToBuildCity = true;
		buildCity.isAbledToBuildVillage = true;
		buildCity.isAbledToBuildRoad = true;
		buildCity.isFirstTurn = false;
		buildCity.isSecondTurn = false;
		buildCity.freeBuild = false;
		buildCity.freeBuildRoad = false;
		
		buildCity.isAbledToEndTurn = true;
		buildCity.allowedToRollDice = false;
		
		buildCity.cityPlacesAvailable = true;
		buildCity.villagePlacesAvailable = true;
		buildCity.roadPlacesAvailable = true;
		
		//Unwichtig
		buildCity.victoryPoints = 0;
		buildCity.longestRoad = 0;
		buildCity.hasLongestRoad = false;
		
		buildCity.preference = "";
		
		plan = "BuildCity;ActivateCityPlaces;";
		status.put(buildCity, plan);
		
		
		
		Status buildCity2 = new Status();

		//Wichtig
		buildCity2.cityPlacesActive = true;
		buildCity2.isAbledToBuildCity = true;
		buildCity2.isAbledToBuildVillage = true;
		buildCity2.isAbledToBuildRoad = true;
		buildCity2.isFirstTurn = false;
		buildCity2.isSecondTurn = false;
		buildCity2.freeBuild = false;
		buildCity2.freeBuildRoad = false;
		
		buildCity2.isAbledToEndTurn = true;
		buildCity2.allowedToRollDice = false;
		
		buildCity2.cityPlacesAvailable = true;
		buildCity2.villagePlacesAvailable = false;
		buildCity2.roadPlacesAvailable = true;
		
		//Unwichtig
		buildCity2.victoryPoints = 0;
		buildCity2.longestRoad = 0;
		buildCity2.hasLongestRoad = false;
		
		buildCity2.preference = "";
		
		plan = "BuildCity;ActivateCityPlaces;";
		status.put(buildCity2, plan);
		
		
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildVillage = new Status();

		//Wichtig
		buildVillage.villagePlacesActive = true;
		buildVillage.isAbledToBuildVillage = true;
		buildVillage.isAbledToBuildRoad = true;
		buildVillage.isFirstTurn = false;
		buildVillage.isSecondTurn = false;
		buildVillage.freeBuild = false;
		buildVillage.freeBuildRoad = false;
		
		buildVillage.cityPlacesAvailable = true;
		buildVillage.villagePlacesAvailable = true;
		buildVillage.roadPlacesAvailable = true;
		
		buildVillage.isAbledToEndTurn = true;
		buildVillage.allowedToRollDice = false;
		
		//Unwichtig
		buildVillage.victoryPoints = 0;
		buildVillage.longestRoad = 0;
		buildVillage.hasLongestRoad = false;
		
		buildVillage.preference = "";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(buildVillage, plan);

		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildVillage2 = new Status();

		//Wichtig
		buildVillage2.villagePlacesActive = true;
		buildVillage2.isAbledToBuildVillage = true;
		buildVillage2.isAbledToBuildRoad = true;
		buildVillage2.isFirstTurn = false;
		buildVillage2.isSecondTurn = false;
		buildVillage2.freeBuild = false;
		buildVillage2.freeBuildRoad = false;
		
		buildVillage2.cityPlacesAvailable = false;
		buildVillage2.villagePlacesAvailable = true;
		buildVillage2.roadPlacesAvailable = true;
		
		buildVillage2.isAbledToEndTurn = true;
		buildVillage2.allowedToRollDice = false;
		
		//Unwichtig
		buildVillage2.victoryPoints = 0;
		buildVillage2.longestRoad = 0;
		buildVillage2.hasLongestRoad = false;
		
		buildVillage2.preference = "";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(buildVillage2, plan);
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildRoad = new Status();

		//Wichtig
		buildRoad.roadPlacesActive = true;
		buildRoad.isAbledToBuildRoad = true;
		buildRoad.isFirstTurn = false;
		buildRoad.isSecondTurn = false;
		buildRoad.freeBuild = false;
		buildRoad.freeBuildRoad = false;
		
		buildRoad.cityPlacesAvailable = true;
		buildRoad.villagePlacesAvailable = true;
		buildRoad.roadPlacesAvailable = true;
		
		buildRoad.isAbledToEndTurn = true;
		buildRoad.allowedToRollDice = false;
		
		//Unwichtig
		buildRoad.victoryPoints = 0;
		buildRoad.longestRoad = 0;
		buildRoad.hasLongestRoad = false;
		
		buildRoad.preference = "";
		
		plan = "BuildRoad;ActivateRoadPlaces;";
		status.put(buildRoad, plan);
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildRoad2 = new Status();

		//Wichtig
		buildRoad2.roadPlacesActive = true;
		buildRoad2.isAbledToBuildRoad = true;
		buildRoad2.isFirstTurn = false;
		buildRoad2.isSecondTurn = false;
		buildRoad2.freeBuild = false;
		buildRoad2.freeBuildRoad = false;
		
		buildRoad2.cityPlacesAvailable = false;
		buildRoad2.villagePlacesAvailable = true;
		buildRoad2.roadPlacesAvailable = true;
		
		buildRoad2.isAbledToEndTurn = true;
		buildRoad2.allowedToRollDice = false;
		
		//Unwichtig
		buildRoad2.victoryPoints = 0;
		buildRoad2.longestRoad = 0;
		buildRoad2.hasLongestRoad = false;
		buildRoad2.preference = "";
		
		plan = "BuildRoad;ActivateRoadPlaces;";
		status.put(buildRoad2, plan);
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildRoad4 = new Status();

		//Wichtig
		buildRoad4.roadPlacesActive = true;
		buildRoad4.isAbledToBuildVillage = true;
		buildRoad4.isAbledToBuildRoad = true;
		buildRoad4.isFirstTurn = false;
		buildRoad4.isSecondTurn = false;
		buildRoad4.freeBuild = false;
		buildRoad4.freeBuildRoad = false;
		
		buildRoad4.cityPlacesAvailable = false;
		buildRoad4.villagePlacesAvailable = false;
		buildRoad4.roadPlacesAvailable = true;
		
		buildRoad4.isAbledToEndTurn = true;
		buildRoad4.allowedToRollDice = false;
		
		//Unwichtig
		buildRoad4.victoryPoints = 0;
		buildRoad4.longestRoad = 0;
		buildRoad4.hasLongestRoad = false;
		buildRoad4.preference = "";
		
		plan = "BuildRoad;ActivateRoadPlaces;";
		status.put(buildRoad4, plan);
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildRoad3 = new Status();

		//Wichtig
		buildRoad3.roadPlacesActive = true;
		buildRoad3.isAbledToBuildRoad = true;
		buildRoad3.isFirstTurn = false;
		buildRoad3.isSecondTurn = false;
		buildRoad3.freeBuild = false;
		buildRoad3.freeBuildRoad = false;
		
		buildRoad3.cityPlacesAvailable = false;
		buildRoad3.villagePlacesAvailable = false;
		buildRoad3.roadPlacesAvailable = true;
		
		buildRoad3.isAbledToEndTurn = true;
		buildRoad3.allowedToRollDice = false;
		
		//Unwichtig
		buildRoad3.victoryPoints = 0;
		buildRoad3.longestRoad = 0;
		buildRoad3.hasLongestRoad = false;
		buildRoad3.preference = "";
		
		plan = "BuildRoad;ActivateRoadPlaces;";
		status.put(buildRoad3, plan);
		
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status rollDice = new Status();

		//Wichtig
		rollDice.isFirstTurn = false;
		rollDice.isSecondTurn = false;
		rollDice.freeBuild = false;
		rollDice.freeBuildRoad = false;
		
		rollDice.isAbledToEndTurn = false;
		rollDice.allowedToRollDice = true;
		
		//Unwichtig
		rollDice.victoryPoints = 0;
		rollDice.longestRoad = 0;
		rollDice.hasLongestRoad = false;
		
		rollDice.preference = "";
		
		plan = "RollDice;";
		status.put(rollDice, plan);
		
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status endTurn = new Status();

		//Wichtig
		endTurn.isFirstTurn = false;
		endTurn.isSecondTurn = false;
		endTurn.freeBuild = false;
		endTurn.freeBuildRoad = false;
		
		endTurn.isAbledToEndTurn = true;
		endTurn.allowedToRollDice = false;
		
		//Unwichtig
		endTurn.victoryPoints = 0;
		endTurn.longestRoad = 0;
		endTurn.hasLongestRoad = false;
		
		endTurn.preference = "";
		
		plan = "EndTurn";
		status.put(endTurn, plan);
		
		
		//Konkreter Fall für den ersten Zug, wenn ein Dorf gebaut werden darf
		Status firstTurnVillageBricks = new Status();

		//Wichtig
		firstTurnVillageBricks.villagePlacesActive = true;
		firstTurnVillageBricks.isFirstTurn = true;
		firstTurnVillageBricks.isSecondTurn = false;
		firstTurnVillageBricks.freeBuild = true;
		firstTurnVillageBricks.freeBuildRoad = false;
		
		
		//Unwichtig
		firstTurnVillageBricks.victoryPoints = 0;
		firstTurnVillageBricks.longestRoad = 0;
		firstTurnVillageBricks.hasLongestRoad = false;
		
		firstTurnVillageBricks.cityPlacesAvailable = false;
		firstTurnVillageBricks.villagePlacesAvailable = true;
		firstTurnVillageBricks.roadPlacesAvailable = true;
		
		firstTurnVillageBricks.isAbledToEndTurn = false;
		firstTurnVillageBricks.allowedToRollDice = false;
		
		firstTurnVillageBricks.preference = "brick";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(firstTurnVillageBricks, plan);	
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status firstTurnVillageWood = new Status();

		//Wichtig
		firstTurnVillageWood.villagePlacesActive = true;
		firstTurnVillageWood.isFirstTurn = true;
		firstTurnVillageWood.isSecondTurn = false;
		firstTurnVillageWood.freeBuild = true;
		firstTurnVillageWood.freeBuildRoad = false;
		
		
		//Unwichtig
		firstTurnVillageWood.victoryPoints = 0;
		firstTurnVillageWood.longestRoad = 0;
		firstTurnVillageWood.hasLongestRoad = false;
		
		firstTurnVillageWood.cityPlacesAvailable = false;
		firstTurnVillageWood.villagePlacesAvailable = true;
		firstTurnVillageWood.roadPlacesAvailable = true;
		
		firstTurnVillageWood.isAbledToEndTurn = false;
		firstTurnVillageWood.allowedToRollDice = false;
		
		firstTurnVillageWood.preference = "wood";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(firstTurnVillageWood, plan);	
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status firstTurnVillageSheep = new Status();

		//Wichtig
		firstTurnVillageSheep.villagePlacesActive = true;
		firstTurnVillageSheep.isFirstTurn = true;
		firstTurnVillageSheep.isSecondTurn = false;
		firstTurnVillageSheep.freeBuild = true;
		firstTurnVillageSheep.freeBuildRoad = false;
		
		
		//Unwichtig
		firstTurnVillageSheep.victoryPoints = 0;
		firstTurnVillageSheep.longestRoad = 0;
		firstTurnVillageSheep.hasLongestRoad = false;
		
		firstTurnVillageSheep.cityPlacesAvailable = false;
		firstTurnVillageSheep.villagePlacesAvailable = true;
		firstTurnVillageSheep.roadPlacesAvailable = true;
		
		firstTurnVillageSheep.isAbledToEndTurn = false;
		firstTurnVillageSheep.allowedToRollDice = false;
		
		firstTurnVillageSheep.preference = "sheep";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(firstTurnVillageSheep, plan);		
				
			
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status firstTurnVillageWheat = new Status();

		//Wichtig
		firstTurnVillageWheat.villagePlacesActive = true;
		firstTurnVillageWheat.isFirstTurn = true;
		firstTurnVillageWheat.isSecondTurn = false;
		firstTurnVillageWheat.freeBuild = true;
		firstTurnVillageWheat.freeBuildRoad = false;
		
		
		//Unwichtig
		firstTurnVillageWheat.victoryPoints = 0;
		firstTurnVillageWheat.longestRoad = 0;
		firstTurnVillageWheat.hasLongestRoad = false;
		
		firstTurnVillageWheat.cityPlacesAvailable = false;
		firstTurnVillageWheat.villagePlacesAvailable = true;
		firstTurnVillageWheat.roadPlacesAvailable = true;
		
		firstTurnVillageWheat.isAbledToEndTurn = false;
		firstTurnVillageWheat.allowedToRollDice = false;
		
		firstTurnVillageWheat.preference = "wheat";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(firstTurnVillageWheat, plan);
		
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status firstTurnVillageStone = new Status();

		//Wichtig
		firstTurnVillageStone.villagePlacesActive = true;
		firstTurnVillageStone.isFirstTurn = true;
		firstTurnVillageStone.isSecondTurn = false;
		firstTurnVillageStone.freeBuild = true;
		firstTurnVillageStone.freeBuildRoad = false;
		
		
		//Unwichtig
		firstTurnVillageStone.victoryPoints = 0;
		firstTurnVillageStone.longestRoad = 0;
		firstTurnVillageStone.hasLongestRoad = false;
		
		firstTurnVillageStone.cityPlacesAvailable = false;
		firstTurnVillageStone.villagePlacesAvailable = true;
		firstTurnVillageStone.roadPlacesAvailable = true;
		
		firstTurnVillageStone.isAbledToEndTurn = false;
		firstTurnVillageStone.allowedToRollDice = false;
		
		firstTurnVillageStone.preference = "wheat";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(firstTurnVillageStone, plan);
		
		
		
		//Konkreter Fall für den ersten Zug, wenn eine kostenlose Straße gebaut werden darf
		Status secondTurnVillageBricks = new Status();

		//Wichtig
		secondTurnVillageBricks.villagePlacesActive = true;
		secondTurnVillageBricks.isFirstTurn = false;
		secondTurnVillageBricks.isSecondTurn = true;
		secondTurnVillageBricks.freeBuild = true;
		secondTurnVillageBricks.freeBuildRoad = false;
		
		//Unwichtig
		secondTurnVillageBricks.victoryPoints = 0;
		secondTurnVillageBricks.longestRoad = 1;
		secondTurnVillageBricks.hasLongestRoad = false;
		
		secondTurnVillageBricks.cityPlacesAvailable = true;
		secondTurnVillageBricks.villagePlacesAvailable = true;
		secondTurnVillageBricks.roadPlacesAvailable = true;
		
		secondTurnVillageBricks.isAbledToEndTurn = false;
		secondTurnVillageBricks.allowedToRollDice = false;
		
		secondTurnVillageBricks.preference = "brick";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(secondTurnVillageBricks, plan);
		
		
		
		//Konkreter Fall für den ersten Zug, wenn eine kostenlose Straße gebaut werden darf
		Status secondTurnVillageWood = new Status();

		//Wichtig
		secondTurnVillageWood.villagePlacesActive = true;
		secondTurnVillageWood.isFirstTurn = false;
		secondTurnVillageWood.isSecondTurn = true;
		secondTurnVillageWood.freeBuild = true;
		secondTurnVillageWood.freeBuildRoad = false;
		
		//Unwichtig
		secondTurnVillageWood.victoryPoints = 0;
		secondTurnVillageWood.longestRoad = 1;
		secondTurnVillageWood.hasLongestRoad = false;
		
		secondTurnVillageWood.cityPlacesAvailable = true;
		secondTurnVillageWood.villagePlacesAvailable = true;
		secondTurnVillageWood.roadPlacesAvailable = true;
		
		secondTurnVillageWood.isAbledToEndTurn = false;
		secondTurnVillageWood.allowedToRollDice = false;
		
		secondTurnVillageWood.preference = "wood";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(secondTurnVillageWood, plan);
		
		
		//Konkreter Fall für den ersten Zug, wenn eine kostenlose Straße gebaut werden darf
		Status secondTurnVillageSheep = new Status();

		//Wichtig
		secondTurnVillageSheep.villagePlacesActive = true;
		secondTurnVillageSheep.isFirstTurn = false;
		secondTurnVillageSheep.isSecondTurn = true;
		secondTurnVillageSheep.freeBuild = true;
		secondTurnVillageSheep.freeBuildRoad = false;
		
		//Unwichtig
		secondTurnVillageSheep.victoryPoints = 0;
		secondTurnVillageSheep.longestRoad = 1;
		secondTurnVillageSheep.hasLongestRoad = false;
		
		secondTurnVillageSheep.cityPlacesAvailable = true;
		secondTurnVillageSheep.villagePlacesAvailable = true;
		secondTurnVillageSheep.roadPlacesAvailable = true;
		
		secondTurnVillageSheep.isAbledToEndTurn = false;
		secondTurnVillageSheep.allowedToRollDice = false;
		
		secondTurnVillageSheep.preference = "sheep";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(secondTurnVillageSheep, plan);
		
		
		//Konkreter Fall für den ersten Zug, wenn eine kostenlose Straße gebaut werden darf
		Status secondTurnVillageWheat = new Status();

		//Wichtig
		secondTurnVillageWheat.villagePlacesActive = true;
		secondTurnVillageWheat.isFirstTurn = false;
		secondTurnVillageWheat.isSecondTurn = true;
		secondTurnVillageWheat.freeBuild = true;
		secondTurnVillageWheat.freeBuildRoad = false;
		
		//Unwichtig
		secondTurnVillageWheat.victoryPoints = 0;
		secondTurnVillageWheat.longestRoad = 1;
		secondTurnVillageWheat.hasLongestRoad = false;
		
		secondTurnVillageWheat.cityPlacesAvailable = true;
		secondTurnVillageWheat.villagePlacesAvailable = true;
		secondTurnVillageWheat.roadPlacesAvailable = true;
		
		secondTurnVillageWheat.isAbledToEndTurn = false;
		secondTurnVillageWheat.allowedToRollDice = false;
		
		secondTurnVillageWheat.preference = "wheat";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(secondTurnVillageWheat, plan);
		
		
		//Konkreter Fall für den ersten Zug, wenn eine kostenlose Straße gebaut werden darf
		Status secondTurnVillageStone = new Status();

		//Wichtig
		secondTurnVillageStone.villagePlacesActive = true;
		secondTurnVillageStone.isFirstTurn = false;
		secondTurnVillageStone.isSecondTurn = true;
		secondTurnVillageStone.freeBuild = true;
		secondTurnVillageStone.freeBuildRoad = false;
		
		//Unwichtig
		secondTurnVillageStone.victoryPoints = 0;
		secondTurnVillageStone.longestRoad = 1;
		secondTurnVillageStone.hasLongestRoad = false;
		
		secondTurnVillageStone.cityPlacesAvailable = true;
		secondTurnVillageStone.villagePlacesAvailable = true;
		secondTurnVillageStone.roadPlacesAvailable = true;
		
		secondTurnVillageStone.isAbledToEndTurn = false;
		secondTurnVillageStone.allowedToRollDice = false;
		
		secondTurnVillageStone.preference = "rock";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(secondTurnVillageStone, plan);
		
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildVillageBricks = new Status();

		//Wichtig
		buildVillageBricks.villagePlacesActive = true;
		buildVillageBricks.isAbledToBuildVillage = true;
		buildVillageBricks.isAbledToBuildRoad = true;
		buildVillageBricks.isFirstTurn = false;
		buildVillageBricks.isSecondTurn = false;
		buildVillageBricks.freeBuild = false;
		buildVillageBricks.freeBuildRoad = false;
		
		buildVillageBricks.isAbledToEndTurn = true;
		buildVillageBricks.allowedToRollDice = false;
		
		buildVillageBricks.cityPlacesAvailable = true;
		buildVillageBricks.villagePlacesAvailable = true;
		buildVillageBricks.roadPlacesAvailable = true;
		
		//Unwichtig
		buildVillageBricks.victoryPoints = 0;
		buildVillageBricks.longestRoad = 0;
		buildVillageBricks.hasLongestRoad = false;
		
		buildVillageBricks.preference = "brick";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(buildVillageBricks, plan);
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildVillageBricks2 = new Status();

		//Wichtig
		buildVillageBricks2.villagePlacesActive = true;
		buildVillageBricks2.isAbledToBuildVillage = true;
		buildVillageBricks2.isAbledToBuildRoad = true;
		buildVillageBricks2.isFirstTurn = false;
		buildVillageBricks2.isSecondTurn = false;
		buildVillageBricks2.freeBuild = false;
		buildVillageBricks2.freeBuildRoad = false;
		
		buildVillageBricks2.isAbledToEndTurn = true;
		buildVillageBricks2.allowedToRollDice = false;
		
		buildVillageBricks2.cityPlacesAvailable = false;
		buildVillageBricks2.villagePlacesAvailable = true;
		buildVillageBricks2.roadPlacesAvailable = true;
		
		//Unwichtig
		buildVillageBricks2.victoryPoints = 0;
		buildVillageBricks2.longestRoad = 0;
		buildVillageBricks2.hasLongestRoad = false;
		
		buildVillageBricks2.preference = "brick";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(buildVillageBricks2, plan);
		

		
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildVillageWood = new Status();

		//Wichtig
		buildVillageWood.villagePlacesActive = true;
		buildVillageWood.isAbledToBuildVillage = true;
		buildVillageWood.isAbledToBuildRoad = true;
		buildVillageWood.isFirstTurn = false;
		buildVillageWood.isSecondTurn = false;
		buildVillageWood.freeBuild = false;
		buildVillageWood.freeBuildRoad = false;
		
		buildVillageWood.isAbledToEndTurn = true;
		buildVillageWood.allowedToRollDice = false;
		
		buildVillageWood.cityPlacesAvailable = true;
		buildVillageWood.villagePlacesAvailable = true;
		buildVillageWood.roadPlacesAvailable = true;
		
		
		//Unwichtig
		buildVillageWood.victoryPoints = 0;
		buildVillageWood.longestRoad = 0;
		buildVillageWood.hasLongestRoad = false;
	
		
		buildVillageWood.preference = "wood";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(buildVillageWood, plan);
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildVillageWood2 = new Status();

		//Wichtig
		buildVillageWood2.villagePlacesActive = true;
		buildVillageWood2.isAbledToBuildVillage = true;
		buildVillageWood2.isAbledToBuildRoad = true;
		buildVillageWood2.isFirstTurn = false;
		buildVillageWood2.isSecondTurn = false;
		buildVillageWood2.freeBuild = false;
		buildVillageWood2.freeBuildRoad = false;
		
		buildVillageWood2.isAbledToEndTurn = true;
		buildVillageWood2.allowedToRollDice = false;
		
		buildVillageWood2.cityPlacesAvailable = false;
		buildVillageWood2.villagePlacesAvailable = true;
		buildVillageWood2.roadPlacesAvailable = true;
		
		//Unwichtig
		buildVillageWood2.victoryPoints = 0;
		buildVillageWood2.longestRoad = 0;
		buildVillageWood2.hasLongestRoad = false;
	
		
		buildVillageWood2.preference = "wood";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(buildVillageWood2, plan);
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildVillageSheep = new Status();

		//Wichtig
		buildVillageSheep.villagePlacesActive = true;
		buildVillageSheep.isAbledToBuildVillage = true;
		buildVillageSheep.isAbledToBuildRoad = true;
		buildVillageSheep.isFirstTurn = false;
		buildVillageSheep.isSecondTurn = false;
		buildVillageSheep.freeBuild = false;
		buildVillageSheep.freeBuildRoad = false;
		
		buildVillageSheep.isAbledToEndTurn = true;
		buildVillageSheep.allowedToRollDice = false;
		
		buildVillageSheep.cityPlacesAvailable = true;
		buildVillageSheep.villagePlacesAvailable = true;
		buildVillageSheep.roadPlacesAvailable = true;
		
		//Unwichtig
		buildVillageSheep.victoryPoints = 0;
		buildVillageSheep.longestRoad = 0;
		buildVillageSheep.hasLongestRoad = false;
	
		
		buildVillageSheep.preference = "sheep";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(buildVillageSheep, plan);
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildVillageSheep2 = new Status();

		//Wichtig
		buildVillageSheep2.villagePlacesActive = true;
		buildVillageSheep2.isAbledToBuildVillage = true;
		buildVillageSheep2.isAbledToBuildRoad = true;
		buildVillageSheep2.isFirstTurn = false;
		buildVillageSheep2.isSecondTurn = false;
		buildVillageSheep2.freeBuild = false;
		buildVillageSheep2.freeBuildRoad = false;
		
		buildVillageSheep2.isAbledToEndTurn = true;
		buildVillageSheep2.allowedToRollDice = false;
		
		buildVillageSheep2.cityPlacesAvailable = false;
		buildVillageSheep2.villagePlacesAvailable = true;
		buildVillageSheep2.roadPlacesAvailable = true;
		
		//Unwichtig
		buildVillageSheep2.victoryPoints = 0;
		buildVillageSheep2.longestRoad = 0;
		buildVillageSheep2.hasLongestRoad = false;
	
		
		buildVillageSheep2.preference = "sheep";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(buildVillageSheep2, plan);
		
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildVillageWheat = new Status();

		//Wichtig
		buildVillageWheat.villagePlacesActive = true;
		buildVillageWheat.isAbledToBuildVillage = true;
		buildVillageWheat.isAbledToBuildRoad = true;
		buildVillageWheat.isFirstTurn = false;
		buildVillageWheat.isSecondTurn = false;
		buildVillageWheat.freeBuild = false;
		buildVillageWheat.freeBuildRoad = false;
		
		buildVillageWheat.isAbledToEndTurn = true;
		buildVillageWheat.allowedToRollDice = false;
		
		buildVillageWheat.cityPlacesAvailable = true;
		buildVillageWheat.villagePlacesAvailable = true;
		buildVillageWheat.roadPlacesAvailable = true;
		
		//Unwichtig
		buildVillageWheat.victoryPoints = 0;
		buildVillageWheat.longestRoad = 0;
		buildVillageWheat.hasLongestRoad = false;
	
		
		buildVillageWheat.preference = "wheat";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(buildVillageWheat, plan);
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildVillageWheat2 = new Status();

		//Wichtig
		buildVillageWheat2.villagePlacesActive = true;
		buildVillageWheat2.isAbledToBuildVillage = true;
		buildVillageWheat2.isAbledToBuildRoad = true;
		buildVillageWheat2.isFirstTurn = false;
		buildVillageWheat2.isSecondTurn = false;
		buildVillageWheat2.freeBuild = false;
		buildVillageWheat2.freeBuildRoad = false;
		
		buildVillageWheat2.isAbledToEndTurn = true;
		buildVillageWheat2.allowedToRollDice = false;
		
		buildVillageWheat2.cityPlacesAvailable = false;
		buildVillageWheat2.villagePlacesAvailable = true;
		buildVillageWheat2.roadPlacesAvailable = true;
		
		//Unwichtig
		buildVillageWheat2.victoryPoints = 0;
		buildVillageWheat2.longestRoad = 0;
		buildVillageWheat2.hasLongestRoad = false;
		
		buildVillageWheat2.preference = "wheat";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(buildVillageWheat2, plan);
		

		
		
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildVillageStone = new Status();

		//Wichtig
		buildVillageStone.villagePlacesActive = true;
		buildVillageStone.isAbledToBuildVillage = true;
		buildVillageStone.isAbledToBuildRoad = true;
		buildVillageStone.isFirstTurn = false;
		buildVillageStone.isSecondTurn = false;
		buildVillageStone.freeBuild = false;
		buildVillageStone.freeBuildRoad = false;
		
		buildVillageStone.isAbledToEndTurn = true;
		buildVillageStone.allowedToRollDice = false;
		
		buildVillageStone.cityPlacesAvailable = true;
		buildVillageStone.villagePlacesAvailable = true;
		buildVillageStone.roadPlacesAvailable = true;
		
		//Unwichtig
		buildVillageStone.victoryPoints = 0;
		buildVillageStone.longestRoad = 0;
		buildVillageStone.hasLongestRoad = false;
	
		
		buildVillageStone.preference = "rock";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(buildVillageStone, plan);
		
		//Konkreter Fall für den ersten Zug, wenn ein kostenloses Dorf gebaut werden darf
		Status buildVillageStone2 = new Status();

		//Wichtig
		buildVillageStone2.villagePlacesActive = true;
		buildVillageStone2.isAbledToBuildVillage = true;
		buildVillageStone2.isAbledToBuildRoad = true;
		buildVillageStone2.isFirstTurn = false;
		buildVillageStone2.isSecondTurn = false;
		buildVillageStone2.freeBuild = false;
		buildVillageStone2.freeBuildRoad = false;
		
		buildVillageStone2.isAbledToEndTurn = true;
		buildVillageStone2.allowedToRollDice = false;
		
		buildVillageStone2.cityPlacesAvailable = false;
		buildVillageStone2.villagePlacesAvailable = true;
		buildVillageStone2.roadPlacesAvailable = true;
		
		//Unwichtig
		buildVillageStone2.victoryPoints = 0;
		buildVillageStone2.longestRoad = 0;
		buildVillageStone2.hasLongestRoad = false;
		
		buildVillageStone2.preference = "rock";
		
		plan = "BuildVillage;ActivateVillagePlaces;";
		status.put(buildVillageStone2, plan);
		

						
	}

	/**
	 * Simpler Getter f&uuml;r die Map, welche die DefaultCases beinhaltet.
	 * 
	 * @return Eine Map mit den DefaultCases.
	 */
	public static HashMap<Status, String> getDefaultCases() {
		return status;
	}

	public static Map getMap() {
		return map;
	}

	public static void setMap(Map map) {
		DefaultCases.map = map;
	}
}
