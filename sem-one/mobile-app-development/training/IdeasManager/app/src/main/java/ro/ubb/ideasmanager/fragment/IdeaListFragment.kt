package ro.ubb.ideasmanager.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ro.ubb.ideasmanager.R
import ro.ubb.ideasmanager.adapter.IdeaListAdapter
import ro.ubb.ideasmanager.data.DataSource
import ro.ubb.ideasmanager.databinding.FragmentIdeaListBinding
import ro.ubb.ideasmanager.log.TAG
import ro.ubb.ideasmanager.model.view_model.IdeaListViewModel
import ro.ubb.ideasmanager.model.view_model.IdeaListViewModelFactory

class IdeaListFragment(context : Context) : Fragment() {
    private var _binding : FragmentIdeaListBinding? = null
    private val binding get() = _binding!!
    private val ideaListViewModel by viewModels<IdeaListViewModel> {
        IdeaListViewModelFactory(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView")
        _binding = FragmentIdeaListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = IdeaListAdapter(DataSource.getDataSource(context?.resources!!))
        adapter.onItemClick = {
            ideaModel -> Log.i(TAG, ideaModel.text)
        }
        binding.recyclerView.adapter = adapter
        binding.addIdeaButton.setOnClickListener{
            findNavController()
                .navigate(R.id.action_ideaListFragment2_to_ideaEditFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView")
        _binding = null
    }
}