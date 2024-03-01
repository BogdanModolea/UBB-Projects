using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Crouch : MonoBehaviour
{
    private Vector3 crawlScale = new Vector3(0.6f, 0.22f, 0.6f);
    private Vector3 crouchScale = new Vector3(0.6f, 0.7f, 0.6f);
    private Vector3 playerScale = new Vector3(0.6f, 1f, 0.6f);

    bool firstPress = false;
    bool secondPress = false;
    bool thirdPress = false;

    private string status = "stand";

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if(status == "stand")
        {
            if (Input.GetKeyDown(KeyCode.LeftControl))
            {
                transform.localScale = crouchScale;
                transform.position = new Vector3(transform.position.x, transform.position.y - 0.1f, transform.position.z);
                status = "crouch";
            }
        }
        else if(status == "crouch")
        {
            if (Input.GetKeyDown(KeyCode.LeftControl))
            {
                transform.localScale = crawlScale;
                transform.position = new Vector3(transform.position.x, transform.position.y - 0.1f, transform.position.z);
                status = "crawl";
            }
            else if(Input.GetKeyDown(KeyCode.LeftShift))
            {
                transform.localScale = playerScale;
                transform.position = new Vector3(transform.position.x, transform.position.y + 0.5f, transform.position.z);
                status = "stand";
            }
        }
        else
        {
            if (Input.GetKeyDown(KeyCode.LeftShift))
            {
                transform.localScale = crouchScale;
                transform.position = new Vector3(transform.position.x, transform.position.y + 0.5f, transform.position.z);
                status = "crouch";
            }
        }
    }
}
