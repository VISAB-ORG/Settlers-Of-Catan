package cbr_util;

import java.util.HashMap;

import de.dfki.mycbr.core.model.AttributeDesc;
import de.dfki.mycbr.core.model.BooleanDesc;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.StringDesc;
import de.dfki.mycbr.core.model.SymbolDesc;
import main.CBRSystem;

/**
 * 
 * @author Tjark Harjes
 *
 */
public class RetrievalHelper {
	/**
	 * Map, in welcher alle Attribute des verwendeten Konzepts gespeichert
	 * werden.
	 */
	private static HashMap<String, AttributeDesc> attributeDescriptor = CBRSystem.getEngine().getStatusConcept()
			.getAllAttributeDescs();
	/*
	 * private static HashMap<String, AttributeDesc> attributeDescriptor =
	 * CBREngine.cbrEngine.getStatusConcept() .getAllAttributeDescs();
	 */

	/**
	 * Darstellung des myCBR-Attributs, welches den aktuellen Munitionsstand
	 * darstellt.
	 */
	public static final IntegerDesc BRICKS = (IntegerDesc) attributeDescriptor.get("bricks");
	/**
	 * Darstellung des myCBR-Attributs, welches die aktuelle Munitionsreserve
	 * darstellt.
	 */
	public static final IntegerDesc WOOD = (IntegerDesc) attributeDescriptor
			.get("wood");
	/**
	 * Darstellung des myCBR-Attributs, welches die Distanz zur Munition
	 * darstellt.
	 */
	public static final IntegerDesc SHEEP = (IntegerDesc) attributeDescriptor
			.get("sheep");
	/**
	 * Darstellung des myCBR-Attributs, welches die Distanz zur Deckung
	 * darstellt.
	 */
	public static final IntegerDesc WHEAT = (IntegerDesc) attributeDescriptor.get("wheat");
	/**
	 * Darstellung des myCBR-Attributs, welches die Distanz zum Gegner
	 * darstellt.
	 */
	public static final IntegerDesc STONE = (IntegerDesc) attributeDescriptor.get("stone");
	/**
	 * Darstellung des myCBR-Attributs, welches die Distanz zur Gesundheit
	 * darstellt.
	 */
	//public static final SymbolDesc DISTANCE_TO_HEALTH_DESC = (SymbolDesc) attributeDescriptor.get("distanceToHealth");
	/**
	 * Darstellung des myCBR-Attributs, welches die Distanz zur Waffe darstellt.
	 */
	//public static final SymbolDesc DISTANCE_TO_WEAPON_DESC = (SymbolDesc) attributeDescriptor.get("distanceToWeapon");
	/**
	 * Darstellung des myCBR-Attributs, welches die aktuelle Waffe darstellt.
	 */
	//public static final SymbolDesc EQUIPPED_WEAPON_DESC = (SymbolDesc) attributeDescriptor.get("equippedWeapon");
	/**
	 * Darstellung des myCBR-Attributs, welches die Distanz zur letzten
	 * bekannten Position des Gegners darstellt.
	 */
	//public static final SymbolDesc LAST_POSITION_DESC = (SymbolDesc) attributeDescriptor.get("lastPosition");
	/**
	 * Darstellung des myCBR-Attributs, welches die eigene Gesundheit darstellt.
	 */
	//public static final SymbolDesc OWN_HEALTH_DESC = (SymbolDesc) attributeDescriptor.get("ownHealth");
	
	
	/**
	 * Darstellung des myCBR-Attributs, welches angibt, ob eine Stadt gebaut werden kann.
	 */
	public static final BooleanDesc IS_ABLED_TO_BUILD_CITY = (BooleanDesc) attributeDescriptor
			.get("isAbledToBuildCity");
	/**
	 * Darstellung des myCBR-Attributs, welches angibt, ob eine Siedlung gebaut werden kann.
	 */
	public static final BooleanDesc IS_ABLED_TO_BUILD_VILLAGE = (BooleanDesc) attributeDescriptor
			.get("isAbledToBuildVillage");
	/**
	 * Darstellung des myCBR-Attributs, welches angibt, ob eine Straﬂe gebaut werden kann.
	 */
	public static final BooleanDesc IS_ABLED_TO_BUILD_ROAD = (BooleanDesc) attributeDescriptor
			.get("isAbledToBuildRoad");
	/**
	 * Darstellung des myCBR-Attributs, welches angibt, ob es sich um den ersten Zug handelt.
	 */
	public static final BooleanDesc IS_FIRST_TURN = (BooleanDesc) attributeDescriptor
			.get("isFirstTurn");
	/**
	 * Darstellung des myCBR-Attributs, welches angibt, ob es sich um den zweiten Zug handelt. 
	 */
	public static final BooleanDesc IS_SECOND_TURN = (BooleanDesc) attributeDescriptor
			.get("isSecondTurn");
	/**
	 * Darstellung des myCBR-Attributs, welches angibt, ob der Spieler den Zug beenden kann. 
	 */
	public static final BooleanDesc IS_ABLED_TO_END_TURN = (BooleanDesc) attributeDescriptor
			.get("isAbledToEndTurn");
	/**
	 * Darstellung des myCBR-Attributs, welches angibt, ob der Spieler w¸rfeln darf. 
	 */
	public static final BooleanDesc IS_ALLOWED_TO_ROLL_DICE = (BooleanDesc) attributeDescriptor
			.get("isAllowedToRollDice");
	/**
	 * Darstellung des myCBR-Attributs, welches angibt, ob der Spieler die l‰ngste Straﬂe hat. 
	 */
	public static final BooleanDesc HAS_LONGEST_ROAD = (BooleanDesc) attributeDescriptor
			.get("hasLongestRoad");	
	/**
	 * Darstellung des myCBR-Attributs, welches angibt, ob der Spieler die l‰ngste Straﬂe hat. 
	 */
	public static final BooleanDesc CITY_PLACES_ACTIVE = (BooleanDesc) attributeDescriptor
			.get("cityPlacesActive");
	/**
	 * Darstellung des myCBR-Attributs, welches angibt, ob der Spieler die l‰ngste Straﬂe hat. 
	 */
	public static final BooleanDesc VILLAGE_PLACES_ACTIVE = (BooleanDesc) attributeDescriptor
			.get("villagePlacesActive");
	/**
	 * Darstellung des myCBR-Attributs, welches angibt, ob der Spieler die l‰ngste Straﬂe hat. 
	 */
	public static final BooleanDesc ROAD_PLACES_ACTIVE = (BooleanDesc) attributeDescriptor
			.get("roadPlacesActive");
	
	public static final BooleanDesc HAS_FREE_BUILD = (BooleanDesc) attributeDescriptor
			.get("hasFreeBuild");
	
	public static final BooleanDesc HAS_FREE_BUILD_ROAD = (BooleanDesc) attributeDescriptor
			.get("hasFreeBuildRoad");
	
	public static final BooleanDesc CITY_PLACES_AVAILABLE = (BooleanDesc) attributeDescriptor
			.get("cityPlacesAvailable");
	
	public static final BooleanDesc VILLAGE_PLACES_AVAILABLE = (BooleanDesc) attributeDescriptor
			.get("villagePlacesAvailable");
	
	public static final BooleanDesc ROAD_PLACES_AVAILABLE = (BooleanDesc) attributeDescriptor
			.get("roadPlacesAvailable");
	
	public static final StringDesc PREFERENCE_DESC = (StringDesc) attributeDescriptor
			.get("preference");
	

	//public static final SymbolDesc WINCHANCE_DESC = (SymbolDesc) attributeDescriptor.get("winChance");
	
	//public static final SymbolDesc KILLDEATHRATIO_DESC = (SymbolDesc) attributeDescriptor.get("killDeathRatio");
	
	//public static final SymbolDesc UPTIME_DESC = (SymbolDesc) attributeDescriptor.get("upTime");
	/**
	 * Darstellung des myCBR-Attributs, welches den Plan als String darstellt.
	 */
	public static final StringDesc PLAN_DESC = (StringDesc) attributeDescriptor.get("plan");

	//public static final IntegerDesc QUALITY_DESC = (IntegerDesc) attributeDescriptor.get("quality");
}
