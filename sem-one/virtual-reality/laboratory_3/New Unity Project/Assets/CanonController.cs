using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CanonController : MonoBehaviour
{
    public float rotationSpeed = 1;

    public float blastPower = 5;

    public GameObject canonBall;

    public Transform shotPoint;

    public GameObject explosion;
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        var horizontalRotation = Input.GetAxis("Horizontal");
        var verticalRotation = Input.GetAxis("Vertical");
        transform.rotation = Quaternion.Euler(transform.rotation.eulerAngles +
                                              new Vector3(0, horizontalRotation * rotationSpeed, verticalRotation * rotationSpeed));
        if (!Input.GetKeyDown(KeyCode.Space)) return;
        var createdCanonBall = Instantiate(canonBall, shotPoint.position, shotPoint.rotation);
        createdCanonBall.GetComponent<Rigidbody>().velocity = shotPoint.transform.forward * -blastPower;
    }
}
