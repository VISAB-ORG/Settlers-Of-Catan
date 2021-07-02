using System.Collections.Generic;

namespace Assets.Scripts.VISAB.Model
{
    public class PlayerInformation
    {
        public int VillageCount => VillagePositions.Count;

        public IList<Vector2> VillagePositions { get; set; } = new List<Vector2>();

        public int StreetCount => StreetPositions.Count;

        public IList<Vector2> StreetPositions { get; set; } = new List<Vector2>();

        public int CityCount => CityPositions.Count;

        public IList<Vector2> CityPositions { get; set; } = new List<Vector2>();

        public PlayerResources Resources { get; set; }

        public PlayerResources ResourcesGained { get; set; }

        public bool HasLongestRoad { get; set; }

        public bool IsAi { get; set; }

        public int LongestRoad { get; set; }

        public string Name { get; set; }

        public IList<string> PlanActions { get; set; }

        public int VictoryPoints { get; set; }

        public bool IsMyTurn { get; set; }
    }
}