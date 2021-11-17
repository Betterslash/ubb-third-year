package ro.ubb.myapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ro.ubb.myapp.adapter.IdeasListAdapter
import ro.ubb.myapp.databinding.FragmentIdeasListBinding
import ro.ubb.myapp.logger.TAG
import ro.ubb.myapp.view_model.IdeaViewModel

class IdeasListFragment : Fragment() {

    private var _binding : FragmentIdeasListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView")
        _binding = FragmentIdeasListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.ideasList.adapter = IdeasListAdapter(this, IdeaViewModel.ideas)
    }

}