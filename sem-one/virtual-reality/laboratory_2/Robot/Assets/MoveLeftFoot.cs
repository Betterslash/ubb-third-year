using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MoveLeftFoot : MonoBehaviour
{
    private float _v = 20.0f;
    private float _x = 0.0f;
    private const float MAX = 60.0f;
    private const float MIN = 0.0f;
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        var dt = Time.deltaTime;
        _x += _v * dt;
        if (_x > MAX)
        {
            _v *= -1;
            _x -= _x - MAX;
        }

        if (_x < MIN)
        {
            _v *= -1;
            _x -= _x - MIN;
        }
        transform.rotation = Quaternion.AngleAxis(_x, new Vector3(0,0,-1));
    }
}
