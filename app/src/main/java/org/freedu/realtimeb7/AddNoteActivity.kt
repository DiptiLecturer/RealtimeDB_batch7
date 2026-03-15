package org.freedu.realtimeb7

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.freedu.realtimeb7.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: DatabaseReference

    private var noteid : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = FirebaseDatabase.getInstance().getReference("Notes")


        val note = intent.getParcelableExtra<Note>("note")
        note?.let{
            binding.etTitle.setText(it.title)
            binding.etDescription.setText(it.description)
            noteid = it.id
            binding.btnAdd.text="Update Note"
        }


        binding.btnAdd.setOnClickListener {

            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()


            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Title and Description are required", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }


            val id = noteid?: db.push().key!!

            val newNote = Note(id, title, description)



            db.child(id).setValue(newNote).addOnSuccessListener {
                Toast.makeText(
                    this,
                    if (noteid == null) "Note saved successfully" else "Note updated successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show()
            }
            finish()
            //after saving the data clear the text field
            binding.etTitle.text.clear()
            binding.etDescription.text.clear()
        }
    }
}