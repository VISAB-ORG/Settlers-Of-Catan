using Assets.Scripts.Model;
using System.Runtime.Serialization;
namespace Assets.Scripts.CBR.Plan
{
    /**
     * Klasse, die die Aktion das Bauen von Straßen darstellt
     */
    [DataContract]
    public class BuildRoad : Action
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


        public BuildRoad(int row, int column) : base("BuildRoad")
        {
            this.row = row;
            this.column = column;
        }


        public BuildRoad() : base("BuildRoad")
        {
        }
    }
}
