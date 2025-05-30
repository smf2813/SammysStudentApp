package com.idz.colman24class2

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.idz.colman24class2.model.Model
import com.idz.colman24class2.model.Student

class EditStudentActivity : AppCompatActivity() {

    private var studentIndex: Int = -1
    private var student: Student? = null

    private lateinit var nameEditText: EditText
    private lateinit var idEditText: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        // Initialize views
        nameEditText = findViewById(R.id.edit_student_name_edit_text)
        idEditText = findViewById(R.id.edit_student_id_edit_text)
        checkBox = findViewById(R.id.edit_student_checkbox)
        saveButton = findViewById(R.id.edit_student_save_button)
        deleteButton = findViewById(R.id.edit_student_delete_button)
        cancelButton = findViewById(R.id.edit_student_cancel_button)

        // Get student index
        studentIndex = intent.getIntExtra("student_position", -1)
        if (studentIndex == -1 || studentIndex >= Model.shared.students.size) {
            Toast.makeText(this, "Invalid student", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        student = Model.shared.students[studentIndex]

        // Fill fields with current data
        nameEditText.setText(student?.name)
        idEditText.setText(student?.id)
        checkBox.isChecked = student?.isChecked ?: false

        // Save button
        saveButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val id = idEditText.text.toString().trim()
            val checked = checkBox.isChecked

            if (name.isEmpty() || id.isEmpty()) {
                Toast.makeText(this, "Please enter both name and ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            student?.apply {
                this.name = name
                this.id = id
                this.isChecked = checked
            }

            Toast.makeText(this, "Student updated!", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        }

        // Delete button
        deleteButton.setOnClickListener {
            Model.shared.students.removeAt(studentIndex)
            Toast.makeText(this, "Student deleted", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        }

        // Cancel button
        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}
