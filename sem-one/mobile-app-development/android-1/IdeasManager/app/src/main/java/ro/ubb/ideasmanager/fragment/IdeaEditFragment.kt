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
import ro.ubb.ideasmanager.model.IdeaModel
import ro.ubb.ideasmanager.model.view_model.IdeaEditViewModel


class IdeaEditFragment : Fragment() {
    private var _binding : FragmentIdeaEditBinding? = null
    private val binding get() : FragmentIdeaEditBinding = _binding!!
    private lateinit var viewModel : IdeaEditViewModel
    companion object {
        const val IDEA_ID = "IDEA_ID"
    }
    private var ideaId : String? = null
    private var idea: IdeaModel? = null
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
                val ideaModel = IdeaModel(
                    idea?.id!!,
                    binding.ideaTitleView.text.toString(),
                    binding.ideaTextView.text.toString(),
                    Integer.parseInt(binding.ideaNeededBudgetView.text.toString()),
                    Integer.parseInt(binding.ideaCurrentBudgetView.text.toString()),
                    0)
                Log.v(TAG, ideaModel.toString())
                viewModel.saveOrUpdateItem(ideaModel, view.context)
            }
        }
        binding.ideaTextView.setText(ideaId)
        revealRating()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(IdeaEditViewModel::class.java)
        viewModel.idea.observe(viewLifecycleOwner, { idea ->
            Log.v(TAG, "update items")
            binding.ideaCurrentBudgetView.setText(idea.currentBudget.toString())
            binding.ideaTextView.setText(idea.text)
            binding.ideaNeededBudgetView.setText(idea.neededBudget.toString())
            binding.ideaTitleView.setText(idea.title)
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
        if (id == null) {
            idea = IdeaModel("", "", "", 0, 0, 0)
        } else {
            viewModel.loadIdea(id).observe(viewLifecycleOwner, {
                Log.v(TAG, "update items")
                if (it != null) {
                    idea = it
                    binding.ideaTitleView.setText(it.title)
                    binding.ideaTextView.setText(it.text)
                    binding.ideaNeededBudgetView.setText(it.neededBudget.toString())
                    binding.ideaCurrentBudgetView.setText(it.currentBudget.toString())
                    binding.ratingBar.rating = it.rating.toFloat()
                    binding.ratingText.text = it.rating.toFloat().toString()
                }
            })
        }
    }

    private fun revealRating(){

        binding.ratingText.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(2000)
                .setListener(null)
        }
    }
}