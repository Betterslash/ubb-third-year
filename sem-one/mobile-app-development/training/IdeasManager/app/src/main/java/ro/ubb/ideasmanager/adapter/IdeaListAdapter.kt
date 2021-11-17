package ro.ubb.ideasmanager.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ro.ubb.ideasmanager.R
import ro.ubb.ideasmanager.log.TAG
import ro.ubb.ideasmanager.model.IdeaModel

class IdeaListAdapter :
    RecyclerView.Adapter<IdeaListAdapter.ViewHolder>(){

    var onItemClick: ((IdeaModel) -> Unit)? = null

    var dataSet = emptyList<IdeaModel>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_idea_list, viewGroup, false)
        Log.v(TAG, "onCreateViewHolder")
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder $position")
        val idea = dataSet[position]
        viewHolder.textView.text = idea.text
        viewHolder.itemView.tag = idea
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = dataSet.size
}