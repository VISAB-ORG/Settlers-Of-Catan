using Assets.Scripts.VISAB.Model;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;
using VISABConnector;

namespace Assets.Scripts.VISAB
{
    public static class VISABHelper
    {
        public static string HostAdress { get; set; }

        public static int Port { get; set; }

        public static int RequestTimeout { get; set; }

        /// <summary>
        /// Retrieve current game information and return them as VISABStatistics.
        /// </summary>
        public static VISABStatistics GetStatistics()
        {
            var gameInformation = GameManager.GameInformation;
            var statistics = new VISABStatistics()
            {
                Players = gameInformation.Players.Select(x => ExtractPlayerInformation(x, gameInformation)).ToList(),
                Turn = gameInformation.TurnCounter,
                TurnTimeStamp = gameInformation.TurnTimeStamp,
                DiceNumberRolled = gameInformation.DiceNumberRolled
            };
            Debug.Log(JsonConvert.SerializeObject(statistics));
            return statistics;
        }

        /// <summary>
        /// Helper method to extract information from a PlayerScript into a VISAB-conform object.
        /// </summary>
        private static PlayerInformation ExtractPlayerInformation(PlayerScript player, GameInformation gameInformation)
        {
            return new PlayerInformation
            {
                Resources = new PlayerResources
                {
                    Brick = player.brick,
                    Sheep = player.sheep,
                    Stone = player.stone,
                    Wheat = player.wheat,
                    Wood = player.wood
                },
                HasLongestRoad = player.hasLongestRoad,
                IsAi = player.isAI,
                LongestRoad = player.longestRoad,
                Name = player.name,
                PlanActions = player.CurrentPlanActions,
                CityPositions = ExtractPositions(player.cities),
                StreetPositions = ExtractPositions(player.roads),
                VillagePositions = ExtractPositions(player.villages),
                VictoryPoints = player.victoryPoints,
                IsMyTurn = gameInformation.ActivePlayer == player,
                ResourcesGained = player.GainedResources,
                VillageResourcesGained = player.VillageGainedResources
            };
        }

        private static IList<Model.Vector2> ExtractPositions(IEnumerable<GameObject> gameObjects)
        {
            var list = new List<Model.Vector2>();
            foreach (var gameObject in gameObjects)
            {
                var position = gameObject.transform.position;
                var vector2Pos = new Model.Vector2
                {
                    X = (int)position.x,
                    Y = (int)position.y
                };
                list.Add(vector2Pos);
            }

            return list;
        }

        public static IMetaInformation GetMetaInformation()
        {
            var gameInformation = GameManager.GameInformation;

            var playerInformation = new Dictionary<string, string>();
            foreach (var player in gameInformation.Players)
            {
                if (player.isAI)
                    playerInformation[player.name] = "script";
                else
                    playerInformation[player.name] = "human";
            }

            var metaInformation = new VISABMetaInformation
            {
                MapRectangle = null, // TODO
                PlayerInformation = playerInformation
            };

            //Debug.Log(JsonConvert.SerializeObject(metaInformation));
            return metaInformation;
        }
    }
}