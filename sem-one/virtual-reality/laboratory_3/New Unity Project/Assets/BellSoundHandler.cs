using UnityEngine;

public class BellSoundHandler : MonoBehaviour
{
    public AudioClip saw;
    // Start is called before the first frame update
    void Start()
    {
        GetComponent<AudioSource> ().playOnAwake = false;
                 GetComponent<AudioSource> ().clip = saw;
    }
    
    void OnCollisionEnter ()  //Plays Sound Whenever collision detected
    {
        GetComponent<AudioSource>().Play();
    }
    // Update is called once per frame
    void Update()
    {
        
    }
}
