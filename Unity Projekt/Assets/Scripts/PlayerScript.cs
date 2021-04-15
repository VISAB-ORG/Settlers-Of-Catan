using Assets.Scripts.CBR.Plan;
using Assets.Scripts.Model;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.ExceptionServices;
using System.Runtime.Versioning;
using System.Threading;
using System.Threading.Tasks;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.UIElements;


/**
 * Klasse zum Ermöglichen der Steuerung eines Spielers auf dem Spielfeld
 */
public class PlayerScript : MonoBehaviour
{
    public new string name;
    public Camera cameraView;
    public GameObject village;
    public GameObject city;
    public GameObject road;
    public float roadRange;

    public Color color;

    public Vector3 playerView;

    public int brick = 0;
    public int wheat = 0;

    public int stone = 0;
    public int wood = 0;
    public int sheep = 0;

    public Text brickTxt, woodTxt, sheepTxt, stoneTxt, wheatTxt;

    public bool isFirstTurn, isSecondTurn;

    public bool freeBuild;
    public bool freeBuildRoad;

    public List<GameObject> villages;
    public List<GameObject> cities;
    public List<GameObject> roads;

    public int longestRoad;
    public bool hasLongestRoad;
    public int victoryPoints;

    private List<GameObject> roadsModified;

    public bool isAI;
    private GameManager gm;

    private void Awake()
    {
        isFirstTurn = false;
        isSecondTurn = false;
        longestRoad = 0;
        victoryPoints = 0;
        roadRange = 1.1f;
        isAI = true;
    }


    void Start()
    {
        brickTxt.text = "Lehm: " + brick.ToString();
        woodTxt.text = "Holz: " + wood.ToString();
        wheatTxt.text = "Getreide: " + wheat.ToString();
        sheepTxt.text = "Schafe: " + sheep.ToString();
        stoneTxt.text = "Stein: " + stone.ToString();

        villages = new List<GameObject>();
    }

    public string getName()
    {
        return name;
    }

    /**
     * Sorgt für die richtigen Einstellungen zu Beginn des ersten Zuges
     */
    public void FirstTurn()
    {
        /*brick = 5;
        wheat = 5;
        stone = 5;
        wood = 5;
        sheep = 5;*/
        AdjustCamera();
        isFirstTurn = true;
        freeBuild = true;
        freeBuildRoad = false;
    }

    /**
     * Sorgt für die richtigen Einstellungen zu Beginn des zweiten Zuges
     */
    public void SecondTurn()
    {
        AdjustCamera();
        isFirstTurn = false;
        isSecondTurn = true;
        freeBuild = true;
        freeBuildRoad = false;
    }

    /**
     * Sorgt für die richtigen Einstellungen zu Beginn eines Zuges
     */
    public void Turn()
    {
        AdjustCamera();
        freeBuild = false;
        freeBuildRoad = false;
        isFirstTurn = false;
        isSecondTurn = false;
    }

    /*
     * Methode zum Anpassen der Kamera an den akutellen Spieler
     */
    private void AdjustCamera()
    {
        cameraView.transform.position = playerView;
    }

    /**
     * Prüfung, ob genügend Ressourcen für den Bau einer Siedlung vorhanden sind
     */
    public bool HasResourcesForVillage()
    {
        if (brick >= 1 && wood >= 1 && sheep >= 1 && wheat >= 1)
            return true;
        return false;
    }

    /**
     * Prüfung, ob genügend Ressourcen für den Bau einer Stadt vorhanden sind
     */
    public bool HasResourcesForCity()
    {
        if (stone >= 3 && wheat >= 2)
            return true;
        return false;
    }

    /**
     * Prüfung, ob genügend Ressourcen für den Bau einer Straße vorhanden sind
     */
    public bool HasResourcesForRoad()
    {
        if (brick >= 1 && wood >= 1)
            return true;
        return false;
    }

    /*
     * Sammeln von Ressourcen für eine bestimmte Siedlung
     */
    public void CollectResourcesForVillage(GameObject village)
    {
        foreach (GameObject tile in village.GetComponent<Village>().tiles)
        {
            //Debug.Log(tile.GetComponentInChildren<Renderer>().sharedMaterial.name);
            if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "wheat" || tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "wheat 1")
            {
                wheat++;
            }
            else if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "sheep")
            {
                sheep++;
            }
            else if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "rock")
            {
                stone++;
            }
            else if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "brick")
            {
                brick++;
            } 
            else if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "wood")
            {
                wood++;
            }
        }
        UpdateResources();
    }

    /*
     * Sammeln von Ressourcen für eine bestimmte Nummer
     */
    public void CollectResources(int number)
    {
        foreach (GameObject village in villages)
        {
            foreach (GameObject tile in village.GetComponent<Village>().tiles)
            {
                if (tile.GetComponent<Tile>().number == number)
                {
                    //Debug.Log(tile.GetComponentInChildren<Renderer>().sharedMaterial.name);
                    if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "wheat" || tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "wheat 1")
                    {
                        wheat++;
                    }
                    else if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "sheep")
                    {
                        sheep++;
                    }
                    else if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "rock")
                    {
                        stone++;
                    }
                    else if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "brick")
                    {
                        brick++;
                    }
                    else if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "wood")
                    {
                        wood++;
                    }
                }
            }
        }

        foreach (GameObject city in cities)
        {
            foreach (GameObject tile in city.GetComponent<City>().tiles)
            {
                if (tile.GetComponent<Tile>().number == number)
                {
                    //Debug.Log(tile.GetComponentInChildren<Renderer>().sharedMaterial.name);
                    if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "wheat 1")
                    {
                        wheat += 2;
                    }
                    else if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "sheep")
                    {
                        sheep += 2;
                    }
                    else if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "rock")
                    {
                        stone += 2;
                    }
                    else if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "brick")
                    {
                        brick += 2;
                    }
                    else if (tile.GetComponentInChildren<Renderer>().sharedMaterial.name == "wood")
                    {
                        wood += 2;
                    }
                }
            }
        }
        UpdateResources();
    }

    /**
     * Siedlung bauen, funktioniert nur, wenn der Spieler, die Voraussetzungen erfüllt
     */
    public GameObject BuildVillage(Vector3 position)
    {
        if (freeBuild || this.HasResourcesForVillage())
        {
            if (!freeBuild)
            {
                brick--;
                wood--;
                sheep--;
                wheat--;
                freeBuildRoad = false;
            } else
            {
                freeBuild = false;
                freeBuildRoad = true;
            }
            GameObject villageInstance = Instantiate(village);
            villageInstance.transform.position = position;
            villageInstance.GetComponent<Renderer>().material.color = color;



            UpdateResources();

            victoryPoints++;
            return villageInstance;
        }
        return null;
    }

    /**
     * Stadt bauen, funktioniert nur, wenn der Spieler, die Voraussetzungen erfüllt
     */
    public GameObject BuildCity(Vector3 position)
    {
        if (this.HasResourcesForCity())
        {
            wheat -= 2;
            stone -= 3;

            GameObject cityInstance = Instantiate(city);
            cityInstance.transform.position = position;
            cityInstance.GetComponent<Renderer>().material.color = color;

            GameObject tempVillage = null;
            foreach (GameObject village in villages)
            {
                if (village.transform.position == cityInstance.transform.position)
                {
                    tempVillage = village;
                }
            }
            if (tempVillage != null)
            {
                villages.Remove(tempVillage);
                Destroy(tempVillage);
            }

            UpdateResources();

            victoryPoints += 2;
            return cityInstance;
        }
        return null;
    }

    /**
     * Straße bauen, funktioniert nur, wenn der Spieler, die Voraussetzungen erfüllt
     */
    public GameObject BuildRoad(Vector3 position, Quaternion rotation)
    {
        if (freeBuildRoad || this.HasResourcesForRoad()) 
        { 

            if (!freeBuildRoad)
            {
                brick--;
                wood--;
            }

            GameObject roadInstance = Instantiate(road);
            roadInstance.transform.position = position;
            roadInstance.transform.rotation = rotation;
            roadInstance.transform.Rotate(-90.0f, 0.0f, 0.0f, Space.Self);
            roadInstance.GetComponent<Renderer>().material.color = color;

            freeBuildRoad = false;

            UpdateResources();

            return roadInstance;
        }
        return null;
    }

    /*
     * Methode zum Aktualisieren der Anzeige der Ressourcen des Spielers
     */
    public void UpdateResources()
    {
        brickTxt.text = "Lehm: " + brick.ToString();
        woodTxt.text = "Holz: " + wood.ToString();
        wheatTxt.text = "Getreide: " + wheat.ToString();
        sheepTxt.text = "Schafe: " + sheep.ToString();
        stoneTxt.text = "Stein: " + stone.ToString();
    }


    /*
     * Die längste Straße eines Spielers berechnen
     */
    public void CalculateRoadLength()
    {
        if (roads.Count > 0)
        {
            List<int> roadLengths = new List<int>();
            foreach(GameObject road in roads)
            //for (int i = 0; i < roads.Count; i++)
            {
                roadsModified = new List<GameObject>(roads);
                //road = roads[i];
                roadLengths.Add(FindRoadNeighbors(road));
            }
            longestRoad = roadLengths.Max();
            foreach (int length in roadLengths)
            {
                //Debug.Log("Länge der Straße: " + length);
            }
            //Debug.Log("Längste Straße: " + longestRoad);
        }
        else
        {
            longestRoad = 0;
        }
    }

    /*
     * Straßen, die an anderen liegen bestimmten
     */
    private int FindRoadNeighbors(GameObject road)
    {
        int neighbors = 0;
        List<GameObject> roadsUpdated = new List<GameObject>();

        for (int i = 0; i < this.roadsModified.Count;)
        {
            GameObject r = this.roadsModified[i];
            //Debug.Log("position road: " + road.transform.position);
            //Debug.Log("position r: " + r.transform.position);

            float distance = (road.transform.position - r.transform.position).magnitude;
            //Debug.Log("Distanz: " + distance);
            //Debug.Log("Erlaubte Distanz: " + roadRange);
            if (distance < roadRange)
            {
                roadsModified.Remove(r);
                roadsUpdated.Add(r);
                neighbors++;
            } 
            else
            {
                i++;
            }
        }
        foreach (GameObject r in roadsUpdated)
        {
            neighbors += FindRoadNeighbors(r);
        }
        return neighbors;
    }

    /*
     * Aktualisieren der Siegespunkte
     */
    public void UpdateVictoryPoints()
    {
        victoryPoints = villages.Count();
        victoryPoints += cities.Count();
    }

    /*
     * Methode, die ausgibt, dass der Spieler gewonnen hat
     */
    public void VictoryMessage()
    {
        brickTxt.text = "Ich habe gewonnen";
    }

    /*
     * Methode, die einen Plan eines KI-Spielers ausführt
     */
    public bool FulfillPlan(Plan plan)
    {
        for (int i = 0; i < plan.GetActionCount(); i++)
        {
            if (plan.actions[i].GetType() == typeof(ActivateVillagePlaces))
            {
                gm.villageFocus.OnMouseDown();
                gm.Update(); //sicher gehen, dass Update mindestens einmal aufgerufen wurde.
            }
            else if (plan.actions[i].GetType() == typeof(ActivateCityPlaces))
            {
                gm.cityFocus.OnMouseDown();
                gm.Update(); //sicher gehen, dass Update mindestens einmal aufgerufen wurde.
            }
            else if (plan.actions[i].GetType() == typeof(ActivateRoadPlaces))
            {
                gm.roadFocus.OnMouseDown();
                gm.Update(); //sicher gehen, dass Update mindestens einmal aufgerufen wurde.
            }
            else if (plan.actions[i].GetType() == typeof(BuildVillage))
            {
                BuildVillage buildvillage = (BuildVillage) plan.actions[i];
                //Debug.Log("row" + buildvillage.row);
                //Debug.Log("row" + buildvillage.column);
                if (gm.map.getVillagePlaceByPosition(buildvillage.row, buildvillage.column).gameObject.activeSelf)
                {
                    gm.map.getVillagePlaceByPosition(buildvillage.row, buildvillage.column).gameObject.GetComponent<buildVillage>().Instantiate();
                }
            }
            else if (plan.actions[i].GetType() == typeof(Assets.Scripts.CBR.Plan.BuildCity))
            {

                Assets.Scripts.CBR.Plan.BuildCity buildcity = (Assets.Scripts.CBR.Plan.BuildCity) plan.actions[i];
                //Debug.Log("row" + buildcity.row);
                //Debug.Log("row" + buildcity.column);
                if (gm.map.getCityPlaceByPosition(buildcity.row, buildcity.column).gameObject.activeSelf)
                {
                    gm.map.getCityPlaceByPosition(buildcity.row, buildcity.column).gameObject.GetComponent<BuildCity>().Instantiate();
                }
            }
            else if (plan.actions[i].GetType() == typeof(Assets.Scripts.CBR.Plan.BuildRoad))
            {
                Assets.Scripts.CBR.Plan.BuildRoad buildroad = (Assets.Scripts.CBR.Plan.BuildRoad) plan.actions[i];
                //Debug.Log("row" + buildroad.row);
                //Debug.Log("row" + buildroad.column);
                if (gm.map.getRoadPlaceByPosition(buildroad.row, buildroad.column).gameObject.activeSelf)
                {
                    gm.map.getRoadPlaceByPosition(buildroad.row, buildroad.column).gameObject.GetComponent<BuildRoad>().Instantiate();
                }
            }
            else if (plan.actions[i].GetType() == typeof(EndTurn))
            {
                if (gm.endTurnBtn.interactable)
                    gm.OnEndTurn();
            }
            else if (plan.actions[i].GetType() == typeof(RollDice))
            {
                if (gm.rollDiceBtn.interactable)
                    gm.OnRollDice();
            }
        }
        return true;
    }

    /*
     * Über diese Methode wird der GameManger des Spielers gesetzt
     */
    public void SetGameManager(GameManager gm)
    {
        this.gm = gm;
    }
}