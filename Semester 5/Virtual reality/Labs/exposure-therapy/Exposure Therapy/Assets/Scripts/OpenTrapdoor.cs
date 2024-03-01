using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class OpenTrapdoor : MonoBehaviour
{
    public GameObject trapdoor;
    public GameObject button1;
    public GameObject button2;
    public GameObject button3;
    public float newX;
    public float newY;
    public Text scoreText;
    public int score;

    void OnTriggerEnter(Collider other)
    {
        if (other.CompareTag("Player"))
        {
            int currentScore = int.Parse(scoreText.text);
            int newScore = currentScore + score;
            scoreText.text = newScore.ToString();

            trapdoor.transform.Rotate(0f, 0f, -90f, Space.World);
            trapdoor.transform.position = trapdoor.transform.position + new Vector3(newX, newY, 0f);
            Destroy(button1);
            Destroy(button2);
            Destroy(button3);
        }
    }

}
