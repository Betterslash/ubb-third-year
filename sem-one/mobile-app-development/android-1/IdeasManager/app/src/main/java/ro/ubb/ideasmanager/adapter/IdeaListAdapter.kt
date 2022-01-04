package ro.ubb.ideasmanager.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_idea_list.view.*
import kotlinx.coroutines.*
import ro.ubb.ideasmanager.R
import ro.ubb.ideasmanager.core.log.TAG
import ro.ubb.ideasmanager.data.database.IdeaDatabase
import ro.ubb.ideasmanager.fragment.IdeaEditFragment
import ro.ubb.ideasmanager.model.IdeaModel
import ro.ubb.ideasmanager.repository.IdeaRepo

class IdeaListAdapter(
    private val fragment : Fragment
) :
    RecyclerView.Adapter<IdeaListAdapter.ViewHolder>(){
    private val ideaRepository : IdeaRepo

    init {
        val ideaDao = IdeaDatabase.getDatabase(fragment.requireContext(), fragment.lifecycleScope).ideaDao()
        ideaRepository = IdeaRepo(ideaDao)
    }
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

    private var onItemClick: View.OnClickListener = View.OnClickListener { view ->
        val idea = view.tag as IdeaModel
        fragment.findNavController().navigate(R.id.action_ideaListFragment2_to_ideaEditFragment, Bundle().apply {
            putString(IdeaEditFragment.IDEA_ID, idea.id)
        })
    }

    private var onDeleteButtonClick: View.OnClickListener = View.OnClickListener { view ->
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            val ideaId = ((view.parent.parent as View).tag as IdeaModel).id
            ideaRepository.delete(ideaId)
            dataSet = dataSet.filter { e -> e.id != ideaId }
            Log.i(TAG, ideaId)
        }
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
        viewHolder.textView.text = idea.title
        viewHolder.itemView.tag = idea
        viewHolder.itemView.setOnClickListener(onItemClick)
        viewHolder.itemView.delete_button.setOnClickListener(onDeleteButtonClick)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = dataSet.size
}