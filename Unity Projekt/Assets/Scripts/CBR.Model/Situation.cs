using System.Runtime.Serialization;
using Assets.Scripts.Model;

namespace Assets.Scripts.CBR.Model
{
    /**
     * Klasse zur Darstellung der Situation, die an die KIs übermittelt wird
     */
    [DataContract]
    public class Situation
    {

         /**
         * Der Spieler, der Situation
         */
        [DataMember]
        public string player { get; set; }

        /**
         * Der Status des entsprechenden Spielers
         */
        [DataMember]
        public Status playerStatus { get; set; }

        /**
         * Default-Konstruktor
         */
        public Situation() : this("", new Status())
        {

        }

        /**
         * Konstruktor, der die Map, den Namen des Spielers und seinen Status enthält
         */
        public Situation(string player, Status playerStatus)
        {
            this.player = player;
            this.playerStatus = playerStatus;
        }

        public override string ToString()
        {
            return "Situation [Player=" + player + ", playerStatus=" + playerStatus.ToString() + "]";
        }

    }
}
