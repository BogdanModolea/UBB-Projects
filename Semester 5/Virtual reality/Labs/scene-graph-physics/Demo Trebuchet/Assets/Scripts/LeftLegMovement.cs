using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LegMovement : MonoBehaviour
{
    private float currentAngle = -45f;
    private bool change = false;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if(change)
	{
	    if(currentAngle <= 45f)
	    {
		transform.Rotate(1, 0, 0);
		currentAngle += 1;
	    }
	    else
	    {
		change = false;
	    }
	}
	else
	{
	    if(currentAngle >= -45f)
	    {
		transform.Rotate(-1, 0, 0);
		currentAngle -= 1;
	    }
	    else
	    {
		change = true;
	    }
	}
    }
}
