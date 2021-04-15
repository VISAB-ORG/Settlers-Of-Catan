using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Assets.Scripts.Model;


/* 
 * Klasse zum Bau von Siedlungen 
 * Jeder Siedlungsbauplatz verfügt über eine eigene Instanz
 */
public class buildVillage : MonoBehaviour
{
    public PlayerScript player;
    public GameManager gameManager;

    //Position des GameObjects auf dem Spielfeld
    public int row;
    public int column;

    public VillageBuildPlace villagePlace;

    private void Awake()
    {
        // Repräsentation für die KI hinzufügen
        PlaceScript placeScript = this.GetComponent<PlaceScript>();
        villagePlace = new VillageBuildPlace(this.gameObject, row, column);

        if (placeScript.resourceTile1 != null)
            if (placeScript.resourceTile1.GetComponentInChildren<Renderer>().sharedMaterial.name == "wheat 1")
            {
                villagePlace.resources.Add("wheat");
            } else
            {
                villagePlace.resources.Add(placeScript.resourceTile1.GetComponentInChildren<Renderer>().sharedMaterial.name);
            }
        if (placeScript.resourceTile2 != null)
            if (placeScript.resourceTile2.GetComponentInChildren<Renderer>().sharedMaterial.name == "wheat 1")
            {
                villagePlace.resources.Add("wheat");
            }
            else
            {
                villagePlace.resources.Add(placeScript.resourceTile2.GetComponentInChildren<Renderer>().sharedMaterial.name);
            }
        if (placeScript.resourceTile3 != null)
            if (placeScript.resourceTile3.GetComponentInChildren<Renderer>().sharedMaterial.name == "wheat 1")
            {
                villagePlace.resources.Add("wheat");
            }
            else
            {
                villagePlace.resources.Add(placeScript.resourceTile3.GetComponentInChildren<Renderer>().sharedMaterial.name);
            }
    }

    /*
     * Bei einem Klick auf den aktivierten Bauplatz wird der Bau einer Siedlung eingeleitet
     */
    private void OnMouseDown()
    {
        //Liefert nur eine Siedlung zurück, wenn die Bedingungen für den Spieler erfüllt sind
        GameObject villageInstance = player.BuildVillage(this.transform.position);

        //Die Informationen über anliegende Ressourcenfelder werden aus dem Bauplatz auf die Siedlung übertragen
        Village villageScript = villageInstance.GetComponent<Village>();
        PlaceScript placeScript = this.GetComponent<PlaceScript>();

        if (placeScript.resourceTile1 != null)
            villageScript.tiles.Add(placeScript.resourceTile1);
        if (placeScript.resourceTile2 != null)
            villageScript.tiles.Add(placeScript.resourceTile2);
        if (placeScript.resourceTile3 != null)
            villageScript.tiles.Add(placeScript.resourceTile3);

        player.villages.Add(villageInstance);
        gameManager.setTempRow(row);
        gameManager.setTempColumn(column);
        gameManager.buildVillage = villageInstance;
        GameObject cityPlaceInstance = Instantiate(gameManager.cityPlace);

        cityPlaceInstance.transform.position = gameManager.buildVillage.transform.position;
        cityPlaceInstance.transform.position = gameManager.buildVillage.transform.position;
        if (gameManager.buildVillage.GetComponent<Renderer>().material.color == gameManager.player1.color)
        {
            cityPlaceInstance.GetComponent<BuildCity>().player = gameManager.player1;
        }
        else
        {
            cityPlaceInstance.GetComponent<BuildCity>().player = gameManager.player2;
        }
        cityPlaceInstance.GetComponent<BuildCity>().gameManager = gameManager;
        cityPlaceInstance.GetComponent<BuildCity>().row = gameManager.tempRow;
        cityPlaceInstance.GetComponent<BuildCity>().column = gameManager.tempColumn;
        cityPlaceInstance.GetComponent<BuildCity>().UpdatePositionForAI();
        cityPlaceInstance.GetComponent<BuildCity>().tiles = new List<GameObject>(gameManager.buildVillage.GetComponent<Village>().tiles);

        gameManager.cityPlaceInstances.Add(cityPlaceInstance);
        gameManager.map.cityBuildPlaces.Add(cityPlaceInstance.GetComponent<BuildCity>().cityPlace);
        gameManager.DestroyVillagePlacesInRadius();
        gameManager.Update();
    }

    /*
     * Übernimmt die gleiche Funktion wie OnMouseDown(), aber wird aufgerufen, wenn die Anweisung von der KI kommt
     */
    public void Instantiate()
    {
        OnMouseDown();
    }
}
