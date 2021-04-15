package cbr_util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import de.dfki.mycbr.core.DefaultCaseBase;
import de.dfki.mycbr.core.ICaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.AttributeDesc;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.retrieval.Retrieval;
import de.dfki.mycbr.core.retrieval.Retrieval.RetrievalMethod;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;
import main.Player;
import model.CityBuildPlace;
import model.Request;
import model.Response;
import model.RoadBuildPlace;
import model.Status;
import model.VillageBuildPlace;
import model.plan.Action;
import model.plan.ActivateCityPlaces;
import model.plan.ActivateRoadPlaces;
import model.plan.ActivateVillagePlaces;
import model.plan.BuildCity;
import model.plan.BuildVillage;
import model.plan.BuildRoad;
import model.plan.EndTurn;
import model.plan.RollDice;
import model.plan.Plan;
import cbr_util.DefaultCases;

/**
 * Diese Klasse stellt die konkrete Verbindung zum myCBR-Projekt her und bietet
 * verschiedene Methoden, um den Retrieve- und Reviseprozess des CBR-Zyklus
 * abzudecken.
 *
 */
public class CBREngine {

	private static CBREngine cbrEngine;


	private static Project cbrProject = null;

	private ArrayList<DefaultCaseBase> caseBases;

	private ArrayList<Instance> caseSequence;
	
	private ArrayList<Status> tempStatus = new ArrayList<>();


	private Concept statusConcept;

	private static final String APPLICATION_NAME = "CBRS";

	private static final String PROJECT_NAME = "CBRS_Siedler.prj";

	private static final String CONCEPT_NAME = "Status";
	
	private static String previousPlan = "";
	

	private static int isAbledToBuildCity = 100;
	

	private static int isAbledToBuildVillage = 100;

	private static int isAbledToBuildRoad = 100;
	

	private static int cityPlacesAct = 100;
	

	private static int villagePlacesAct = 100;
	

	private static int roadPlacesAct = 100;
	

	private static int isAllowedToRollDice = 100;
	

	private static int isAbledToEndTurn = 100;
	

	private static int isFirstTurn = 100;

	private static int isSecondTurn = 100;
	
	private static int hasFreeBuild = 100;
	
	private static int hasFreeBuildRoad = 100;
	
	private static int bricks = 0;
	
	private static int wood = 0;
	
	private static int sheep = 0;
	
	private static int wheat = 0;
	
	private static int stone = 0;
	

	//JTextArea jtf;

	/**
	 * Einziger Konstruktor fuer die Klasse, der fuer das
	 * Singleton-Designpattern angepasst wurde und daher die Sichtbarkeit
	 * <tt>private</tt> besitzt. Innerhalb des Konstruktors wird die
	 * myCBR-Projektdatei und das sich darin befindene Statuskonzept eingelesen.
	 */
	private CBREngine(/*JTextArea jtf*/) {
		
		//this.jtf = jtf;

		try {

			caseBases = new ArrayList<>();

			/*File file = new File(PROJECT_NAME);
			jtf.setText(PROJECT_NAME);
			String data_path = file.getAbsolutePath();
			jtf.setText(jtf.getText() + file.getAbsolutePath());
			Project project = new Project(data_path);*/
			File f1 = new File(System.getProperty("user.dir"));
			//jtf.setText(jtf.getText() + f1);
			File f2 = new File(f1.getAbsolutePath());
			//jtf.setText(jtf.getText() + f2);
			File f3 = new File(f2.getPath(), APPLICATION_NAME);
			//jtf.setText(jtf.getText() + f3);
			String projektPfad = new File(f3.getPath(), PROJECT_NAME).getAbsolutePath();
			//jtf.setText(jtf.getText() + projektPfad);
			System.out.println(projektPfad);
			cbrProject = new Project(projektPfad).getProject();
			
			while (cbrProject.isImporting()) {
				Thread.sleep(200);
			}

		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
			//jtf.setText(jtf.getText() + "error: " + e.getMessage());
		}

		setStatusConcept(cbrProject.getConceptByID(CONCEPT_NAME));
		//jtf.setText("\n" + jtf.getText());
		

	}

	/**
	 * Statischer Getter fuer das Singleton-Designpattern. Falls die einzige
	 * Instanz der CBREngine <tt>null</tt> ist, wird der <tt>private</tt>
	 * Konstruktor aufgerufen und das so erzeuge Objekt anschlie&szlig;end
	 * mittels <tt>return</tt> zurueckgegeben..
	 * 
	 * @return Die einzige Instanz der Klasse <tt>{@link CBREngine}</tt>.
	 */
	public static CBREngine getInstance(/*JTextArea jtf*/) {
		if (cbrEngine == null) {
			cbrEngine = new CBREngine(/*jtf*/);
		}
		return cbrEngine;
	}

	/**
	 * Es folgen simple Getter und Setter fuer die Membervariablen.
	 */
	public Project getCbrProject() {
		return cbrProject;
	}

//	public void setCbrProject(Project cbrProject) {
//		this.cbrProject = cbrProject;
//	}

	public ArrayList<DefaultCaseBase> getCaseBases() {
		return caseBases;
	}

	public Concept getStatusConcept() {
		return statusConcept;
	}

	public void setStatusConcept(Concept statusConcept) {
		this.statusConcept = statusConcept;
	}

	/**
	 * Methode um zu ueberpruefen, ob fuer einen bestimmten Spieler
	 * bereits eine Fallbasis existiert.
	 * 
	 * @param name
	 *            Der Name des Spielers.
	 * @return <tt>True</tt>, falls bereits eine Fallbasis existiert,
	 *         <tt>false</tt>, falls nicht.
	 */
	public boolean caseBaseForPlayerAlreadyExists(String name) {
		return cbrProject.hasCB(name);
	}

	/**
	 * Diese Methode erstellt eine Fallbasis fuer einen gegebenen Namen (der
	 * Name des Spielers).
	 * 
	 * @param name
	 *            Der Name des Spielers, fuer den eine Fallbasis erstellt
	 *            werden soll.
	 * @throws Exception
	 *             Die Exception kann geworfen werden, falls keine Fallbasis
	 *             erstellt werden kann (falls das Projekt nicht korrekt geladen
	 *             wurde).
	 */
	public void createCaseBaseForPlayer(String name) throws Exception {
		caseBases.add(cbrProject.createDefaultCB(name));
	}

	/**
	 * Diese Methode ermittelt alle Faelle fuer einen Spieler und gibt
	 * diese als <tt>Collection</tt> zurueck.
	 * 
	 * @param name
	 *            Der Name des Spielers.
	 * @return Die Liste mit allen Faellen fuer den gegebenen Spieler.
	 */
	public Collection<Instance> getCasesForPlayer(String name) {
		return cbrProject.getCB(name).getCases();
	}

	/**
	 * Diese Methode ermittelt, ob die Fallbasis des gegebenen Spielers leer
	 * ist.
	 * 
	 * @param name
	 *            Der Name des Spielers.
	 * @return <tt>True</tt>, falls die Fallbasis leer ist, <tt>false</tt>,
	 *         falls nicht.
	 */
	public boolean isCaseBaseEmpty(String name) {
		return cbrProject.getCB(name).getCases().isEmpty();
	}

	/**
	 * Diese Methode erstellt aus einem uebergebenen Status und einem Plan
	 * einen neuen Fall und gibt diesen dann zurueck.
	 * 
	 * @param status
	 *            Der Status, in dem sich der Spieler befindet.
	 * @param plan
	 *            Der vorgeschlagene Plan.
	 * @param name
	 *            Der Name der Instanz.
	 * @return Die aus den Parametern erstellte Instanz.
	 * @throws Exception
	 *             Falls auf das Konzept nicht zugegriffen werden kann.
	 */
	private Instance createInstance(Status status, String plan, String name) throws Exception {
		Instance instance = statusConcept.addInstance(name);

		instance.addAttribute(RetrievalHelper.IS_ABLED_TO_BUILD_CITY, status.isAbledToBuildCity);
		instance.addAttribute(RetrievalHelper.IS_ABLED_TO_BUILD_VILLAGE, status.isAbledToBuildVillage);
		instance.addAttribute(RetrievalHelper.IS_ABLED_TO_BUILD_ROAD, status.isAbledToBuildVillage);
		instance.addAttribute(RetrievalHelper.IS_FIRST_TURN, status.isFirstTurn);
		instance.addAttribute(RetrievalHelper.IS_SECOND_TURN, status.isSecondTurn);
		instance.addAttribute(RetrievalHelper.IS_ALLOWED_TO_ROLL_DICE, status.allowedToRollDice);
		instance.addAttribute(RetrievalHelper.IS_ABLED_TO_END_TURN, status.isAbledToEndTurn);
		instance.addAttribute(RetrievalHelper.CITY_PLACES_ACTIVE, status.cityPlacesActive);
		instance.addAttribute(RetrievalHelper.VILLAGE_PLACES_ACTIVE, status.villagePlacesActive);
		instance.addAttribute(RetrievalHelper.ROAD_PLACES_ACTIVE, status.roadPlacesActive);
		/*instance.addAttribute(RetrievalHelper.BRICKS, status.bricks);
		instance.addAttribute(RetrievalHelper.WOOD, status.wood);
		instance.addAttribute(RetrievalHelper.SHEEP, status.sheep);
		instance.addAttribute(RetrievalHelper.WHEAT, status.wheat);
		instance.addAttribute(RetrievalHelper.STONE, status.stone);*/
		instance.addAttribute(RetrievalHelper.CITY_PLACES_AVAILABLE, status.cityPlacesAvailable);
		instance.addAttribute(RetrievalHelper.VILLAGE_PLACES_AVAILABLE, status.villagePlacesAvailable);
		instance.addAttribute(RetrievalHelper.ROAD_PLACES_AVAILABLE, status.roadPlacesAvailable);
		instance.addAttribute(RetrievalHelper.HAS_LONGEST_ROAD, status.hasLongestRoad);
		instance.addAttribute(RetrievalHelper.HAS_FREE_BUILD, status.freeBuild);
		instance.addAttribute(RetrievalHelper.HAS_FREE_BUILD_ROAD, status.freeBuildRoad);
		instance.addAttribute(RetrievalHelper.PREFERENCE_DESC, status.preference);
		//instance.addAttribute(RetrievalHelper.preference, plan);
		instance.addAttribute(RetrievalHelper.PLAN_DESC, plan);


		return instance;
	}
	
	/**
	 * Diese Methode fuegt die Start(Default)faelle der Fallbasis des
	 * Spielers hinzu.
	 * 
	 * @param name
	 *            Der Name des Spielers.
	 * @throws Exception
	 *             Falls auf das Projekt nicht zugegriffen werden kann.
	 */
	public void addDefaultCases(String name) throws Exception {
		//Collection<Instance> instances = cbrProject.getCB(name).getCases();
		Instance instance;
		Status status; 
		
		String plan;
		//int counter = instances.size();
		int counter = 0;

		//jtf.setText(jtf.getText() + "\nTest1");
		HashMap<Status, String> defaultCases = DefaultCases.getDefaultCases();
		//jtf.setText(jtf.getText() + "\nTest2");
		for (Map.Entry<Status, String> entry : defaultCases.entrySet()) {
			//jtf.setText(jtf.getText() + "\nTest3");
			status = entry.getKey();
			//jtf.setText(jtf.getText() + "\n" + status.toString2());
			
			plan = entry.getValue();
			//jtf.setText(jtf.getText() + "\n" + plan);

			instance = createInstance(status, plan, "s" + counter++);
			//jtf.setText(jtf.getText() + "\nInstanz erstellt");

			cbrProject.getCB(name).addCase(instance);
			//jtf.setText(jtf.getText() + "\nFall hinzugefügt");
		}
	}
	
	
	
	
	
// BIS HIER HIN HAENGT ALLES MIT DEM EINLESEN UND SPEICHERN VON FAELLEN ZUSAMMEN AB HIER GEHT ES UM DIE FALL/PLANMANIPULATION	
	
	
	
	
	/**
	 * Diese Methode befuellt eine gegebene Instanz mit den konkreten
	 * Werten, die aus einem gegebenen Status gezogen werden k&ouml;nnen.
	 * 
	 * @param instance
	 *            Die Instanz, die befuellt werden soll.
	 * @param status
	 *            Der Status, aus dem die konkreten Werte gezogen werden.
	 * @return Die nun befuellte Instanz.
	 * @throws ParseException 
	 */
	private Instance fillInstance(Instance instance, Status status, String player) throws ParseException {

		if (player.equals("Player1")) {
			status.calculatePreferencePlayer1();
		} else {
			status.calculatePreferencePlayer2();
		}
		//jtf.setText(jtf.getText() + "\n*******************Preference: " + status.getPreference());
		instance.addAttribute(RetrievalHelper.IS_ABLED_TO_BUILD_CITY, status.hasResourcesForCity());
		instance.addAttribute(RetrievalHelper.IS_ABLED_TO_BUILD_VILLAGE, status.hasResourcesForVillage());
		instance.addAttribute(RetrievalHelper.IS_ABLED_TO_BUILD_ROAD, status.hasResourcesForRoad());
		instance.addAttribute(RetrievalHelper.IS_FIRST_TURN, status.isFirstTurn);
		instance.addAttribute(RetrievalHelper.IS_SECOND_TURN, status.isSecondTurn);
		instance.addAttribute(RetrievalHelper.IS_ALLOWED_TO_ROLL_DICE, status.allowedToRollDice);
		instance.addAttribute(RetrievalHelper.IS_ABLED_TO_END_TURN, status.isAbledToEndTurn);
		instance.addAttribute(RetrievalHelper.CITY_PLACES_ACTIVE, status.getMap().cityPlacesActive());
		instance.addAttribute(RetrievalHelper.VILLAGE_PLACES_ACTIVE, status.getMap().villagePlacesActive());
		instance.addAttribute(RetrievalHelper.ROAD_PLACES_ACTIVE, status.getMap().roadPlacesActive());
		/*instance.addAttribute(RetrievalHelper.BRICKS, status.bricks);
		instance.addAttribute(RetrievalHelper.WOOD, status.wood);
		instance.addAttribute(RetrievalHelper.SHEEP, status.sheep);
		instance.addAttribute(RetrievalHelper.WHEAT, status.wheat);
		instance.addAttribute(RetrievalHelper.STONE, status.stone);*/
		instance.addAttribute(RetrievalHelper.CITY_PLACES_AVAILABLE, status.cityBuildPlacesAvailable());
		instance.addAttribute(RetrievalHelper.VILLAGE_PLACES_AVAILABLE, status.villagePlacesAvailable);
		instance.addAttribute(RetrievalHelper.ROAD_PLACES_AVAILABLE, status.roadPlacesAvailable);
		instance.addAttribute(RetrievalHelper.HAS_LONGEST_ROAD, status.hasLongestRoad);
		instance.addAttribute(RetrievalHelper.HAS_FREE_BUILD, status.freeBuild);
		instance.addAttribute(RetrievalHelper.HAS_FREE_BUILD_ROAD, status.freeBuildRoad);
		instance.addAttribute(RetrievalHelper.PREFERENCE_DESC, status.preference);

		
		//updateAttributeWeight(status);
		//tempStati.add(status);

		//temporary disabled
		/*setWeightForAttr(instance, RetrievalHelper.IS_COVERED_DESC, isCoveredAttrW);
		setWeightForAttr(instance, RetrievalHelper.PLAN_DESC, planAttrW);
		setWeightForAttr(instance, RetrievalHelper.QUALITY_DESC, qualityAttrW);*/
		
		//Alle Werte werden hier initial gesetzt.
		/*setWeightForAttr(instance, RetrievalHelper.IS_ABLED_TO_BUILD_CITY, isAbledToBuildCity); 
		setWeightForAttr(instance, RetrievalHelper.IS_ABLED_TO_BUILD_VILLAGE, isAbledToBuildVillage);
		setWeightForAttr(instance, RetrievalHelper.IS_ABLED_TO_BUILD_ROAD, isAbledToBuildRoad);
		setWeightForAttr(instance, RetrievalHelper.IS_FIRST_TURN, isFirstTurn);
		setWeightForAttr(instance, RetrievalHelper.IS_SECOND_TURN, isSecondTurn);
		setWeightForAttr(instance, RetrievalHelper.IS_ALLOWED_TO_ROLL_DICE, isAllowedToRollDice);
		setWeightForAttr(instance, RetrievalHelper.IS_ABLED_TO_END_TURN, isAbledToEndTurn);
		//Hier wäre iscovered bool, mal gucken ob das geht
		setWeightForAttr(instance, RetrievalHelper.CITY_PLACES_ACTIVE, cityPlacesAct);
		setWeightForAttr(instance, RetrievalHelper.VILLAGE_PLACES_ACTIVE, villagePlacesAct);
		setWeightForAttr(instance, RetrievalHelper.ROAD_PLACES_ACTIVE, roadPlacesAct);
		//setWeightForAttr(instance, RetrievalHelper.HAS_LONGEST_ROAD, hasLongestRoad);
		setWeightForAttr(instance, RetrievalHelper.HAS_FREE_BUILD, hasFreeBuild);
		setWeightForAttr(instance, RetrievalHelper.HAS_FREE_BUILD_ROAD, hasFreeBuildRoad);
		/*setWeightForAttr(instance, RetrievalHelper.BRICKS, bricks);
		setWeightForAttr(instance, RetrievalHelper.WOOD, wood);
		setWeightForAttr(instance, RetrievalHelper.SHEEP, sheep);
		setWeightForAttr(instance, RetrievalHelper.WHEAT, wheat);
		setWeightForAttr(instance, RetrievalHelper.STONE, stone);*/
		
		/*jtf.setText(jtf.getText() + "\n*************************VERGLEICHSFALL**************************************");
		jtf.setText(jtf.getText() + "\nCityPlacesActive: " + instance.getAttForDesc(RetrievalHelper.CITY_PLACES_ACTIVE).getValueAsString());
		jtf.setText(jtf.getText() + "\nVillagePlacesActive: " + instance.getAttForDesc(RetrievalHelper.VILLAGE_PLACES_ACTIVE).getValueAsString());
		jtf.setText(jtf.getText() + "\nRoadPlacesActive: " + instance.getAttForDesc(RetrievalHelper.ROAD_PLACES_ACTIVE).getValueAsString());
		jtf.setText(jtf.getText() + "\nFirst Tur: " + instance.getAttForDesc(RetrievalHelper.IS_FIRST_TURN).getValueAsString());
		jtf.setText(jtf.getText() + "\nSecond Turn: " + instance.getAttForDesc(RetrievalHelper.IS_SECOND_TURN).getValueAsString());
		jtf.setText(jtf.getText() + "\nFree Build: " + instance.getAttForDesc(RetrievalHelper.HAS_FREE_BUILD).getValueAsString());
		jtf.setText(jtf.getText() + "\nFree Build road: " + instance.getAttForDesc(RetrievalHelper.HAS_FREE_BUILD_ROAD).getValueAsString());
		jtf.setText(jtf.getText() + "\nhas longest Road: " + instance.getAttForDesc(RetrievalHelper.HAS_LONGEST_ROAD).getValueAsString());
		jtf.setText(jtf.getText() + "\nabled to build city: " + instance.getAttForDesc(RetrievalHelper.IS_ABLED_TO_BUILD_CITY).getValueAsString());
		jtf.setText(jtf.getText() + "\nabled to build village: " + instance.getAttForDesc(RetrievalHelper.IS_ABLED_TO_BUILD_VILLAGE).getValueAsString());
		jtf.setText(jtf.getText() + "\nabled to build road: " + instance.getAttForDesc(RetrievalHelper.IS_ABLED_TO_BUILD_ROAD).getValueAsString());
		jtf.setText(jtf.getText() + "\nabled to end Turn: " + instance.getAttForDesc(RetrievalHelper.IS_ABLED_TO_END_TURN).getValueAsString());
		jtf.setText(jtf.getText() + "\nallowed to roll dice: " + instance.getAttForDesc(RetrievalHelper.IS_ALLOWED_TO_ROLL_DICE).getValueAsString());
		/*jtf.setText(jtf.getText() + "\nbricks" + instance.getAttForDesc(RetrievalHelper.BRICKS).getValueAsString());
		jtf.setText(jtf.getText() + "\nwood" + instance.getAttForDesc(RetrievalHelper.WOOD).getValueAsString());
		jtf.setText(jtf.getText() + "\nsheep" + instance.getAttForDesc(RetrievalHelper.SHEEP).getValueAsString());
		jtf.setText(jtf.getText() + "\nwheat" + instance.getAttForDesc(RetrievalHelper.WHEAT).getValueAsString());
		jtf.setText(jtf.getText() + "\nstone" + instance.getAttForDesc(RetrievalHelper.STONE).getValueAsString());*/
		//jtf.setText(jtf.getText() + "\nPreference: " + instance.getAttForDesc(RetrievalHelper.PREFERENCE_DESC).getValueAsString());
		
		flush();
		
		return instance;
		
	}
	
	
	
	/**
	 * Diese Methode ermittelt mit dem Übergebenen Status die notwendige Gewichtugn der Attribute.
	 * @param status
	 */
	public void updateAttributeWeight(Status status) {
		
		if (status.isFirstTurn) {
			isFirstTurn = 1000;
			isSecondTurn = 0;
			hasFreeBuild = 900;
			hasFreeBuildRoad = 900;
			villagePlacesAct = 800;
			roadPlacesAct = 800;
			
		} else if (status.isSecondTurn) {
			isSecondTurn = 1000;
			isFirstTurn = 0;
			hasFreeBuild = 900;
			hasFreeBuildRoad = 900;
			villagePlacesAct = 800;
			roadPlacesAct = 800;
			
		} else if(status.allowedToRollDice) {
			
			isAllowedToRollDice = 1000;
			
		} else if(!status.allowedToRollDice) { 
			
			flush();
			if (status.hasResourcesForCity()) {
				if (!status.getMap().cityPlacesActive()) {
					isAbledToBuildCity = 100;
					cityPlacesAct = 1000;
					
				} else if (status.getMap().cityPlacesActive()) {
					isAbledToBuildCity = 1000;
				}

			} else if (status.hasResourcesForVillage()) {
				if (!status.getMap().villagePlacesActive()) {
					isAbledToBuildVillage = 100;
					villagePlacesAct = 1000;
					
				} else if (status.getMap().villagePlacesActive()) {
					isAbledToBuildVillage = 1000;
				}

			} else if (status.hasResourcesForRoad()) {
				if (!status.getMap().roadPlacesActive()) {
					isAbledToBuildRoad = 100;
					roadPlacesAct = 1000;
					
				} else if (status.getMap().roadPlacesActive()) {
					isAbledToBuildRoad = 1000;
					roadPlacesAct = 100;
				}
				
			} else {
				isAbledToEndTurn = 1000;
				isAbledToBuildRoad = 0;
				roadPlacesAct = 0;
				isAbledToBuildVillage = 0;
				isAbledToBuildRoad = 0;
				isAbledToBuildVillage = 0;
				villagePlacesAct = 0;
				isAbledToBuildCity = 0;
				cityPlacesAct = 0;
			} 
		}
	}
	
	public void flush() {
		
		isAbledToEndTurn = 0;
		isAbledToBuildRoad = 0;
		roadPlacesAct = 0;
		isAbledToBuildVillage = 0;
		isAbledToBuildRoad = 0;
		isAbledToBuildVillage = 0;
		villagePlacesAct = 0;
		isAbledToBuildCity = 0;
		cityPlacesAct = 0;
		hasFreeBuild = 0;
		hasFreeBuildRoad = 0;
		/*bricks = 0;
		wood = 0;
		sheep = 0;
		wheat = 0;
		stone = 0;*/
		
	}
	

	/**
	 * Diese Methode veraendert das Gewicht eines Attributs einer Instanz um
	 * den uebergebenen Wert.
	 * 
	 * @param instance
	 *            Die Instanz, dessen Attribut veraendert werden soll.
	 * @param attr
	 *            Das Attribut, was veraendert werden soll.
	 * @param weight
	 *            Die neue Gewichtung des Attributs.
	 */
	private void setWeightForAttr(Instance instance, AttributeDesc attr, int weight) {

		for (AttributeDesc desc : instance.getAttributes().keySet()) {
			if (desc.getName().contains(attr.getName())) {
				System.out.println("sett attr weight for: " + desc.getName() + " to: " + weight);
				statusConcept.getActiveAmalgamFct().setWeight(desc, weight);
				if (weight == 0) {
					statusConcept.getActiveAmalgamFct().setActive(desc, false);
				}
				break;
			}
		}
	}

	/**
	 * Diese Methode fuehrt anhand einer gegebenen Anfrage ein Retrieval
	 * aus, fuehrt den Reviseprozess aus und gibt den vorgeschlagenen Plan
	 * in Form eines <tt>{@link Response}</tt> Objekts zurueck.
	 * 
	 * @param request
	 *            Die gegebene Anfrage.
	 * @return Der vorgeschlagene Plan in Form eines Response-Objekts.
	 * @throws ParseException 
	 */
	public Response executeRetrieval(Request request) throws ParseException {
		Response response = new Response();
		response.getSituation().setPlayer(request.getSituation().getPlayer());
		response.getSituation().setPlayerStatus(new Status());

		String cbName = request.getSituation().getPlayer();

		Retrieval retrieval = new Retrieval(statusConcept, cbrProject.getCB(cbName));
		retrieval.setRetrievalMethod(RetrievalMethod.RETRIEVE_SORTED);

		Instance query = retrieval.getQueryInstance();

		query = fillInstance(query, request.getSituation().getPlayerStatus(), request.getSituation().getPlayer());
		//tempStatus.add(request.getSituation().getPlayerStatus());

		retrieval.start();
		List<Pair<Instance, Similarity>> resultate = retrieval.getResult();
		
		//jtf.setText(jtf.getText() + "\n" + resultate.size());
		/*for (int i = 0; i < 5;i++) {
			jtf.setText(jtf.getText() + "\n*************************Fall: " + i + "***************************");
			jtf.setText(jtf.getText() + "\n" + resultate.get(i).getSecond());
			jtf.setText(jtf.getText() + "\nCityPlacesActive: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.CITY_PLACES_ACTIVE).getValueAsString());
			jtf.setText(jtf.getText() + "\nVillagePlacesActive: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.VILLAGE_PLACES_ACTIVE).getValueAsString());
			jtf.setText(jtf.getText() + "\nRoadPlacesActive: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.ROAD_PLACES_ACTIVE).getValueAsString());
			jtf.setText(jtf.getText() + "\nIsFirstTurn: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.IS_FIRST_TURN).getValueAsString());
			jtf.setText(jtf.getText() + "\nIsSecondTurn: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.IS_SECOND_TURN).getValueAsString());
			jtf.setText(jtf.getText() + "\nfreeBuild: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.HAS_FREE_BUILD).getValueAsString());
			jtf.setText(jtf.getText() + "\nFreeBuildRoad: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.HAS_FREE_BUILD_ROAD).getValueAsString());
			jtf.setText(jtf.getText() + "\nHas Longest Road: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.HAS_LONGEST_ROAD).getValueAsString());
			jtf.setText(jtf.getText() + "\nabled to build city: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.IS_ABLED_TO_BUILD_CITY).getValueAsString());
			jtf.setText(jtf.getText() + "\nabled to build village: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.IS_ABLED_TO_BUILD_VILLAGE).getValueAsString());
			jtf.setText(jtf.getText() + "\nabled to build road: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.IS_ABLED_TO_BUILD_ROAD).getValueAsString());
			jtf.setText(jtf.getText() + "\nabled to end turn: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.IS_ABLED_TO_END_TURN).getValueAsString());
			jtf.setText(jtf.getText() + "\nallowed to roll dice: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.IS_ALLOWED_TO_ROLL_DICE).getValueAsString());
			/*jtf.setText(jtf.getText() + "\nbricks: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.BRICKS).getValueAsString());
			jtf.setText(jtf.getText() + "\nwood: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.WOOD).getValueAsString());
			jtf.setText(jtf.getText() + "\nsheep: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.SHEEP).getValueAsString());
			jtf.setText(jtf.getText() + "\nwheat: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.WHEAT).getValueAsString());
			jtf.setText(jtf.getText() + "\nstone: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.STONE).getValueAsString());*/
			/*jtf.setText(jtf.getText() + "\nPreference: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.PREFERENCE_DESC).getValueAsString());
			jtf.setText(jtf.getText() + "\nPlan: " + resultate.get(i).getFirst().getAttForDesc(RetrievalHelper.PLAN_DESC).getValueAsString());
		}*/

		int counter = 0;
		boolean found = false;
		Instance currentInstance;
		Plan plan = new Plan();
		
		Instance handleInstance = null;
		

		while (counter < resultate.size() && !found) {
		
			System.out.println("counter: " + counter);
			
			currentInstance = resultate.get(counter).getFirst();

			response.getSituation().getPlayerStatus().cityPlacesActive = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.CITY_PLACES_ACTIVE).getValueAsString());

			response.getSituation().getPlayerStatus().villagePlacesActive = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.VILLAGE_PLACES_ACTIVE).getValueAsString());

			response.getSituation().getPlayerStatus().roadPlacesActive = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.ROAD_PLACES_ACTIVE).getValueAsString());

			response.getSituation().getPlayerStatus().isFirstTurn = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.IS_FIRST_TURN).getValueAsString());

			response.getSituation().getPlayerStatus().isSecondTurn = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.IS_SECOND_TURN).getValueAsString());

			response.getSituation().getPlayerStatus().allowedToRollDice = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.IS_ALLOWED_TO_ROLL_DICE).getValueAsString());

			response.getSituation().getPlayerStatus().isAbledToEndTurn = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.IS_ABLED_TO_END_TURN).getValueAsString());
			
			response.getSituation().getPlayerStatus().freeBuild = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.HAS_FREE_BUILD).getValueAsString());
			
			response.getSituation().getPlayerStatus().freeBuildRoad = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.HAS_FREE_BUILD_ROAD).getValueAsString());

			response.getSituation().getPlayerStatus().hasLongestRoad = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.HAS_LONGEST_ROAD).getValueAsString());
			
			response.getSituation().getPlayerStatus().isAbledToBuildCity = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.IS_ABLED_TO_BUILD_CITY).getValueAsString());
			
			response.getSituation().getPlayerStatus().isAbledToBuildVillage = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.IS_ABLED_TO_BUILD_VILLAGE).getValueAsString());
			
			response.getSituation().getPlayerStatus().isAbledToBuildRoad = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.IS_ABLED_TO_BUILD_ROAD).getValueAsString());
			
			response.getSituation().getPlayerStatus().cityPlacesAvailable = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.CITY_PLACES_AVAILABLE).getValueAsString());
			
			response.getSituation().getPlayerStatus().villagePlacesAvailable = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.VILLAGE_PLACES_AVAILABLE).getValueAsString());
			
			response.getSituation().getPlayerStatus().roadPlacesAvailable = Boolean.parseBoolean(currentInstance.getAttForDesc(RetrievalHelper.ROAD_PLACES_AVAILABLE).getValueAsString());
			
			response.getSituation().getPlayerStatus().preference = (currentInstance.getAttForDesc(RetrievalHelper.PREFERENCE_DESC).getValueAsString());

			//response.getSituation().getPlayerStatus().bricks = (Integer.parseInt(currentInstance.getAttForDesc(RetrievalHelper.BRICKS).getValueAsString()));

			//response.getSituation().getPlayerStatus().wood = (Integer.parseInt(currentInstance.getAttForDesc(RetrievalHelper.WOOD).getValueAsString()));

			//response.getSituation().getPlayerStatus().sheep = (Integer.parseInt(currentInstance.getAttForDesc(RetrievalHelper.SHEEP).getValueAsString()));

			//response.getSituation().getPlayerStatus().wheat = (Integer.parseInt(currentInstance.getAttForDesc(RetrievalHelper.WHEAT).getValueAsString()));

			//response.getSituation().getPlayerStatus().stone = (Integer.parseInt(currentInstance.getAttForDesc(RetrievalHelper.STONE).getValueAsString()));

			plan.setActionsAsString(currentInstance.getAttForDesc(RetrievalHelper.PLAN_DESC).getValueAsString());
			//jtf.setText(jtf.getText() + "\n" + plan.getActionsAsString());
			//jtf.setText(jtf.getText() + "\nRausgekommen: " + response.getSituation().getPlayerStatus().preference);
			
			
			if (planExecutable(plan, request.getSituation().getPlayerStatus())) {
				
				if (previousPlan.equals("ActivateVillagePlaces;") && previousPlan.equals(plan.getActionsAsString())) {
					plan.setActionsAsString("ActivateRoadPlaces;");
				} else if (previousPlan.equals("ActivateRoadPlaces;") && plan.getActionsAsString().equals("ActivateRoadPlaces;")) {
					plan.setActionsAsString("BuildRoad;ActivateRoadPlaces;");
				} else if (previousPlan.equals("ActivateCityPlaces;") && previousPlan.equals(plan.getActionsAsString())) {
					plan.setActionsAsString("BuildCity;ActivateCityPlaces;");
				}
				
				if (!planExecutable(plan, request.getSituation().getPlayerStatus())) {
					plan.setActionsAsString(currentInstance.getAttForDesc(RetrievalHelper.PLAN_DESC).getValueAsString());
				} 
				response.setPlan(plan);
				found = true;
			} else {
				//jtf.setText(jtf.getText() + "Following plan was not permitted: " + plan.getActionsAsString());
			}
			counter++;
			handleInstance = currentInstance;	
			
		}
		if (!found) {
			if(request.getSituation().getPlayerStatus().allowedToRollDice) {
				plan.setActionsAsString("RollDice;");
			} else {
				plan.setActionsAsString("EndTurn;");
			}
			response.setPlan(plan);
			//System.out.println("Could not find a good case... just do something!");
		}
		previousPlan = response.getPlan().getActionsAsString();

		//updateCasebase(handleInstance, request);
		
//		/*
//		 * Dieser Abschnitt ist für das Hinzufügen des verwendeten Falls 
//		 * in die Fallbasis verantwortlich. Da momentan keine Retain-Phase
//		 * implementiert ist, wird auf diesen Schritt voerst verzeichtet.	
//		 * 
//		 * ram zu cbr Aender, dann wie vorher	
//		 */
//		try {
//			Instance newCase = createInstance(request.getSituation().getPlayerStatus(), plan.getActionsAsString(),
//					"s" + ramProject.getCB(request.getSituation().getPlayer()).getCases().size());
//			
//			ramProject.getCB(request.getSituation().getPlayer()).addCase(newCase);
//			
//		} catch (Exception exc) {
//			exc.printStackTrace();
//		}		

		return response;
	}
	

	/**
	 * Diese Methode gibt die Fallbasis eines Spielers zurueck.
	 * 
	 * @param player
	 *            Der Name des Spielers und der gesuchten Fallbasis.
	 * @return Die gefundene Fallbasis oder <tt>null</tt>, falls fuer den
	 *         Spieler keine Fallbasis existiert.
	 */
	
	public ICaseBase getCaseBaseForPlayer(String player) {
		ICaseBase cb = null;

		if (cbrProject.hasCB(player)) {
			cb = cbrProject.getCB(player);
		}

		return cb;
	}

	/**
	 * Diese Methode ermittelt, ob ein vorgeschlagener Plan mit dem gegebenen
	 * Status vereinbar und durchfuehrbar ist.
	 */
	private boolean planExecutable(Plan plan, Status status) {

		List<Action> actions = extractActionsFromStatus(plan.getActionsAsString());
		Action act = null;

		for (Action action : actions) {
			
			//Collect Health Ablehnung, nur wenn distanz unbekannt, ergo nicht gespawned
			if (action instanceof ActivateCityPlaces) {
				if (!(status.hasResourcesForCity() || status.getMap().cityPlacesActive()) || status.isSecondTurn || status.isFirstTurn || status.allowedToRollDice) {
					return false;
				}
			} else if (action instanceof ActivateVillagePlaces) {
				/*jtf.setText(jtf.getText() + "\n*****************************************ActivateVillagePlaces***********************************************"
						+ "\nstatus.freeBuild: " + status.freeBuild
						+ "\nstatus.isFirstTurn" + status.isFirstTurn
						+ "\nstatus.isSecondTurn: " + status.isSecondTurn
						+ "\nstatus.freeBuild && (status.isFirstTurn || status.isSecondTurn): " + (status.freeBuild && (status.isFirstTurn || status.isSecondTurn))
						+ "\nstatus.hasResourcesForVillage(): " + status.hasResourcesForVillage()
						+ "\n(status.hasResourcesForVillage() && !(status.isFirstTurn || status.isSecondTurn)): " + (status.hasResourcesForVillage() && !(status.isFirstTurn || status.isSecondTurn))
						+ "\nstatus.getMap().villagePlacesActive()): " + status.getMap().villagePlacesActive()
						+ "\nstatus.allowedToRollDice: " + status.allowedToRollDice
						+ "\n!((status.freeBuild && (status.isFirstTurn || status.isSecondTurn)) || (status.hasResourcesForVillage() && !(status.isFirstTurn || status.isSecondTurn)) || status.getMap().villagePlacesActive()) || status.allowedToRollDice: " 
						+ (!((status.freeBuild && (status.isFirstTurn || status.isSecondTurn)) || (status.hasResourcesForVillage() && !(status.isFirstTurn || status.isSecondTurn)) || status.getMap().villagePlacesActive()) || status.allowedToRollDice));*/
				if (!((status.freeBuild && (status.isFirstTurn || status.isSecondTurn)) || (status.hasResourcesForVillage() && !(status.isFirstTurn || status.isSecondTurn)) || status.getMap().villagePlacesActive()) || status.allowedToRollDice) {
					return false;
				}
			} else if (action instanceof ActivateRoadPlaces) {
				if (!(status.freeBuildRoad || (status.hasResourcesForRoad() && !(status.isFirstTurn || status.isSecondTurn)) || status.getMap().roadPlacesActive()) || status.allowedToRollDice) {
					return false;
				}
			} else if (action instanceof BuildCity) {
				act = (BuildCity) action;
				if (!(status.getMap().cityPlacesActive() && status.hasResourcesForCity()) || status.isSecondTurn || status.isFirstTurn || status.allowedToRollDice) {
					return false;
				} else {
					CityBuildPlace place = status.getMap().getRandomCityPlace();
					plan.setActionsAsString(plan.getActionsAsString().replace("BuildCity;", "BuildCity:" + place.row + ":" + place.column + ";"));
				}
			} else if (action instanceof BuildVillage) {
				act = (BuildVillage) action;
				/*jtf.setText(jtf.getText() + "\n************************************BuildVillage**********************************************"
						+ "\nstatus.freeBuild: " + status.freeBuild
						+ "\nstatus.isFirstTurn" + status.isFirstTurn
						+ "\nstatus.isSecondTurn: " + status.isSecondTurn
						+ "\n((status.hasResourcesForVillage() && !(status.isFirstTurn || status.isSecondTurn)) || status.freeBuild): " + ((status.hasResourcesForVillage() && !(status.isFirstTurn || status.isSecondTurn)) || status.freeBuild)
						+ "\nstatus.hasResourcesForVillage(): " + status.hasResourcesForVillage()
						+ "\n(status.hasResourcesForVillage() && !(status.isFirstTurn || status.isSecondTurn)): " + (status.hasResourcesForVillage() && !(status.isFirstTurn || status.isSecondTurn))
						+ "\nstatus.getMap().villagePlacesActive()): " + status.getMap().villagePlacesActive()
						+ "\nstatus.allowedToRollDice: " + status.allowedToRollDice
						+ "\n(!(status.getMap().villagePlacesActive() && ((status.hasResourcesForVillage() && !(status.isFirstTurn || status.isSecondTurn)) || status.freeBuild)) || status.allowedToRollDice) : " 
						+ (!(status.getMap().villagePlacesActive() && ((status.hasResourcesForVillage() && !(status.isFirstTurn || status.isSecondTurn)) || status.freeBuild)) || status.allowedToRollDice));*/
				if (!(status.getMap().villagePlacesActive() && ((status.hasResourcesForVillage() && !(status.isFirstTurn || status.isSecondTurn)) || status.freeBuild)) || status.allowedToRollDice) {
					return false;
				} else {
					VillageBuildPlace place = status.getMap().getVillagePlaceByPreference(status.preference);
					plan.setActionsAsString(plan.getActionsAsString().replace("BuildVillage;", "BuildVillage:" + place.row + ":" + place.column + ";"));
				}
			} else if (action instanceof BuildRoad) {
				act = (BuildRoad) action;
				if (!(status.getMap().roadPlacesActive() && ((status.hasResourcesForRoad() && !(status.isFirstTurn || status.isSecondTurn)) || status.freeBuildRoad)) || status.allowedToRollDice) {
					return false;
				} else {
					RoadBuildPlace place = status.getMap().getRandomRoadPlace();
					plan.setActionsAsString(plan.getActionsAsString().replace("BuildRoad;", "BuildRoad:" + place.row + ":" + place.column + ";"));
				}
			} else if (action instanceof EndTurn) {
				act = (EndTurn) action;
				if (!(status.isAbledToEndTurn)) {
					return false;
				} 
			} else if (action instanceof RollDice) {
				act = (RollDice) action;
				if (!(status.allowedToRollDice)) {
					return false;
				} 
			}
		}
		//jtf.setText(jtf.getText() + "\nTRUUUUUUUUUUUUUUUUUUUUUUUUUUUEEEEEEEEEEEEEEEEEEEE");
		return true;
	}



	/**
	 * Aktionen auf Grundlage eines Strings erzeugen.
	 *
	 */
	private ArrayList<Action> extractActionsFromStatus(String str) {

		String[] strings = str.split(";");

		ArrayList<Action> actions = new ArrayList<>();

		for (String action : strings) {
			if (action != "") {
				if (action.contains("ActivateVillagePlaces"))
                {
                    actions.add(new ActivateVillagePlaces());
                }

                if (action.contains("BuildVillage"))
                {
                    actions.add(new BuildVillage());
                }

                if (action.contains("ActivateCityPlaces"))
                {
                	actions.add(new ActivateCityPlaces());
                }

                if (action.contains("BuildCity"))
                {
                    actions.add(new BuildCity());
                }

                if (action.contains("ActivateRoadPlaces"))
                {
                	actions.add(new ActivateRoadPlaces());
                }

                if (action.contains("BuildRoad"))
                {
                    actions.add(new BuildRoad());
                }

                if (action.contains("EndTurn"))
                {
                	actions.add(new EndTurn());
                }
                if (action.contains("RollDice"))
                {
                	actions.add(new RollDice());
                }
			}
		}
		return actions;
	}	
	

	 /*
	public void updateCasebase(Instance handleInstance, Request request) {
		
		if(handleInstance != null) {	
			double deathcheck = 0.0; 
			double currentUpTime = request.getSituation().getPlayerStatus().getUpTime();
			double winChance = request.getSituation().getPlayerStatus().getWinChance();
			
			if(caseSequence == null) {
				caseSequence = new ArrayList<Instance>();	
			}

			if(currentUpTime != 0.0) {
				
				deathcheck = currentUpTime;  
				newUpTime = currentUpTime;
				caseSequence.add(handleInstance);
				
				
				caseBaseLog("Sequencenumber: " + sequencecounter + " : "  + caseSequence.toString());
				caseBaseLog("Fall " + casecounter + " :" + handleInstance.getAttributes().toString());

				if(oldUpTime > newUpTime) {
					sequenceUpTime = oldUpTime;
					caseBaseLog("Regular KI killed the CBR KI at: " + deathcheck);
					sequencecounter++;
					
					if(winChance < 10.0) {
						for(int i = 0; i < caseSequence.size(); i++) {
							if(cbrProject.getCB(request.getSituation().getPlayer()).getCases().size() > 50) {
								Status status = tempStati.get(i);
								Instance currentIni = caseSequence.get(i);	
								Plan copyplan = new Plan();
								copyplan.setActionsAsString(currentIni.getAttForDesc(RetrievalHelper.PLAN_DESC).getValueAsString());
								removeCasesFromSequence(i, caseSequence);

									try {
									Instance gc = createInstance(status, copyplan.getActionsAsString(),
											caseSequence.get(i).toString());	
									cbrProject.getCB(request.getSituation().getPlayer()).removeCase(gc.toString());
									
									caseBaseLog("Removed from CB: " + gc.toString() + ", it was: " + handleInstance.getAttributes().toString());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
					
					caseSequence.clear();
					tempStati.clear();
					caseBaseLog("Casebase Size after Removing: " + cbrProject.getCB(request.getSituation().getPlayer()).getCases().size());	
				}
				
				
				if(request.getSituation().toString().contains("isEnemyAlive=false")) {

					caseBaseLog("CBR KI killed the regular KI at : " + newUpTime);
					
					for(int i = 0; i < caseSequence.size(); i++) {
						
						Status status = tempStati.get(i);
						Plan copyplan = new Plan();
						Instance currentIni = caseSequence.get(i);						
						copyplan.setActionsAsString(currentIni.getAttForDesc(RetrievalHelper.PLAN_DESC).getValueAsString());
						removeCasesFromSequence(i, caseSequence);

						try {
							Instance goodCase = createInstance(status, copyplan.getActionsAsString(),
									"s" + (cbrProject.getCB(request.getSituation().getPlayer()).getCases().size() + minCBSize));	
							cbrProject.getCB(request.getSituation().getPlayer()).addCase(goodCase);
							minCBSize++;
						} catch (Exception e) {
							e.printStackTrace();
						}						
					}

					if(sequenceUpTime * 1.5 < newUpTime) {	
						
						caseBaseLog("Improved UpTime about : " + (newUpTime - sequenceUpTime) + " seconds!");	
					}	
					caseBaseLog("Casebase Size after Adding: " + cbrProject.getCB(request.getSituation().getPlayer()).getCases().size());
				}
				oldUpTime = currentUpTime;
				casecounter++;
				
			}
		}
	}

	/*
	public void removeCasesFromSequence(int i, ArrayList<Instance> caseSequence) {
		
		for(int i2 = i+1; i2 < caseSequence.size(); i2++) {
			
			if(caseSequence.get(i).toString().contains("MoveTo;Shoot") == caseSequence.get(i2).toString().contains("MoveTo;Shoot")) {
				
				caseSequence.remove(i2);
				
			} else if(caseSequence.get(i).toString().contains("Reload")) {
				
				caseSequence.remove(i);
				
			} else if(caseSequence.get(i).toString().contains("UseCover;CollectItem<health>") == caseSequence.get(i2).toString().contains("UseCover;CollectItem<health>")) {
				
				caseSequence.remove(i2);
				
			} else if(caseSequence.get(i2).toString().contains("CollectItem<ammunition>")) {
				
				caseSequence.remove(i2);
				
			} else if(caseSequence.get(i).toString().contains("CollectItem<health>") == caseSequence.get(i2).toString().contains("CollectItem<health>")) {
				
				caseSequence.remove(i2);
				
			} else if(caseSequence.get(i).toString().contains("CollectItem<weapon>;SwitchWeapon") == caseSequence.get(i2).toString().contains("CollectItem<weapon>;SwitchWeapon")) {
				
				caseSequence.remove(i2);
				
			} 
								
		
		}
	}
	public static void caseBaseLog(String text) {
		
		
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(System.getProperty("user.dir"), "caseBaseLog.txt"), true);
			fw.write(text);
			fw.write("\r\n");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				fw.flush();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
}