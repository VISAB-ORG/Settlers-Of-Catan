using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Assets.Scripts.Model;


/* 
 * Klasse zum Bau von Straßen
 * Jeder Siedlungsbauplatz verfügt über eine eigene Instanz
 */
public class BuildRoad : MonoBehaviour
{
    public PlayerScript player;
    public GameManager gameManager;

    //Position des GameObjects auf dem Spielfeld
    public int row;
    public int column;

    public RoadBuildPlace roadPlace;

    private void Awake()
    {
        // Repräsentation für die KI hinzufügen
        roadPlace = new RoadBuildPlace(this.gameObject, row, column);
    }

    /*
     * Bei einem Klick auf den aktivierten Bauplatz wird der Bau einer Straße eingeleitet
     */
    private void OnMouseDown()
    {
        //Liefert nur eine Siedlung zurück, wenn die Bedingungen für den Spieler erfüllt sind
        GameObject roadInstance = player.BuildRoad(this.transform.position, this.transform.rotation);

        //Prüfung, ob eine Straße erzeugt wurde
        if (roadInstance != null)
        {
            player.roads.Add(roadInstance);
            player.CalculateRoadLength();
            gameManager.buildRoad = roadInstance;

            this.gameObject.transform.parent = null;
            Destroy(this.gameObject);
            gameManager.map.roadBuildPlaces.Remove(roadPlace);
            roadPlace = null;
        }
    }

    /*
     * Übernimmt die gleiche Funktion wie OnMouseDown(), aber wird aufgerufen, wenn die Anweisung von der KI kommt
     */
    public void Instantiate()
    {
        OnMouseDown();
    }
}
