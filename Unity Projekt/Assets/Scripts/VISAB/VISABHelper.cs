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
        private static IVISABSession session;

        public static VISABStatistics GetStatistics()
        {
            // var gameInformation = GameManager.GameInformation;
            return null; 
            //    new VISABStatistics
            // {
            //    Turn = gameInformation.TurnCounter,
            //    TurnTimeStamp = gameInformation.TurnTimeStamp,
            //    Player1 = ExtractPlayerInformation(gameInformation.Player1),
            //    Player2 = ExtractPlayerInformation(gameInformation.Player2)
            // };
        }

        /// <summary>
        /// This method is called by the GameManager whenever information changes.
        /// </summary>
        public static async void PushStatistics()
        {
            VISABStatistics statistics = GetStatistics();
            var response = await session.SendStatistics(statistics);
            if (response.IsSuccess)
                Debug.Log($"Send statistics to VISAB! Turn:{statistics.Turn}, Time: {statistics.TurnTimeStamp}");
        }

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


        /// <summary>
        /// Initiates the infinite loop that sends information to the VISAB api. The session is stopped
        /// if the given cancellationToken is canceled.
        /// </summary>
        /// <param name="cancellationToken">The cancellationToken</param>
        /// <returns>An awaitable Task</returns>
        public static async Task StartVISABSession(CancellationToken cancellationToken)
        {
            // Initializes the VISAB transmission session
            Debug.Log("Starting to initalize Session with VISAB WebApi.");
            session = await VISABApi.InitiateSession("Settlers");
            if (session == default)
            {
                // TODO: Start VISAB
                while (session == default && !cancellationToken.IsCancellationRequested)
                {
                    Debug.Log("Couldent initialize VISAB api session!");
                    session = await VISABApi.InitiateSession("Settlers");
                }
            }
            Debug.Log($"Initialized Session with VISAB WebApi! SessionId:{session.SessionId}");

            if (cancellationToken.IsCancellationRequested == true)
            {
                // Close the VISAB api session
                Debug.Log($"Closing VISAB WebApi session! SessionId:{session.SessionId}");
                await session.CloseSession();
                Debug.Log($"Closed session!");
            }
        }

        public static IVISABSession GetSession()
        {
            return session;
        }

        public async static void CloseVISABSession()
        {
            // Close the VISAB api session
            Debug.Log($"Closing VISAB WebApi session! SessionId:{session.SessionId}");
            await session.CloseSession();
            Debug.Log($"Closed session!");
        }
    }
}