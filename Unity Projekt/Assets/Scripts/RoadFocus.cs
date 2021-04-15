using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/*
 * Klasse zur Aktivierung des Baumodus für Straßen 
 */
public class RoadFocus : MonoBehaviour
{
    public bool hasFocus = false;
    public VillageFocus villageFocus;
    public CityFocus cityFocus;

    /*
    * Wenn ein Klick auf das entsprechende GameObject ausgeführt wird, wird der Baumodus aktiviert bzw. deaktiviert
    */
    public void OnMouseDown()
    {
        hasFocus = !hasFocus;
        if (hasFocus)
        {
            villageFocus.hasFocus = false;
            cityFocus.hasFocus = false;
        }
    }
}
