using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HeadMovement : MonoBehaviour
{
    private float angle;
    private float velocity = 20.0f;

    // Start is called before the first frame update
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {
	var dt = Time.deltaTime;
        var deltaAngle = velocity * dt;

        if (Mathf.Abs(angle + deltaAngle) > 15.0f)
        {
            velocity *= -1.0f;
            deltaAngle *= -1.0f;
        }
        angle += deltaAngle;


        transform.Rotate(Vector3.right, deltaAngle);
    }
}
