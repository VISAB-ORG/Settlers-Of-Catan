using Assets.Scripts.Model;
using System.Runtime.Serialization;

namespace Assets.Scripts.CBR.Plan
{        
    
    /**
    * Klasse, die die Aktion das Bauen von Straßen darstellt
    */
    [DataContract]
    public class BuildCity : Action
    {

        /**
        * Attribut zur Speicherung, in welcher Zeile gebaut werden soll 
        */
        [DataMember]
        public int row;

        /**
         * Attribut zur Speicherung, in welcher Spalte gebaut werden soll 
         */
        [DataMember]
        public int column;

        public BuildCity(int row, int column) : base("BuildCity")
        {
            this.row = row;
            this.column = column;
        }


        public BuildCity() : base("BuildCity")
        {
        }
    }
}
