using Assets.Scripts.Model;
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

    /// <summary>
    /// The player object controlled by script or human
    /// </summary>
    public PlayerScript Player1 { get; set; }

    /// <summary>
    /// The player object controlled by script or human
    /// </summary>
    public PlayerScript Player2 { get; set; }

}