using System;
using UnityEngine;

public class PointToCursor : MonoBehaviour
{
    private Camera _cam;
    // Start is called before the first frame update
    void Start()
    {
        _cam = Camera.main;
    }

    void Update ()
    {
        var mouseOnScreen = _cam.ScreenToWorldPoint(new Vector3(Input.mousePosition.x, Input.mousePosition.y, 5));
        var rotation = transform.position - mouseOnScreen;
        transform.rotation = Quaternion.LookRotation(rotation);
    }

    private static float AngleBetweenTwoPoints(Vector3 a, Vector3 b) {
        return Mathf.Atan2(a.y - b.y, a.x - b.x) * Mathf.Rad2Deg;
    }
}
