using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DrawProjection : MonoBehaviour
{
    public CanonController CanonController;

    public LineRenderer LineRenderer;
    // Start is called before the first frame update
    void Start()
    {
        CanonController = GetComponent<CanonController>();
        LineRenderer = GetComponent<LineRenderer>();
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
