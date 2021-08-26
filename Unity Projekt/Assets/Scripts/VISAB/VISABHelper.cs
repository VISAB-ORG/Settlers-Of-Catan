using Assets.Scripts.VISAB.Model;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using UnityEngine;
using VISABConnector;
using VISABConnector.Unity;

namespace Assets.Scripts.VISAB
{
    public static class VISABHelper
    {
        public static string HostAdress { get; set; } = "http://localhost";

        public static int Port { get; set; } = 2673;

        public static int RequestTimeout { get; set; } = 2;

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
                    X = (double)position.x,
                    Y = (double)position.z
                };
                list.Add(vector2Pos);
            }

            return list;
        }

        public static IMetaInformation GetMetaInformation()
        {
            var gameInformation = GameManager.GameInformation;

            var playerInformation = new Dictionary<string, string>();
            var playerColors = new Dictionary<string, string>
            {
                { "Player1", "#FF851B" },
                { "Player2", "#B10DC9" }
            };

            foreach (var player in gameInformation.Players)
            {
                if (player.isAI)
                    playerInformation[player.name] = "script";
                else
                    playerInformation[player.name] = "human";
            }

            var metaInformation = new VISABMetaInformation
            {
                MapRectangle = GetMapRectangle(),
                PlayerInformation = playerInformation,
                PlayerColors = playerColors
            };

            //Debug.Log(JsonConvert.SerializeObject(metaInformation));
            return metaInformation;
        }

        public static VISABImageContainer MakeSnapshots()
        {
            static SnapshotConfiguration defaultInstantiate(string prefabPath) => new SnapshotConfiguration
            {
                ImageHeight = 1024,
                ImageWidth = 1024,
                InstantiationSettings = new InstantiationConfiguration
                {
                    SpawnLocation = new Vector3(100, 100, 100),
                    PrefabPath = prefabPath,
                    SpawnRotation = new Vector3(0, 0, 0)
                },
                CameraConfiguration = new CameraConfiguration
                {
                    CameraOffset = 1f,
                    Orthographic = false,
                    CameraRotation = new Vector3(90, 0, 0)
                }
            };

            var mapConfig = new SnapshotConfiguration
            {
                CameraConfiguration = new CameraConfiguration
                {
                    CameraOffset = 0.1f,
                    Orthographic = true,
                    OrthographicSize = 5,
                    CameraRotation = new Vector3(90, 180, 0)
                },
                GameObjectId = "Map",
                ImageHeight = 1024,
                ImageWidth = 1024,
            };

            var map = ImageCreator.TakeSnapshot(mapConfig);

            var cityConfig = defaultInstantiate("Prefabs/City");
            cityConfig.CameraConfiguration.CameraOffset = 1.5f;
            var city = ImageCreator.TakeSnapshot(cityConfig);

            var streetConfig = defaultInstantiate("Prefabs/Road");
            streetConfig.CameraConfiguration.CameraOffset = 2f;
            var street = ImageCreator.TakeSnapshot(streetConfig);

            var villageConfig = defaultInstantiate("Prefabs/Village");
            villageConfig.CameraConfiguration.CameraOffset = 1.75f;
            var village = ImageCreator.TakeSnapshot(villageConfig);

            var images = new VISABImageContainer
            {
                CityImage = city,
                CityAnnotation = "C",
                StreetImage = street,
                StreetAnnotation = "S",
                VillageImage = village,
                VillageAnnotation = "V",
                MapImage = map
            };

#if UNITY_EDITOR
            File.WriteAllBytes("Snapshots/map.png", map);
            File.WriteAllBytes("Snapshots/" + DateTime.Now.ToString("yyyy-dd-M--HH-mm-ss" + "city") + ".png", city);
            File.WriteAllBytes("Snapshots/" + DateTime.Now.ToString("yyyy-dd-M--HH-mm-ss" + "street") + ".png", street);
            File.WriteAllBytes("Snapshots/" + DateTime.Now.ToString("yyyy-dd-M--HH-mm-ss" + "village") + ".png", village);
#endif

            return images;
        }

        public static MapRectangle GetMapRectangle()
        {
            var map = GameObject.Find("Map");

            var bounds = map.GetBoundsWithChildren();
            var anchorPoint = new Model.Vector2 { X = (double)bounds.min.x, Y = (double)bounds.min.z };

            return new MapRectangle
            {
                Height = (double)bounds.size.z,
                Width = (double)bounds.size.x,
                TopLeftAnchorPoint = anchorPoint
            };
        }

        public static Bounds GetBoundsWithChildren(this GameObject gameObject)
        {
            Renderer[] renderers = gameObject.GetComponentsInChildren<Renderer>();

            Bounds bounds = renderers.Length > 0 ? renderers[0].bounds : new Bounds();

            for (int i = 1; i < renderers.Length; i++)
            {
                if (renderers[i])
                {
                    bounds.Encapsulate(renderers[i].bounds);
                }
            }

            return bounds;
        }
    }
}