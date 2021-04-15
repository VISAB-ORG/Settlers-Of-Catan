package model;

import model.Map.Material;


/**
 * Es existiert eine gleichnamige Klasse als Skript für die Felder im Spiel. Diese hier dient als Repräsentation für die KIs.
 * Sie verfügt sowohl über die Nummer, als auch über die eigene Ressource
 */
public class Tile {
	public int number;
    public String material;

    public Tile(int number, String material)
    {
        this.number = number;
        this.material = material;
    }
    
    public String Message()
    {
        String msg = "\n Number: " + number;
        msg += "\n Rohstoff: " + material;
        return msg;
    }
}
