package ro.ubb.ideasmanager.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ro.ubb.ideasmanager.databinding.FragmentIdeaEditBinding
import ro.ubb.ideasmanager.core.log.TAG
import ro.ubb.ideasmanager.model.view_model.IdeaEditViewModel


class IdeaEditFragment : Fragment() {
    private var _binding : FragmentIdeaEditBinding? = null
    private val binding get() : FragmentIdeaEditBinding = _binding!!
    private lateinit var viewModel : IdeaEditViewModel
    companion object {
        const val IDEA_ID = ""
    }
    private var ideaId : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            if (it.containsKey(IDEA_ID)) {
                ideaId = it.getString(IDEA_ID).toString()
            }
        }
        _binding = FragmentIdeaEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        binding.updateIdeaButton.setOnClickListener{
            binding.updateIdeaButton.setOnClickListener {
                Log.v(TAG, "save item")
                viewModel.saveOrUpdateItem(binding.ideaTextView.text.toString())
            }
        }
        binding.ideaTextView.setText(ideaId)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(IdeaEditViewModel::class.java)
        viewModel.idea.observe(viewLifecycleOwner, { item ->
            Log.v(TAG, "update items")
            binding.ideaTextView.setText(item.text)
        })
        viewModel.fetching.observe(viewLifecycleOwner, { fetching ->
            Log.v(TAG, "update fetching")
            binding.progressBar.visibility = if (fetching) View.VISIBLE else View.GONE
        })
        viewModel.fetchingError.observe(viewLifecycleOwner, { exception ->
            if (exception != null) {
                Log.v(TAG, "update fetching error")
                val message = "Fetching exception ${exception.message}"
                val parentActivity = activity?.parent
                if (parentActivity != null) {
                    Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.completed.observe(viewLifecycleOwner, { completed ->
            if (completed) {
                Log.v(TAG, "completed, navigate back")
                findNavController().navigateUp()
            }
        })
        val id = ideaId
        if (id != null) {
            viewModel.loadIdea(id)
        }
    }
}