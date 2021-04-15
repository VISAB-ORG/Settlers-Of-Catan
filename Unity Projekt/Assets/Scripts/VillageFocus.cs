using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/*
 * Klasse zur Aktivierung des Baumodus für Siedlungen 
 */
public class VillageFocus : MonoBehaviour
{
    public  bool hasFocus = false;
    public RoadFocus roadFocus;
    public CityFocus cityFocus;

    /*
     * Wenn ein Klick auf das entsprechende GameObject ausgeführt wird, wird der Baumodus aktiviert bzw. deaktiviert
     */
    public void OnMouseDown()
    {
        hasFocus = !hasFocus;
        if (hasFocus)
        {
            roadFocus.hasFocus = false;
            cityFocus.hasFocus = false;
        }
    }
}
