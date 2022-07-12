package com.parach.roomsample.ui

import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)
        setHasOptionsMenu(true)
        button_save = view.findViewById(R.id.button_save)
        title_edit_text = view.findViewById(R.id.edit_text_title)
        note_edit_text  = view.findViewById(R.id.edit_text_note)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            title_edit_text?.setText(note?.title)
            note_edit_text?.setText(note?.note)
        }

        button_save?.setOnClickListener { view ->
            val title = title_edit_text?.text.toString().trim()
            val body = note_edit_text?.text.toString().trim()

            if(title.isEmpty()){
                title_edit_text?.error = "title required"
                title_edit_text?.requestFocus()
                return@setOnClickListener
            }

            if(body.isEmpty()){
                note_edit_text?.error = "note required"
                note_edit_text?.requestFocus()
                return@setOnClickListener
            }

            launch {
                context?.let {
                    val myNote = Note(title,body)
                    if(note == null){
                        NoteDatabase(requireActivity()).getNoteDao().addNote(myNote)
                        it.toast("Note Saved")
                    }else{
                        myNote.id = note!!.id
                        NoteDatabase(requireActivity()).getNoteDao().updateNote(myNote)
                        it.toast("Note Updated")
                    }

                    Navigation.findNavController(view).navigate(R.id.homeFragment)
                }
            }
//            saveNote(myNote)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    private fun deleteNote(){
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes"){_,_ -> {
                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(note!!)
                    NoteDatabase(requireActivity()).getNoteDao().deleteNote(note!!)
                }
            }

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete -> if(note != null) deleteNote() else context?.toast("Can not delete")
        }
        return super.onOptionsItemSelected(item)
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