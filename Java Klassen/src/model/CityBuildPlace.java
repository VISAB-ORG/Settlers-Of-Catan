package model;

import java.util.ArrayList;

/**
 * Klasse zur abstrakten Speicherung eines Städtebauplatzes
 * 
 * @author Tjark Harjes
 */
public class CityBuildPlace {
	
    /**
     * Ein Bauplatz kann aktiv oder nicht aktiv sein
     */
    public boolean isActive;
	public int row;
	public int column;
	public ArrayList<String> resources;

    /**
     * Default-Konstruktor, Zu Beginn ist der Bauplatz deaktiviert
     */
    public CityBuildPlace(int row, int column, ArrayList<String> resources)
    {
        isActive = false;
        this.resources = resources; 
    }

    /**
     * Methode, die das Deaktivieren eines Bauplatzes anschaulicher macht
     */
    public void Deactivate()
    {
        isActive = false;
    }

    /**
     * Methode, die das Aktivieren eines Bauplatzes anschaulicher macht
     */
    public void Activate()
    {
        isActive = true;
    }
    
    @Override
    public String toString()
    {
        String msg = "\n isActive: " + isActive;
        return msg;
    }

}
