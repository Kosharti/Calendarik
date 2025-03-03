package com.example.calendarik

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import kotlin.toString

class NoteAdapter(private val onDeleteClick: (Note) -> Unit) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventNameTextView: TextView = itemView.findViewById(R.id.eventNameTextView)
        val noteTextView: TextView = itemView.findViewById(R.id.noteTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteNoteButton)

        fun bind(note: Note) {
            eventNameTextView.text = note.eventName
            noteTextView.text = note.noteText
            timeTextView.text = when {
                note.startTime != null && note.endTime != null ->
                    "${note.startTime.toString()} - ${note.endTime.toString()}"
                note.startTime != null -> note.startTime.toString()
                else -> ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
        holder.deleteButton.setOnClickListener {
            onDeleteClick(note)
        }
    }
}

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}