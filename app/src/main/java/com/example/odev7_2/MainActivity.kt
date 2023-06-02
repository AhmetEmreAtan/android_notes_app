package com.example.odev7_2

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var database: Database
    lateinit var edittxttitle: EditText
    lateinit var edittxtdetail: EditText
    lateinit var btnsave: Button
    lateinit var listView: ListView
    lateinit var adapter: ArrayAdapter<Notes>
    private lateinit var notesList: MutableList<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edittxttitle = findViewById(R.id.edittexttitle)
        edittxtdetail = findViewById(R.id.edittxtdetail)
        btnsave = findViewById(R.id.btnsave)
        listView = findViewById(R.id.listView)
        database = Database(this)
        notesList = mutableListOf()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notesList)

        btnsave.setOnClickListener {
            val title = edittxttitle.text.toString()
            val detail = edittxtdetail.text.toString()
            database.insertNote(title, detail)
            showNotes()
        }

        btnsave.setOnClickListener {
            val title = edittxttitle.text.toString()
            val detail = edittxtdetail.text.toString()

            if (title.isEmpty() || detail.isEmpty()) {
                Toast.makeText(this, "Eksik bilgi girdiniz :)))", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                edittxttitle.setText("")
                edittxtdetail.setText("")
            }

            database.insertNote(title, detail)
            showNotes()
        }

        showNotes()

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, Detail::class.java)
            intent.putExtra("POSITION", position)
            startActivity(intent)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Notu silmek istediğinize emin misiniz?")
                .setCancelable(false)
                .setPositiveButton("Evet") { _, _ ->
                    val db = Database(this)
                    val note = notesList[position]
                    db.deleteNoteById(note.id)
                    db.close()

                    notesList.removeAt(position)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Başarıyla Silindi.", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Hayır") { dialog, _ ->
                    dialog.cancel()
                }
            val alert = dialogBuilder.create()
            alert.show()

            true
        }
    }

    private fun showNotes() {
        val notes = database.getAllNotes()
        notesList.clear()
        notesList.addAll(notes)
        adapter.notifyDataSetChanged()
        listView.adapter = adapter
    }
}
