using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using VISABConnector;
using UnityEngine;
using static GameManager;

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
            return new VISABStatistics()
            {

                Turn = gameInformation.TurnCounter,
                TurnTimeStamp = gameInformation.TurnTimeStamp,
                Player1 = ExtractPlayerInformation(gameInformation.Player1),
                Player2 = ExtractPlayerInformation(gameInformation.Player2)
            };
        }

        /// <summary>
        /// Helper method to extract information from a PlayerScript into a VISAB-conform object.
        /// </summary>
        private static PlayerInformation ExtractPlayerInformation(PlayerScript player)
        {
            return new PlayerInformation
            {
                Brick = player.brick,
                FreeBuild = player.freeBuild,
                FreeBuildRoad = player.freeBuildRoad,
                HasLongestRoad = player.hasLongestRoad,
                IsAi = player.isAI,
                LongestRoad = player.longestRoad,
                Name = player.name,
                Plan = "", // TODO: What do we do here?
                RoadRange = player.roadRange,
                Sheep = player.sheep,
                Stone = player.stone,
                VictoryPoints = player.victoryPoints,
                Wheat = player.wheat,
                Wood = player.wood
            };
        }

    }
}