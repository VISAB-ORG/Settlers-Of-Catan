using Assets.Scripts.VISAB.Model;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;
using UnityEngine.SceneManagement;
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
                    Y = (int)position.z
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
                MapRectangle = GetMapRectangle(),
                PlayerInformation = playerInformation
            };

            //Debug.Log(JsonConvert.SerializeObject(metaInformation));
            return metaInformation;
        }

        public static VISABImageContainer MakeSnapshots()
        {
            Func<string, SnapshotConfiguration> defaultInstantiate = (prefabPath) => new SnapshotConfiguration
            {
                ImageHeight = 1024,
                ImageWidth = 1024,
                CameraOffset = 2f,
                Orthographic = true,
                InstantiationSettings = new InstantiationConfiguration
                {
                    PrefabPath = prefabPath,
                    SpawnLocation = new Vector3(100, 100, 100),
                    SpawnRotation = Quaternion.identity
                }
            };

            Func<string, SnapshotConfiguration> defaultExisting = (gameId) => new SnapshotConfiguration
            {
                ImageHeight = 1024,
                ImageWidth = 1024,
                CameraOffset = 2f,
                Orthographic = true,
                GameObjectId = gameId
            };

            var prefabPaths = new Dictionary<string, string>
            {
                { "WeaponCrate", "Prefabs/WeaponsCrate/WeaponsCrate" },
                { "M4a1", "Prefabs/M4A1_Collectable" },
                { "Health", "Prefabs/Health" }
            };

            var existingIds = new Dictionary<string, string>
            {
                { "John Doe", "John Doe" },
                { "Jane Doe", "Jane Doe" }
            };

            var images = new VISABImageContainer();

            foreach (var pair in prefabPaths)
            {
                var config = defaultInstantiate(pair.Value);
                var bytes = ImageCreator.TakeSnapshot(config);

                images.StaticObjects.Add(pair.Key, bytes);
            }

            foreach (var pair in existingIds)
            {
                var config = defaultExisting(pair.Value);
                var bytes = ImageCreator.TakeSnapshot(config);

                images.MoveableObjects.Add(pair.Key, bytes);
            }

            Debug.Log(JsonConvert.SerializeObject(images));

            return images;
        }

        public static MapRectangle GetMapRectangle()
        {
            // get root objects in scene
            List<GameObject> rootObjects = new List<GameObject>();
            Scene scene = SceneManager.GetActiveScene();
            scene.GetRootGameObjects(rootObjects);

            var map = GameObject.Find("Map");

            var bounds = map.GetBoundsWithChildren();
            var anchorPoint = new Model.Vector2 { X = (int)bounds.min.x, Y = (int)bounds.min.z };

            return new MapRectangle
            {
                Height = (int)bounds.size.z,
                Width = (int)bounds.size.x,
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