package it.unipi.dii.masss_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import it.unipi.dii.masss_project.databinding.ActivityMainBinding
import it.unipi.dii.masss_project.databinding.ActivityRecordingBinding

class RecordingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("username")

        val binding: ActivityRecordingBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_recording)

        val textView = TextView(this)
        textView.text = "Welcome ${username}!"
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT, // Width
            ConstraintLayout.LayoutParams.MATCH_PARENT // Height
        )
        layoutParams.setMargins(425, 750, 0, 0) // Left, Top, Right, Bottom margins
        textView.layoutParams = layoutParams
        val parentLayout = findViewById<ConstraintLayout>(R.id.parentLayout)
        parentLayout.addView(textView)

        val button: Button = binding.startButton
        button.setOnClickListener {onStartAttempt(binding) }

    }

    private fun onStartAttempt(binding: ActivityRecordingBinding) {
        if (binding.startButton.text == "Start") {
            binding.startButton.text = "Stop"
            val message = "Start transportation mode detection"
            val duration = Toast.LENGTH_LONG

            val toast = Toast.makeText(this, message, duration)
            toast.show()

            // todo: rilevare posizione utente tramite GPS
            // todo: fare una prima classificazione con l'activity recognition system api
            // todo: se viene rilevato vehicle -> usare classificatore
        } else {
            binding.startButton.text = "Start"
            val message = "Stop transportation mode detection"
            val duration = Toast.LENGTH_LONG

            val toast = Toast.makeText(this, message, duration)
            toast.show()

            // todo: passare ad unl'altra pagina che mostra i risultati ottenuti
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }

    }
}