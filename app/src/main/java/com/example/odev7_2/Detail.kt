package com.example.odev7_2

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Detail : AppCompatActivity() {
    lateinit var titleTextView: TextView
    lateinit var detailTextView: TextView
    lateinit var db: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        titleTextView = findViewById(R.id.txttitle)
        detailTextView = findViewById(R.id.txtdetail)
        db = Database(this)

        val position = intent.getIntExtra("POSITION", -1)
        if (position != -1) {
            val notes = db.getAllNotes()
            val note = notes[position]
            val title = note.title
            val detail = note.detail
            titleTextView.text = title
            detailTextView.text = detail
        }
    }
}