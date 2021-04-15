using System.Collections.Generic;
using System.Runtime.Serialization;
using UnityEngine;

namespace Assets.Scripts.Model
{
    /**
     * Klasse zur abstrakten Speicherung eines Siedlungsbauplatz
     */
    [DataContract]
    public class CityBuildPlace
    {
        public GameObject gameObject;

        /**
         * Ein Bauplatz kann aktiv oder nicht aktiv sein
         */
        [DataMember]
        public bool isActive { get; set; }

        /**
         * Zeile des Platzes auf dem Spielfeld
         */
        [DataMember]
        public int row { get; set; }

        /**
         * Spalte des Platzes auf dem Spielfeld
         */
        [DataMember]
        public int column { get; set; }

        [DataMember]
        public List<string> resources { get; set; }

        /**
         * Default-Konstruktor, Zu Beginn ist der Bauplatz deaktiviert
         */
        public CityBuildPlace(GameObject gm, int row, int column)
        {
            isActive = false;
            gameObject = gm;
            this.row = row;
            this.column = column;
            resources = new List<string>();
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

        public override string ToString()
        {
            string msg = "\n isActive: " + isActive;
            return msg;
        }
    }
}
