package ro.ubb.myapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var currentValue = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        incrementButton.setOnClickListener {
            currentValue++
            text_counter.text = "$currentValue"
            Log.i("someTag","Value incremented to $currentValue")
        }
        Log.i("someTag","Value incremented to $currentValue")
    }
}