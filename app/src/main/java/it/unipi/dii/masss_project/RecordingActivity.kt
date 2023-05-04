package it.unipi.dii.masss_project

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import it.unipi.dii.masss_project.databinding.ActivityRecordingBinding

class RecordingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordingBinding
    private var lastUpdateAccelerometer: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("username")

        binding= DataBindingUtil.setContentView(
            this, R.layout.activity_recording)

        val textView = TextView(this)
        "Welcome ${username}!".also { textView.text = it }
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT, // Width
            ConstraintLayout.LayoutParams.MATCH_PARENT // Height
        )
        layoutParams.setMargins(425, 750, 0, 0) // Left, Top, Right, Bottom margins
        textView.layoutParams = layoutParams
        val parentLayout = findViewById<ConstraintLayout>(R.id.parentLayoutRecordingActivity)
        parentLayout.addView(textView)

        val startButton: Button = binding.startButton
        startButton.setOnClickListener {onStartAttempt(binding) }

        val resultButton: Button = binding.resultButton
        resultButton.setOnClickListener {onResult(binding) }

    }

    private fun onStartAttempt(binding: ActivityRecordingBinding) {
        if (binding.startButton.text == "Start") {
            binding.startButton.text = "Stop"

            val resultButton: Button = binding.resultButton
            resultButton.visibility = View.INVISIBLE

            val message = "Start transportation mode detection"
            val duration = Toast.LENGTH_LONG

            val toast = Toast.makeText(this, message, duration)
            toast.show()

            /**************** Get an instance of the SensorManager ****************/
            val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

            /****************           Accelerometer              ****************/
            // Get the accelerometer sensor
            val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            val accelerometerListener = SensorAccelerometer()
            sensorManager.registerListener(accelerometerListener,
                                                accelerometer,
                                                SensorManager.SENSOR_DELAY_NORMAL)

            /*****************          Gyroscope                  ****************/
            //Get the gyroscope sensor
            val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            val gyroscopeListener = SensorGyroscope()
            sensorManager.registerListener(gyroscopeListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)

            /****************           TO-DO              ****************/

            // todo: rilevare posizione utente tramite GPS
            // todo: fare una prima classificazione con l'activity recognition system api
            // todo: se viene rilevato vehicle -> usare classificatore
        } else {
            binding.startButton.text = "Start"

            val resultButton: Button = binding.resultButton
            resultButton.visibility = View.VISIBLE

            val message = "Stop transportation mode detection"
            val duration = Toast.LENGTH_LONG

            val toast = Toast.makeText(this, message, duration)
            toast.show()

            // todo: collezionare risultati del transportation mode


            // todo: passare ad unl'altra pagina che mostra i risultati ottenuti
            //val intent = Intent(this, ResultActivity::class.java)
            //startActivity(intent)
        }

    }

    private fun onResult(binding: ActivityRecordingBinding) {
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
    }

}