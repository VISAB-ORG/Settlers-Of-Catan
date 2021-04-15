using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.Video;

/**
 * Klasse, die das Menü verwaltet
 */
public class MainMenu : MonoBehaviour
{
    private int player1Ai = 1;
    private int player2Ai = 1;

    void OnDisable()
    {
        PlayerPrefs.SetInt("player1Ai", player1Ai);
        PlayerPrefs.SetInt("player2Ai", player2Ai);
    }
    public void OnclickStartGame()
    {
        SceneManager.LoadScene(1);
    }

    public void TogglePlayer1Ai()
    {
        if (player1Ai == 0)
        {
            player1Ai = 1;
        } else
        {
            player1Ai = 0;
        }
    }

    public void TogglePlayer2Ai()
    {
        if (player2Ai == 0)
        {
            player2Ai = 1;
        }
        else
        {
            player2Ai = 0;
        }
    }
}
