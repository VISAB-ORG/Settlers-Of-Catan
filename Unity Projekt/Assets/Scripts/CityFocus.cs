using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/*
 * Klasse zur Aktivierung des Baumodus für Siedlungen 
 */
public class CityFocus : MonoBehaviour
{
    public  bool hasFocus = false;
    public RoadFocus roadFocus;
    public VillageFocus villageFocus;

    /*
     * Wenn ein Klick auf das entsprechende GameObject ausgeführt wird, wird der Baumodus aktiviert bzw. deaktiviert
     */
    public void OnMouseDown()
    {
        hasFocus = !hasFocus;
        if (hasFocus)
        {
            roadFocus.hasFocus = false;
            villageFocus.hasFocus = false;
        }
    }
}
