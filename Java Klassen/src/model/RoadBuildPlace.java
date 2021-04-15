package model;


/**
 * Klasse zur abstrakten Speicherung eines Siedlungsbauplatz
 * 
 * @author Tjark Harjes
 */
public class RoadBuildPlace {
	
    /**
     * Ein Bauplatz kann aktiv oder nicht aktiv sein
     */
    public boolean isActive;
	public int row;
	public int column;

    
    /**
     * Default-Konstruktor, Zu Beginn ist der Bauplatz deaktiviert
     */
    public RoadBuildPlace()
    {
        isActive = false;
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
