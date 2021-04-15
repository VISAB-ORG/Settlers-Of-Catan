using System.Collections;
using System.Collections.Generic;
using System.Globalization;
using UnityEngine;
using UnityEngine.Tilemaps;
using Assets.Scripts.Model;


/**
 * Klasse zum Generieren des Spielfeldes
 */
public class MapGeneratorScript : MonoBehaviour
{

    public GameObject hexTile;
    public GameObject village;
    public GameObject road;
    public Material wheat, sheep, rock, sand, ocean, brick, wood;
    public Material num2, num3, num4, num5, num6, num8, num9, num10, num11, num12;

    public GameObject hex1, hex2, hex3, hex4, hex5, hex6, hex7, hex8, hex9, hex10, hex11, hex12,
        hex13, hex14, hex15, hex16, hex17, hex18, hex19;

    public Map map;

    /**
     * Zu Beginn müssen einige Elemente intialisiert werden
     */
    void Awake()
    {
        wheat.name = "wheat"; //Umbennung von wheat 1 in wheat
        List<Material> resources = new List<Material>()
        {
            wheat, wheat, wheat, wheat,
            wood, wood, wood, wood,
            sheep, sheep, sheep, sheep,
            brick, brick, brick,
            rock, rock, rock,
            sand
        };

        List<GameObject> hexTiles = new List<GameObject>()
        {
            hex1, hex2, hex3, hex4, hex5, hex6, hex7, hex8, hex9, hex10, hex11, hex12,
            hex13, hex14, hex15, hex16, hex17, hex18, hex19
        };

        List<Material> numbers = new List<Material>()
        {
            num5, num2, num6, num3, num8, num10, num9, num12, num11, num4, num8, num10, num9, num4, num5, num6, num3, num11
        };

        List<int> ints = new List<int>()
        {
            5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11
        };

        List<int> positions = new List<int>()
        {
            7, 3, 0, 1, 2, 6, 11, 15, 18, 17, 16, 12, 8, 4, 5, 10, 14, 13, 9
        };

        List<GameObject> tiles = new List<GameObject>();

        CreateMap(resources, numbers, tiles, positions, hexTiles, ints);
    }

    /**
     * Diese Methode erzeugt das tatsächliche Spielfeld
     */
    void CreateMap(List<Material> resources, List<Material> numbers, List<GameObject> tiles, List<int> positions, List<GameObject> hexTiles, List<int> ints)
    {
        map = new Map();

        // Alle Einzelteile des Spielfelds werden durchlaufen
        int randomResource;
        foreach (GameObject tile in hexTiles)
        {
            GameObject hexSurface = tile.transform.GetChild(0).gameObject; //Oberfläche des Sechsecks
            GameObject number = tile.transform.GetChild(1).gameObject; //Nummer auf dem Sechseck

            // Eine zufällige Oberfläche wird dem Sechseck zugewiesen.
            if (resources.Count > 1) randomResource = Random.Range(0, resources.Count);
            else randomResource = 0;

            hexSurface.GetComponent<Renderer>().sharedMaterial = resources[randomResource];

            Assets.Scripts.Model.Tile modelTile = new Assets.Scripts.Model.Tile(ints[0], (Map.Material)System.Enum.Parse(typeof(Map.Material), resources[randomResource].name, true));
            map.tiles.Add(modelTile);

            // Die vergebene Oberfläche kann nicht erneut genutzt werden
            resources.RemoveAt(randomResource);


            // Sofern das Sechseck kein Wüstenfeld ist, erhält es eine Nummer
            if (hexSurface.GetComponent<Renderer>().sharedMaterial != sand)
            {
                number.GetComponent<Renderer>().material = numbers[0];
                tile.GetComponent<Tile>().number = ints[0];
                ints.RemoveAt(0);   
                numbers.RemoveAt(0);
            }
            else
            {
                Destroy(number); //Wenn es sich um ein Sandfeld handelt, wird der Nummer-Blueprint gelöscht
            }
        }
    }
}
