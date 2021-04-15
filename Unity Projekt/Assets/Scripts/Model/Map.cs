using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Runtime.Serialization;

namespace Assets.Scripts.Model
{

    /**
     * Klasse, die die Karte als Repräsentation zur Übermittelung an die KIs enthält
     */
    [DataContract]
    public class Map
    {
        /**
         * Die Namen der möglichen Felder
         */
        public enum Material
        {
            brick,
            wheat,
            rock,
            wood,
            sheep,
            sand,
            ocean
        }
        /**
         * Die Felder
         */
        [DataMember]
        public List<Tile> tiles;

        /**
         * Die Bauplätze für Siedlungen
         */
        [DataMember]
        public List<VillageBuildPlace> villageBuildPlaces;

        /**
         * Die Bauplätze für Straßen
         */
        [DataMember]
        public List<RoadBuildPlace> roadBuildPlaces;

        /**
         * Die Bauplätze für Städte
         */
        [DataMember]
        public List<CityBuildPlace> cityBuildPlaces;


        /**
         * Default Konstruktor
         */
        public Map()
        {
            tiles = new List<Tile>();
            villageBuildPlaces = new List<VillageBuildPlace>();
            roadBuildPlaces = new List<RoadBuildPlace>();
            cityBuildPlaces = new List<CityBuildPlace>();
        }

        public VillageBuildPlace getVillagePlaceByPosition(int row, int column)
        {
            foreach (VillageBuildPlace villagePlace in villageBuildPlaces)
            {
                if (villagePlace.row == row && villagePlace.column == column)
                {
                    return villagePlace;
                }
            }
            return null;
        }

        public RoadBuildPlace getRoadPlaceByPosition(int row, int column)
        {
            foreach (RoadBuildPlace roadPlace in roadBuildPlaces)
            {
                if (roadPlace.row == row && roadPlace.column == column)
                {
                    return roadPlace;
                }
            }
            return null;
        }

        public CityBuildPlace getCityPlaceByPosition(int row, int column)
        {
            foreach (CityBuildPlace cityPlace in cityBuildPlaces)
            {
                if (cityPlace.row == row && cityPlace.column == column)
                {
                    return cityPlace;
                }
            }
            return null;
        }

        public override string ToString()
        {
            string toString = "Tiles: \n";
            foreach(Tile tile in tiles)
            {
                toString += tile.Message();
            }
            /*toString += "Build places Village: ";
            foreach (VillageBuildPlace place in villageBuildPlaces)
            {
                toString += place.ToString();
            }
            toString += "Build places Road: ";
            foreach (RoadBuildPlace place in roadBuildPlaces)
            {
                toString += place.ToString();
            }*/
            return toString;
        }
    }
}
