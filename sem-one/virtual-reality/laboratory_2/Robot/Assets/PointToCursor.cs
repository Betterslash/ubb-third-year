using System;
using UnityEngine;

public class PointToCursor : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
    }

    void Update ()
    {
        if (Camera.main is null) return;
        var positionOnScreen = Camera.main.WorldToViewportPoint(transform.position);
        var mouseOnScreen = (Vector2)Camera.main.ScreenToViewportPoint(Input.mousePosition);
        var angle = AngleBetweenTwoPoints(positionOnScreen, mouseOnScreen);
        transform.rotation = Quaternion.Euler(new Vector3(0f,angle,0f));
    }

    private static float AngleBetweenTwoPoints(Vector3 a, Vector3 b) {
        return Mathf.Atan2(a.y - b.y, a.x - b.x) * Mathf.Rad2Deg;
    }
}
