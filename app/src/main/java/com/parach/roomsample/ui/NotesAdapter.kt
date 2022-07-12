package com.parach.roomsample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parach.roomsample.R
import com.parach.roomsample.db.Note

class NotesAdapter(private var notes: List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val titleView: TextView
        val noteView: TextView

        init {
            titleView = view.findViewById(R.id.text_view_title)
            noteView = view.findViewById(R.id.text_view_note)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.titleView.text = notes[position].title
        holder.noteView.text = notes[position].note

        holder.view.setOnClickListener {
            val action = HomeFragment.
        }
    }

    override fun getItemCount() = notes.size
}