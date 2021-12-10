package ro.ubb.ideamanager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ro.ubb.ideamanager.databinding.FragmentIdeaListBinding
import ro.ubb.ideamanager.model.idea.IdeaListAdapter
import ro.ubb.ideamanager.model.idea.IdeaListViewModel
import ro.ubb.ideamanager.shared.TAG

class IdeaListFragment : Fragment() {

    private var _binding: FragmentIdeaListBinding? = null
    private lateinit var ideaListAdapter: IdeaListAdapter
    private lateinit var ideaListViewModel: IdeaListViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIdeaListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated")
        setUpItemList()
    }

    private fun setUpItemList() {
        ideaListAdapter = IdeaListAdapter(this)
        binding.ideaList.adapter = ideaListAdapter
        ideaListViewModel = ViewModelProvider(this).get(IdeaListViewModel::class.java)
        ideaListViewModel.ideas.observe(viewLifecycleOwner, {value ->
            Log.i(TAG, "update ideas")
            ideaListAdapter.ideas = value
        })
        ideaListViewModel.loading.observe(viewLifecycleOwner, {loading ->
            Log.i(TAG, "update loading")
            binding.progressBar.visibility = if(loading) View.VISIBLE else View.GONE
        })
        ideaListViewModel.loadingError.observe(viewLifecycleOwner, {
            exception -> if(exception != null){
                Log.i(TAG, "UPDATE LOADING ERROR")
                val message = "Loading exception ${exception.message}"
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        })
        ideaListViewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}