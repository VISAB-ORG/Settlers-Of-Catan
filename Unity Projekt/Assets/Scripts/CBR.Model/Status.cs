using System.Runtime.Serialization;
using UnityEngine;
using System.Linq;
using System.Collections.Generic;
using Assets.Scripts.Model;

namespace Assets.Scripts.CBR.Model
{
    /**
     * Klasse, die den Status eines Spielers speichert
     */
    [DataContract]
    public class Status
    {
        /**
        * Der Zustand der Karte
        */
        [DataMember]
        public Map map { get; set; }

        //Alle Attribute, die zwischen KI und Spiel über die Situation des Spielers übermittelt werden müssen
        [DataMember]
        public bool isFirstTurn { get; set; }
        [DataMember]
        public bool isSecondTurn { get; set; }

        [DataMember]
        public int victoryPoints { get; set; }
        [DataMember]
        public int longestRoad { get; set; }
        [DataMember]
        public bool hasLongestRoad { get; set; }

        [DataMember]
        public int bricks { get; set; }
        [DataMember]
        public int wheat { get; set; }
        [DataMember]
        public int stone { get; set; }
        [DataMember]
        public int wood { get; set; }
        [DataMember]
        public int sheep { get; set; }

        [DataMember]
        public bool freeBuild { get; set; }
        [DataMember]
        public bool freeBuildRoad { get; set; }

        [DataMember]
        public List<GameObject> villages { get; set; }
        [DataMember]
        public List<GameObject> roads { get; set; }

        [DataMember]
        public bool isAbledToEndTurn { get; set; }
        [DataMember]
        public bool allowedToRollDice { get; set; }

        [DataMember]
        public bool villagePlacesAvailable { get; set; }
        [DataMember]
        public bool roadPlacesAvailable { get; set; }


        /**
	     * Default-Konstruktor für den Status.
	     */
        public Status() : this(new Map(), true, false, 0, 0, false, 0, 0, 0, 0, 0, true, false, new List<GameObject>(), new List<GameObject>(), false, false, false, false)
        {

        }

        /**
         * Konstruktor, wenn der Status des Spielers aus dem Spiel abgeleitet werden kann
         */
        public Status(Map map, bool isFirstTurn, bool isSecondTurn, int victoryPoints, int longestRoad, bool hasLongestRoad, int bricks, int wheat, int stone, int wood, int sheep, 
            bool freeBuild, bool freeBuildRoad, List<GameObject> villages, List<GameObject> roads, bool isAbledToEndTurn, bool allowedToRollDice, bool villagePlacesAvailable, bool roadPlacesAvailable)
        {
            this.map = map;
            this.isFirstTurn = isFirstTurn;
            this.isSecondTurn = isSecondTurn;
            this.victoryPoints = victoryPoints;
            this.longestRoad = longestRoad;
            this.hasLongestRoad = hasLongestRoad;
            this.bricks = bricks;
            this.wheat = wheat;
            this.stone = stone;
            this.wood = wood;
            this.sheep = sheep;
            this.freeBuild = freeBuild;
            this.freeBuildRoad = freeBuildRoad;
            this.villages = villages;
            this.roads = roads;
            this.isAbledToEndTurn = isAbledToEndTurn;
            this.allowedToRollDice = allowedToRollDice;
            this.villagePlacesAvailable = villagePlacesAvailable;
            this.roadPlacesAvailable = roadPlacesAvailable;
        }

        public override string ToString()
        {
            return "Status [Map: " + map.ToString() + " isFirstTurn=" + isFirstTurn + ", isSecondTurn=" + isSecondTurn + ", victoryPoints="
                + victoryPoints + ", longestRoad=" + longestRoad + ", hasLongestRoad=" + hasLongestRoad + ", bricks="
                + bricks + ", wheat=" + wheat + ", stone=" + stone
                + ", wood=" + wood + ", sheep=" + sheep
                + ", freeBuild=" + freeBuild + ", freeBuildRoad=" + freeBuildRoad + ", villages=" + villages.Count
                + ", roads=" + roads.Count + ", isAbledToEndTurn=" + isAbledToEndTurn
                + ", allowedToRollDice=" + allowedToRollDice
                + ", villagePlacesAvailable=" + villagePlacesAvailable
                + ", roadPlacesAvailable=" + roadPlacesAvailable + "]";
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

    }
}
