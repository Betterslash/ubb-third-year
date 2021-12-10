package ro.ubb.ideamanager.model.idea

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ro.ubb.ideamanager.IdeaEditFragment
import ro.ubb.ideamanager.R
import ro.ubb.ideamanager.shared.TAG

class IdeaListAdapter (private val fragment: Fragment,)
    : RecyclerView.Adapter<IdeaListAdapter.ViewHolder>() {
    var ideas = emptyList<Idea>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onItemClick : View.OnClickListener = View.OnClickListener{view ->
        val idea = view.tag as Idea
        fragment.findNavController().navigate(R.id.IdeaEditFragment, Bundle().apply {
            putString(IdeaEditFragment.ITEM_ID, idea._id)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sample_idea_view, parent, false)
        Log.v(TAG, "onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder $position")
        val idea = ideas[position]
        holder.textView.text = idea.title
        holder.itemView.tag = idea
        holder.itemView.setOnClickListener(onItemClick)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text)

    }

    override fun getItemCount(): Int {
        return ideas.size
    }
}