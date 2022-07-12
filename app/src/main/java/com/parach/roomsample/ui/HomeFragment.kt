package com.parach.roomsample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.parach.roomsample.R
import com.parach.roomsample.db.NoteDatabase
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {
    lateinit var button_add: FloatingActionButton
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
         button_add = view.findViewById(R.id.button_add)
        recyclerView = view.findViewById(R.id.recycler_view_notes)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                val notes = NoteDatabase(it).getNoteDao().getAllNotes()
                recyclerView.adapter = NotesAdapter(notes)

            }
        }

        button_add?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.addNoteFragment)
        }
    }

}