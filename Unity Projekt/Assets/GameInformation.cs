using Assets.Scripts.Model;
using System.Collections.Generic;
using UnityEngine;
using static GameManager;

/// <summary>
/// Class keeping information of the current state of the game
/// </summary>
public class GameInformation
{
    /// <summary>
    /// The turn counter
    /// </summary>
    public int TurnCounter { get; set; }

    /// <summary>
    /// Get the time stamp of a turn that ended
    /// </summary>
    public string TurnTimeStamp { get; set; }

    public IList<PlayerScript> Players { get; set; } = new List<PlayerScript>();

    public float RoadRange { get; set; }

    public PlayerScript ActivePlayer { get; set; }
    public int DiceNumberRolled { get; set; }
}