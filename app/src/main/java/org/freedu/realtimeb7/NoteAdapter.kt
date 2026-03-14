package org.freedu.realtimeb7



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.freedu.realtimeb7.databinding.ItemNoteBinding

class NoteAdapter(
    private val list: MutableList<Note>,
    private val onDelete: (Note) -> Unit
) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = list[position]
        holder.binding.tvTitle.text = note.title
        holder.binding.etDescription.text = note.description

        holder.binding.btnDelete.setOnClickListener {
            onDelete(note)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

}