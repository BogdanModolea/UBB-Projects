using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class OpenDoor : MonoBehaviour
{
    public GameObject door;
    public GameObject button1;
    public GameObject button2;
    public GameObject button3;
    public Text scoreText;
    public int score;

    void OnTriggerEnter(Collider other)
    {
        if (other.CompareTag("Player"))
        {
            int currentScore = int.Parse(scoreText.text);
            int newScore = currentScore + score;
            scoreText.text = newScore.ToString();

            door.transform.Rotate(0f, 90f, 0f, Space.World);
            Destroy(button1);
            Destroy(button2);
            Destroy(button3);
        }
    }

}
