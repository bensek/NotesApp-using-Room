package com.parach.roomsample.ui

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.parach.roomsample.R
import com.parach.roomsample.db.Note
import com.parach.roomsample.db.NoteDatabase
import com.parach.roomsample.util.toast
import kotlinx.coroutines.launch

class AddNoteFragment : BaseFragment() {
    var button_save: FloatingActionButton? = null
    var title_edit_text: EditText? = null
    var note_edit_text: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)
        button_save = view.findViewById(R.id.button_save)
        title_edit_text = view.findViewById(R.id.edit_text_title)
        note_edit_text  = view.findViewById(R.id.edit_text_note)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button_save?.setOnClickListener { view ->
            val title = title_edit_text?.text.toString().trim()
            val note = note_edit_text?.text.toString().trim()

            if(title.isEmpty()){
                title_edit_text?.error = "title required"
                title_edit_text?.requestFocus()
                return@setOnClickListener
            }

            if(note.isEmpty()){
                note_edit_text?.error = "note required"
                note_edit_text?.requestFocus()
                return@setOnClickListener
            }

            launch {
                val myNote = Note(title,note)

                context?.let {
                    NoteDatabase(requireActivity()).getNoteDao().addNote(myNote)
                    it.toast("Note saved")

                    Navigation.findNavController(view).navigate(R.id.homeFragment)
                }
            }
//            saveNote(myNote)
        }
    }

//    private fun saveNote(note: Note){
//        class SaveNote: AsyncTask<Void, Void, Void>(){
//            override fun doInBackground(vararg params: Void?): Void? {
//                NoteDatabase(requireActivity()).getNoteDao().addNote(note)
//
//                return null
//            }
//
//            override fun onPostExecute(result: Void?) {
//                super.onPostExecute(result)
//                Toast.makeText(requireActivity(), "Note Saved", Toast.LENGTH_LONG).show()
//            }
//        }
//
//        SaveNote().execute()
//    }

}