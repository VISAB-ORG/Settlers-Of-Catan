using Assets.Scripts.Model;
using System.Runtime.Serialization;
namespace Assets.Scripts.CBR.Plan
{
    /**
     * Klasse, die die Aktion das Bauen von Siedlungen darstellt
     */
    [DataContract]
    public class BuildVillage : Action
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


        public BuildVillage(int row, int column) : base("BuildVillage")
        {
            this.row = row;
            this.column = column;
        }

        public BuildVillage() : base("BuildVillage")
        {
            
        }
    }
}
