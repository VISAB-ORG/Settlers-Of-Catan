using Assets.Scripts.Model;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BuildCity : MonoBehaviour
{
    public PlayerScript player;
    public GameManager gameManager;

    //Position des GameObjects auf dem Spielfeld
    public int row;
    public int column;

    public Assets.Scripts.Model.CityBuildPlace cityPlace;
    public List<GameObject> tiles;

    private void Awake()
    {
        // Repräsentation für die KI hinzufügen
        cityPlace = new Assets.Scripts.Model.CityBuildPlace(this.gameObject, row, column);
    }

    /*
     * Bei einem Klick auf den aktivierten Bauplatz wird der Bau einer Siedlung eingeleitet
     */
    private void OnMouseDown()
    {
        cityPlace.row = row;
        cityPlace.column = column;
        //Liefert nur eine Siedlung zurück, wenn die Bedingungen für den Spieler erfüllt sind
        GameObject cityInstance = player.BuildCity(this.transform.position);

        //Die Informationen über anliegende Ressourcenfelder werden aus dem Bauplatz auf die Siedlung übertragen
        City cityScript = cityInstance.GetComponent<City>();
        cityScript.tiles = new List<GameObject>(this.tiles);

        player.cities.Add(cityInstance);

        gameManager.cityPlaceInstances.Remove(this.gameObject);
        gameManager.map.cityBuildPlaces.Remove(cityPlace);
        Destroy(this.gameObject);
        cityPlace = null;
    }

    public void UpdatePositionForAI()
    {
        cityPlace.row = row;
        cityPlace.column = column;
    }

    /*
     * Übernimmt die gleiche Funktion wie OnMouseDown(), aber wird aufgerufen, wenn die Anweisung von der KI kommt
     */
    public void Instantiate()
    {
        cityPlace.row = row;
        cityPlace.column = column;
        OnMouseDown();
    }
}
