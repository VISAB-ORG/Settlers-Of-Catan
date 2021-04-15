using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using System;
using System.Diagnostics;
using Assets.Scripts.Connection;
using Assets.Scripts.Utility;
using Assets.Scripts.CBR.Model;
using Assets.Scripts.Model;
using Assets.Scripts.CBR.Plan;
using System.Threading;
using System.Diagnostics.Tracing;

/**
 * Klasse, zum Steuern des Spielflusses. 
 * Die ist sowohl für das Spiel mit menschlichen als auch für computergesteuerte Spieler zuständig
 */
public class GameManager : MonoBehaviour
{
    public MapGeneratorScript mapGenerator;

    public PlayerScript player1, player2;
    public PlayerScript activePlayer;
    public Camera cameraView;

    public Text gameOverText;

    public GameObject villagePlaces;
    public VillageFocus villageFocus;

    public GameObject cityPlace;
    public List<GameObject> cityPlaceInstances;
    public CityFocus cityFocus;

    public GameObject roadPlaces;
    public RoadFocus roadFocus;

    public GameObject buildVillage, buildRoad, buildCity;

    public GameObject spawnPoint = null;

    public Button rollDiceBtn, endTurnBtn, newGameBtn;

    public float villageRange;
    public float roadRange;

    public Map map;

    private bool lateStart = true;

    private Connection connection;

    public int tempRow = 0;
    public int tempColumn = 0;

    private float enableEndTurnWait = 0.3f, addResourcesWait=0.2f, MakeAiTurnWait=1f, MakeAiMainTurnWait=2f;
    private bool isSpeedUp = true;

    public int counter = 1;

    /**
     * Die Buttons müssen zuerst deaktiviert werden, sonst kann dies von den KIs ausgenutzt werden
     */
    private void Awake()
    {
        endTurnBtn.interactable = false;
        rollDiceBtn.interactable = false;
    }


    /**
     * Start wird nach Awake aufgerufen und setzt die Namen der Spieler, sowie einige Variablen und bestimmt wer den ersten Zug hat. 
     */
    void Start()
    {
        player1.isAI = PlayerPrefs.GetInt("player1Ai") == 1 ? true : false;
        player2.isAI = PlayerPrefs.GetInt("player2Ai") == 1 ? true : false;
        player1.name = "Player1";
        player1.SetGameManager(this);
        player2.name = "Player2";
        player2.SetGameManager(this);
        UpdateActivePlayer(player1);

        if (isSpeedUp) {
            enableEndTurnWait = 0.1f;
            addResourcesWait = 0.05f; 
            MakeAiTurnWait = 0.4f; 
            MakeAiMainTurnWait = 0.5f;
        } else
        {
            enableEndTurnWait = 4f;
            addResourcesWait = 3f;
            MakeAiTurnWait = 4f;
            MakeAiMainTurnWait = 5f;
        }

        lateStart = true;
    }

    /**
     * Einige Anweisungen können erst nach der Start-Methode ausgeführt werden, auch wenn sie zu Beginn des Spiels nötig sind. 
     * Jedoch müssen hierzu erst andere Klassen fertig initialisiert sein
     */
    private void LateStart()
    {
        //Die Karte kann erst jetzt geholt werden, da sie vorher noch nicht fertig war.
        map = mapGenerator.map;

        Transform transform = villagePlaces.GetComponent<Transform>();
        for (int i = villagePlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
        {
            Transform child = transform.GetChild(i);
            map.villageBuildPlaces.Add(child.gameObject.GetComponent<buildVillage>().villagePlace);
        }

        transform = roadPlaces.GetComponent<Transform>();
        for (int i = roadPlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
        {
            Transform child = transform.GetChild(i);
            map.roadBuildPlaces.Add(child.gameObject.GetComponent<BuildRoad>().roadPlace);
        }

        foreach (GameObject cityPlace in cityPlaceInstances)
        {
            map.cityBuildPlaces.Add(cityPlace.GetComponent<BuildCity>().cityPlace);
        }

        //Nun kann der erste Zug von Spieler 1 ausgeführt werden
        activePlayer.FirstTurn();
        StartAIProcess();
        if (activePlayer.isAI) MakeAiTurn(MakeAiTurnWait);
    }

    /**
     * Die ersten beiden Züge der Spieler bestehen aus vier einzelnen Schritten
     */
    private void MakeAiTurn(float delay)
    {
        StartCoroutine(ActivateAi(delay));
        StartCoroutine(ActivateAi(delay*2));
        StartCoroutine(ActivateAi(delay*3));
        StartCoroutine(ActivateAi(delay*4));
        StartCoroutine(ActivateAi(delay*5));
    }

    /**
     * Die Hauptzüge bestehen aus beliebig vielen Anfragen
     */
    private void MakeAiMainTurn()
    {
        StartCoroutine(ActivateAiMainTurn(MakeAiMainTurnWait));
    }

    /**
     * IEnumerator für die ersten beiden Züge jedes Spielers
     */
    IEnumerator ActivateAi(float wait)
    {
        yield return new WaitForSeconds(wait);
        Response response = SendToAI(endTurnBtn.interactable, rollDiceBtn.interactable);
        Plan plan = response.plan;
        plan.StringToActions();
       // UnityEngine.Debug.Log("Plan " + plan.ToString());
        activePlayer.FulfillPlan(plan);
    }

    /**
     * IEnumerator für die Hauptzüge
     * Dieser IEnumerator wird erst nicht mehr aufgerufen, wenn der Plan den Wunsch, den Zug zu beenden enthält
     */
    IEnumerator ActivateAiMainTurn(float wait)
    {
        yield return new WaitForSeconds(wait);
        Response response = SendToAI(endTurnBtn.interactable, rollDiceBtn.interactable);
        Plan plan = response.plan;
        plan.StringToActions();
        //UnityEngine.Debug.Log("Plan " + plan.ToString());

        bool wantsToEndTurn = false;
        for (int i = 0; i < plan.actions.Count; i++) {
            if (plan.actions[i].GetType() == typeof(EndTurn))
            {
                wantsToEndTurn = true;
            }
        }
        activePlayer.FulfillPlan(plan);
        yield return new WaitForSeconds(wait);
        if (!wantsToEndTurn)
        {
            StartCoroutine(ActivateAiMainTurn(MakeAiMainTurnWait));
        }   
    }

    /**
     * Das Java-Projekt wird gestartet
     */
    private void StartAIProcess()
    {
        Process foo = new Process();
        foo.StartInfo.FileName = @"C:\Users\tjark\Desktop\CBRSystem.jar";
        foo.StartInfo.FileName = Environment.CurrentDirectory + @"\Assets\CBRSystem4.jar";
        foo.StartInfo.Arguments = "" + Constants.PORT;
        foo.Start();
        connection = new Connection();
    }

    /**
     * Methode zum Wechseln des Spielers nach dem Beenden eines Zuges
     */
    void ChangePlayer()
    {
        cameraView.transform.Rotate(0.0f, 180.0f, 0.0f, 0);
        if (activePlayer == player1)
        {
            UpdateActivePlayer(player2);
        }
        else
        {
            UpdateActivePlayer(player1);
        }
        activePlayer.Turn(); //Der Spieler wurde gewechselt und jetzt darf der nächste seinen Zug ausführen
    }

    /**
     * Die Update-Methode wird kontinuierlich aufgerufen und hält die Spieloberfläche somit aktuell
     * Für den Einsatz der KIs muss sie zusätzlich die Repräsentationen für die KIs angleichen
     */
    public void Update()
    {
        if (enabled)
        {
            // lateStart ermöglicht und die Initialisierung der Karte nach der Startmethode
            if (lateStart)
            {
                lateStart = false;
                LateStart();
            }

            //Falls der Baumodus für Siedlungen aktiviert wurde und dies in den ersten zwei Zügen eines Spielers geschah, so werden die Bauplätze aktiviert
            if (villageFocus.hasFocus && (activePlayer.isFirstTurn || activePlayer.isSecondTurn) && activePlayer.freeBuild && !rollDiceBtn.interactable)
            {
                villagePlaces.SetActive(true);
                Transform transform = villagePlaces.GetComponent<Transform>();
                for (int i = villagePlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
                {
                    Transform child = transform.GetChild(i);
                    child.gameObject.SetActive(true); //GameObject aktivieren
                    child.gameObject.GetComponent<buildVillage>().villagePlace.Activate(); //Repräsentation in der Map für die Computerspieler
                }

                //Wenn eine Siedlung gebaut wurde, werden Bauplätze, die zu nah dran sind, gelöscht.
                if (buildVillage != null)
                {

                    /*GameObject cityPlaceInstance = Instantiate(cityPlace);

                    cityPlaceInstance.transform.position = buildVillage.transform.position;
                    cityPlaceInstance.transform.position = buildVillage.transform.position;
                    if (buildVillage.GetComponent<Renderer>().material.color == player1.color)
                    {
                        cityPlaceInstance.GetComponent<BuildCity>().player = player1;
                    }
                    else
                    {
                        cityPlaceInstance.GetComponent<BuildCity>().player = player2;
                    }
                    cityPlaceInstance.GetComponent<BuildCity>().gameManager = this;
                    cityPlaceInstance.GetComponent<BuildCity>().row = tempRow;
                    cityPlaceInstance.GetComponent<BuildCity>().column = tempColumn;
                    cityPlaceInstance.GetComponent<BuildCity>().UpdatePositionForAI();
                    cityPlaceInstance.GetComponent<BuildCity>().tiles = new List<GameObject>(buildVillage.GetComponent<Village>().tiles);

                    cityPlaceInstances.Add(cityPlaceInstance);
                    map.cityBuildPlaces.Add(cityPlaceInstance.GetComponent<BuildCity>().cityPlace);*/

                    DestroyVillagePlacesInRadius();
                    buildVillage = null;
                }
            }
            //Wenn der Baumodus aktiviert werden soll und genügend Ressourcen vorhanden sind, erfolgt die Aktivierung entsprechender Bauplätze ebenfalls
            else if (villageFocus.hasFocus && activePlayer.HasResourcesForVillage() && !activePlayer.isFirstTurn && !activePlayer.isSecondTurn && !rollDiceBtn.interactable)
            {
                villagePlaces.SetActive(true);
                foreach (GameObject road in activePlayer.roads)
                {
                    Transform transform = villagePlaces.GetComponent<Transform>();
                    for (int i = villagePlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
                    {
                        Transform child = transform.GetChild(i);
                        float distance = (road.transform.position - child.position).magnitude;
                        //UnityEngine.Debug.Log("row und column" + child.gameObject.GetComponent<buildVillage>().villagePlace.row + "column" + child.gameObject.GetComponent<buildVillage>().villagePlace.column);
                        if (distance < roadRange) //Diesmal dürfen nur die Bauplätze aktiviert werden, die nah genug an gebauten Straßen des Spielers liegen
                        {
                            //UnityEngine.Debug.Log("row und column" + child.gameObject.GetComponent<buildVillage>().villagePlace.row + "column" + child.gameObject.GetComponent<buildVillage>().villagePlace.column);
                            child.gameObject.SetActive(true); //GameObject aktivieren
                            child.gameObject.GetComponent<buildVillage>().villagePlace.Activate(); //Repräsentation in der Map für die Computerspieler
                        }
                    }
                }
                //Siedlungen müssen zwei Straßen von der nächsten entfernt werden, die zu nahen Bauplätze werden zerstört
                if (buildVillage != null)
                {
                    /*GameObject cityPlaceInstance = Instantiate(cityPlace);

                    cityPlaceInstance.transform.position = buildVillage.transform.position;
                    if (buildVillage.GetComponent<Renderer>().material.color == player1.color)
                    {
                        cityPlaceInstance.GetComponent<BuildCity>().player = player1;
                    }
                    else
                    {
                        cityPlaceInstance.GetComponent<BuildCity>().player = player2;
                    }
                    cityPlaceInstance.GetComponent<BuildCity>().gameManager = this;
                    cityPlaceInstance.GetComponent<BuildCity>().row = tempRow;
                    cityPlaceInstance.GetComponent<BuildCity>().column = tempColumn;
                    cityPlaceInstance.GetComponent<BuildCity>().UpdatePositionForAI();
                    cityPlaceInstance.GetComponent<BuildCity>().tiles = new List<GameObject>(buildVillage.GetComponent<Village>().tiles);

                    cityPlaceInstances.Add(cityPlaceInstance);
                    map.cityBuildPlaces.Add(cityPlaceInstance.GetComponent<BuildCity>().cityPlace);*/

                    DestroyVillagePlacesInRadius();
                    buildVillage = null;
                }
            }
            else //Deaktivieren der aktiven Bauplätze im Spiel und für die KIs
            {
                Transform transform = villagePlaces.GetComponent<Transform>();
                for (int i = villagePlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
                {
                    Transform child = transform.GetChild(i);
                    if (child.gameObject.GetComponent<buildVillage>().villagePlace != null)
                    {
                        child.gameObject.GetComponent<buildVillage>().villagePlace.Deactivate(); //Repräsentation in der Map für die Computerspieler
                        child.gameObject.SetActive(false);
                    }

                }
                foreach (VillageBuildPlace place in map.villageBuildPlaces)
                {
                    if (place != null)
                    {
                        place.Deactivate();
                    }
                }
                villagePlaces.SetActive(false);
            }

            //Wenn der Baumodus aktiviert werden soll und genügend Ressourcen vorhanden sind, erfolgt die Aktivierung entsprechender Bauplätze ebenfalls
            if (cityFocus.hasFocus && activePlayer.HasResourcesForCity() && !activePlayer.isFirstTurn && !activePlayer.isSecondTurn && !rollDiceBtn.interactable)
            {

                foreach (GameObject cityPlace in cityPlaceInstances)
                {
                    /*UnityEngine.Debug.Log("***************Zeile: " + cityPlace.GetComponent<BuildCity>().row
                        + "\n***************Spalte: " + cityPlace.GetComponent<BuildCity>().column
                        + "\n***************Bauplatzspielername: " + cityPlace.GetComponent<BuildCity>().player.name
                        + "\n***************Aktiver Spielername: " + activePlayer.name);*/
                    if (cityPlace.GetComponent<BuildCity>().player == activePlayer)
                    {
                        cityPlace.gameObject.SetActive(true);
                        cityPlace.GetComponent<BuildCity>().cityPlace.Activate();
                    }
                }
            }
            else //Deaktivieren der aktiven Bauplätze im Spiel und für die KIs
            {
                foreach (GameObject cityPlace in cityPlaceInstances)
                {
                    cityPlace.gameObject.SetActive(false);
                    cityPlace.GetComponent<BuildCity>().cityPlace.Deactivate();
                }
            }




            // Das gleiche Prozedere wie für die Siedlungen wird auch auf die Straßen angewandt, nur dass hier entweder Siedlungen oder Straßen im direkten Umkreis liegen müssen. 
            if (roadFocus.hasFocus && ((activePlayer.HasResourcesForRoad() && !(activePlayer.isFirstTurn || activePlayer.isSecondTurn)) || (activePlayer.isFirstTurn && activePlayer.freeBuildRoad) || (activePlayer.isSecondTurn && activePlayer.freeBuildRoad)) && !rollDiceBtn.interactable)
            {
                //Solche Bauplätze aktivieren, die nah genug an Siedlungen liegen
                roadPlaces.SetActive(true);
                for (int i = roadPlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
                {
                    Transform transform = roadPlaces.GetComponent<Transform>();
                    Transform child = transform.GetChild(i);

                    //Solche Bauplätze aktivieren, die nah genug an Siedlungen liegen
                    foreach (GameObject village in activePlayer.villages)
                    {
                        float distance = (village.transform.position - child.position).magnitude;
                        if (distance < roadRange)
                        {
                            child.gameObject.SetActive(true);
                            child.gameObject.GetComponent<BuildRoad>().roadPlace.Activate(); //Repräsentation in der Map für die Computerspieler
                        }
                    }
                    //Solche Bauplätze aktivieren, die nah genug an Städten liegen
                    foreach (GameObject city in activePlayer.cities)
                    {
                        float distance = (city.transform.position - child.position).magnitude;
                        if (distance < roadRange)
                            if (distance < roadRange)
                            {
                                child.gameObject.SetActive(true);
                                child.gameObject.GetComponent<BuildRoad>().roadPlace.Activate(); //Repräsentation in der Map für die Computerspieler
                            }

                    }
                    //Solche Bauplätze aktivieren, die nah genug an anderen Straßen liegen
                    foreach (GameObject road in activePlayer.roads)
                    {
                        float distance = (road.transform.position - child.position).magnitude;
                        if (distance < roadRange)
                        {
                            child.gameObject.SetActive(true);
                            child.gameObject.GetComponent<BuildRoad>().roadPlace.Activate(); //Repräsentation in der Map für die Computerspieler
                        }
                    }
                }
            }
            else
            {   //Deaktivieren der Bauplätze für Straßen
                Transform transform = roadPlaces.GetComponent<Transform>();
                for (int i = roadPlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
                {
                    Transform child = transform.GetChild(i);
                    child.gameObject.GetComponent<BuildRoad>().roadPlace.Deactivate(); //Repräsentation in der Map für die Computerspieler
                    child.gameObject.SetActive(false);

                }
                foreach (RoadBuildPlace place in map.roadBuildPlaces)
                {
                    if (place != null)
                    {
                        place.Deactivate();
                    }
                }
                roadPlaces.SetActive(false);
            }
            //Wenn der konstenlose Bau für Siedlung und Straße in den jeweils ersten zwei Zügen jedes Spielers verbraucht wurde, so darf der Zug beendet werden. 
            if (!activePlayer.freeBuild && !activePlayer.freeBuildRoad && (activePlayer.isFirstTurn || activePlayer.isSecondTurn))
                endTurnBtn.interactable = true;
        }
    }

    /**
     * Methode, die Bauplätze, die zu nah an der nächsten Siedlung liegen zerstört.
     */
    public void DestroyVillagePlacesInRadius()
    {
        Transform transform = villagePlaces.GetComponent<Transform>();
        for (int i = villagePlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
        {
            Transform child = transform.GetChild(i);
            float distanceSqr = (buildVillage.transform.position - child.position).magnitude;
            if (distanceSqr < villageRange)
            {
                Destroy(child.gameObject);
                map.villageBuildPlaces.Remove(child.gameObject.GetComponent<buildVillage>().villagePlace);
            }
        }
    }

    /**
     * Methode, zum Würfel werfen
     */
    public void OnRollDice()
    {
        // left mouse button clicked so roll random colored dice 2 of each dieType
        Dice.Clear();
        Dice.Roll("1d6", "d6-" + "red", spawnPoint.transform.position, Force());
        Dice.Roll("1d6", "d6-" + "red", spawnPoint.transform.position, Force());
        rollDiceBtn.interactable = false;

        //Nachdem gewürfelt wurde, werden die Ressourcen aktualisiert und der Zug darf beendet werden
        StartCoroutine(AddResources()); 
        StartCoroutine(EnableEndTurn());
    }

    /**
     * Die Kraft mit der die Würfel geworfen werden
     */
    private Vector3 Force()
    {
        Vector3 rollTarget = Vector3.zero + new Vector3(2 + 7 * UnityEngine.Random.value, .5F + 4 * UnityEngine.Random.value, -2 - 3 * UnityEngine.Random.value);
        return Vector3.Lerp(spawnPoint.transform.position, rollTarget, 1).normalized * (-35 - UnityEngine.Random.value * 20);
    }

   
    /**
     * Hinzufügen der Ressourcen nach dem Würfeln
     */
    IEnumerator AddResources()
    {
        yield return new WaitForSeconds(addResourcesWait);
        System.Random rnd = new System.Random();
        int number;
        if (isSpeedUp)
        {
            number = rnd.Next(1, 13);
        }
        else
        {
            number = Dice.GetValue("");
        }
        
        
        player1.CollectResources(number);
        player2.CollectResources(number);
        activePlayer.UpdateResources();
    }

    /**
     * Erlauben des Beenden des Zuges nach dem Würfeln
     */
    IEnumerator EnableEndTurn()
    {
        yield return new WaitForSeconds(enableEndTurnWait);
        endTurnBtn.interactable = true;
    }

    /**
     * Das Tatsächliche Beenden eines Zuges
     * Sollten hier genügend Siegespunkte erreicht worden sein, gewinnt der aktive Spieler
     * Außerdem werden Anpassungen für die Züge durchgeführt, da die Reihenfolge für die ersten beiden Züge anders ist, als für die normalen. 
     */
    public void OnEndTurn()
    {
        if (activePlayer.victoryPoints >= 10)
        {
            gameOverText.text = "Spiel beendet! Spieler " + ((activePlayer == player1) ? "1" : "2") + " hat gewonnen!"; //Sieger anzeigen
            newGameBtn.gameObject.SetActive(true); //Start eines neuen Spiels ermöglichen
            this.enabled = false; //Das aktuelle Spiel kann nicht weitergeführt werden
            string name = activePlayer == player1 ? "Player1" : "Player2";
            UnityEngine.Debug.Log("********************************Spielergebnis********************************************:"
                + "\nSpielnummer: " + counter
                + "\nSiegpunkte Spieler 1: " + player1.victoryPoints
                + "\nSiegpunkte Spieler 2: " + player2.victoryPoints
                + "\nSieger: " + name);

        }
        else
        {
            if (activePlayer.isFirstTurn && activePlayer == player1)
            {
                UpdateActivePlayer(player2);
                cameraView.transform.Rotate(0.0f, 180.0f, 0.0f, 0);

                activePlayer.FirstTurn();
                endTurnBtn.interactable = false;
                rollDiceBtn.interactable = false;
                if (activePlayer.isAI) MakeAiTurn(MakeAiTurnWait);
            }
            else if (activePlayer.isFirstTurn && activePlayer == player2)
            {
                activePlayer.SecondTurn();
                endTurnBtn.interactable = false;
                rollDiceBtn.interactable = false;

                if (activePlayer.isAI) MakeAiTurn(MakeAiTurnWait);
            }
            else if (activePlayer.isSecondTurn && activePlayer == player2)
            {
                activePlayer.CollectResourcesForVillage(activePlayer.villages[activePlayer.villages.Count - 1]);
                UpdateActivePlayer(player1);
                cameraView.transform.Rotate(0.0f, 180.0f, 0.0f, 0);

                activePlayer.SecondTurn();
                endTurnBtn.interactable = false;
                rollDiceBtn.interactable = false;

                if (activePlayer.isAI) MakeAiTurn(MakeAiTurnWait);
            }
            else if (activePlayer.isSecondTurn && activePlayer == player1)
            {
                activePlayer.CollectResourcesForVillage(activePlayer.villages[activePlayer.villages.Count - 1]);
                activePlayer.Turn();
                endTurnBtn.interactable = false;
                rollDiceBtn.interactable = true;

                if (activePlayer.isAI) MakeAiMainTurn();
            }
            else
            {
                ChangePlayer();
                rollDiceBtn.interactable = true;
                endTurnBtn.interactable = false;

                if (activePlayer.isAI) MakeAiMainTurn();
            }
            activePlayer.UpdateResources(); //Ressourcenanzeige aktualisieren
        }
    }

    /**
     * Senden einer Anfrage an den Server. Eine Antwort wird erwartet
     */
    public Response SendToAI(bool isAbledToEndTurn, bool allowedToRollDice)
    {
        Situation situation = new Situation(activePlayer.getName(), new Status(map, activePlayer.isFirstTurn, activePlayer.isSecondTurn, activePlayer.victoryPoints,
            activePlayer.longestRoad, activePlayer.hasLongestRoad, activePlayer.brick, activePlayer.wheat, activePlayer.stone, activePlayer.wood,
            activePlayer.sheep, activePlayer.freeBuild, activePlayer.freeBuildRoad, activePlayer.villages, activePlayer.roads, isAbledToEndTurn, allowedToRollDice, 
            SimulateVillagePlaceActivation(), SimulateRoadPlaceActivation()));

        Request request = new Request(situation);
        Response response = connection.Send(request);
        for (int i = 0; i <= 3 && response.situation.player == null; i++)
        {
            if (i < 3)
            {
                request = new Request(situation);
                response = connection.Send(request);
                //UnityEngine.Debug.Log("Fehlversuch: " + i);
                Thread.Sleep(500);
            }
            else
            {
                activePlayer.FulfillPlan(new Plan());
            }
        }
        //UnityEngine.Debug.Log(response.ToString());
        return response;
    }

    private bool SimulateVillagePlaceActivation()
    {
        bool focusState = villagePlaces.activeSelf;
        if (activePlayer.isFirstTurn || activePlayer.isSecondTurn)
        {
            return true;
        }
        else 
        {
            villagePlaces.SetActive(true);
            foreach (GameObject road in activePlayer.roads)
            {
                Transform transform = villagePlaces.GetComponent<Transform>();
                for (int i = villagePlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
                {
                    Transform child = transform.GetChild(i);
                    float distance = (road.transform.position - child.position).magnitude;
                    if (distance < roadRange) 
                    {
                        villagePlaces.SetActive(focusState);
                        return true;
                    }
                }
            }
        }
        villagePlaces.SetActive(focusState);
        return false;
    }

    private bool SimulateRoadPlaceActivation()
    {
        bool focusState = roadPlaces.activeSelf;
        roadPlaces.SetActive(true);
        for (int i = roadPlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
        {
            Transform transform = roadPlaces.GetComponent<Transform>();
            Transform child = transform.GetChild(i);

            foreach (GameObject village in activePlayer.villages)
            {
                float distance = (village.transform.position - child.position).magnitude;
                if (distance < roadRange)
                {
                    roadPlaces.SetActive(focusState);
                    return true;
                }
            }
            foreach (GameObject city in activePlayer.cities)
            {
                float distance = (city.transform.position - child.position).magnitude;
                if (distance < roadRange)
                    if (distance < roadRange)
                    {
                        roadPlaces.SetActive(focusState);
                        return true;
                    }

            }
            foreach (GameObject road in activePlayer.roads)
            {
                float distance = (road.transform.position - child.position).magnitude;
                if (distance < roadRange)
                {
                    roadPlaces.SetActive(focusState);
                    return true;
                }
            }
        }
        roadPlaces.SetActive(focusState);
        return false;
    }


    /**
     * Methode zum Starten eines neuen Spiels
     */
    public void OnNewGame()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
    }


    /**
     * Methode zum aktivieren eines Spielers
     */
    private void UpdateActivePlayer(PlayerScript player)
    {
        activePlayer = player;

        activePlayer.UpdateVictoryPoints();
        if (player1.longestRoad > player2.longestRoad)
        {
            player1.hasLongestRoad = true;
            player2.hasLongestRoad = false;
            player1.victoryPoints += 2;
        } else if (player1.longestRoad < player2.longestRoad)
        {
            player2.hasLongestRoad = true;
            player1.hasLongestRoad = false;
            player2.victoryPoints += 2;
        } else
        {
            player1.hasLongestRoad = false;
            player2.hasLongestRoad = false;
        }


        Transform transform = villagePlaces.GetComponent<Transform>();
        for (int i = villagePlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
        {
            Transform child = transform.GetChild(i);
            child.gameObject.SetActive(true);
        }
        villagePlaces.SetActive(true);

        buildVillage[] buildVillages = villagePlaces.GetComponentsInChildren<buildVillage>();
        foreach (buildVillage build in buildVillages)
        {
            build.player = activePlayer;
        }

        for (int i = villagePlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
        {
            Transform child = transform.GetChild(i);
            child.gameObject.SetActive(false);
        }
        villagePlaces.SetActive(false);

        transform = roadPlaces.GetComponent<Transform>();
        for (int i = roadPlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
        {
            Transform child = transform.GetChild(i);
            child.gameObject.SetActive(true);
        }
        roadPlaces.SetActive(true);

        BuildRoad[] buildRoads = roadPlaces.GetComponentsInChildren<BuildRoad>();
        foreach (BuildRoad build in buildRoads)
        {
            build.player = activePlayer;
        }

        for (int i = roadPlaces.GetComponent<Transform>().childCount - 1; i >= 0; i--)
        {
            Transform child = transform.GetChild(i);
            child.gameObject.SetActive(false);
        }
        roadPlaces.SetActive(false);
    }

    public int getTempRow()
    {
        return tempRow;
    }

    public void setTempRow(int row)
    {
        this.tempRow = row;
    }

    public int getTempColumn()
    {
        return tempColumn;
    }

    public void setTempColumn(int column)
    {
        this.tempColumn = column;
    }
}
