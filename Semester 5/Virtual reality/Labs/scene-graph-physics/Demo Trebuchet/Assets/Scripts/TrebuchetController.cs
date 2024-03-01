using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrebuchetController : MonoBehaviour
{
    public Rigidbody weight;
    public GameObject ball;
    public Rigidbody slingArm;

    // Start is called before the first frame update
    void Start()
    {
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Space))
        {
            weight.isKinematic = false;
        }

        if (weight.isKinematic == false)
        {
            Vector3 pos = slingArm.position;

            if (pos.y >= 12.0f && pos.z >= -3.1f && pos.y <= 14f && pos.z <= 1.1f)
            {
                HingeJoint hingeToDestroy;
                hingeToDestroy = ball.GetComponent<HingeJoint>();
                Destroy(hingeToDestroy);
            }
        }
    }
}
