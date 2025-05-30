package com.idz.colman24class2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.idz.colman24class2.model.Model
import com.idz.colman24class2.model.Student

class StudentDetailsActivity : AppCompatActivity() {

    private var student: Student? = null
    private var position: Int = -1

    private lateinit var nameText: TextView
    private lateinit var idText: TextView
    private lateinit var isCheckedText: TextView
    private lateinit var imageView: ImageView
    private lateinit var backButton: Button
    private lateinit var editButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.student_details_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get data from Intent
        position = intent.getIntExtra("student_position", -1)
        if (position >= 0) {
            student = Model.shared.students[position]
        }

        // Initialize views
        imageView = findViewById(R.id.student_details_image_view)
        nameText = findViewById(R.id.student_details_name_text_view)
        idText = findViewById(R.id.student_details_id_text_view)
        isCheckedText = findViewById(R.id.student_details_checked_text_view)
        backButton = findViewById(R.id.student_details_back_button)
        editButton = findViewById(R.id.student_details_edit_button)

        updateUI()

        backButton.setOnClickListener {
            finish()
        }

        editButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student_position", position)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh student data if changed
        if (position >= 0) {
            student = Model.shared.students[position]
            updateUI()
        }
    }

    private fun updateUI() {
        student?.let {
            imageView.setImageResource(R.drawable.avatar)
            nameText.text = "Name: ${it.name}"
            idText.text = "ID: ${it.id}"
            isCheckedText.text = "Checked: ${it.isChecked}"
        }
    }
}
