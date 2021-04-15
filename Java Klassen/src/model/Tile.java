package model;

import model.Map.Material;


/**
 * Es existiert eine gleichnamige Klasse als Skript f�r die Felder im Spiel. Diese hier dient als Repr�sentation f�r die KIs.
 * Sie verf�gt sowohl �ber die Nummer, als auch �ber die eigene Ressource
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
