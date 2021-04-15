using System.Runtime.Serialization;

namespace Assets.Scripts.Model
{
    /*
     * Es existiert eine gleichnamige Klasse als Skript für die Felder im Spiel. Diese hier dient als Repräsentation für die KIs.
     * Sie verfügt sowohl über die Nummer, als auch über die eigene Ressource
     */
    [DataContract]
    public class Tile
    {
        [DataMember]
        public string material { get; set; }
        [DataMember]
        public int number { get; set; }

        public Tile(int number, Map.Material material)
        {
            this.number = number;
            this.material = material.ToString();
        }

        public string Message()
        {
            string msg = "\n Number: " + number;
            msg += "\n Rohstoff: " + material;
            return msg;
        }
    }
}
