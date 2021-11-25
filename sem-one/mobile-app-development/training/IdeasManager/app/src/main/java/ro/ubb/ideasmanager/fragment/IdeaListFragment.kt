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
import ro.ubb.ideasmanager.R
import ro.ubb.ideasmanager.adapter.IdeaListAdapter
import ro.ubb.ideasmanager.core.auth.AuthRepository
import ro.ubb.ideasmanager.core.log.TAG
import ro.ubb.ideasmanager.databinding.FragmentIdeaListBinding
import ro.ubb.ideasmanager.model.view_model.IdeaListViewModel

class IdeaListFragment : Fragment() {

    private lateinit var ideasModel : IdeaListViewModel
    private lateinit var ideaListAdapter: IdeaListAdapter
    private var _binding : FragmentIdeaListBinding? = null
    private val binding get() = _binding!!

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
        if (!AuthRepository.isLoggedIn) {
            findNavController().navigate(R.id.loginFragment)
            return
        }
        setupItemList()
        binding.addIdeaButton.setOnClickListener{
            Log.v(TAG, "add new idea")
            findNavController().navigate(R.id.ideaEditFragment)
        }
        binding.logoutButton.setOnClickListener{
            Log.v(TAG, "logout...")
            AuthRepository.logout()
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun setupItemList() {
        ideaListAdapter = IdeaListAdapter(this)
        binding.recyclerView.adapter = ideaListAdapter
        ideasModel = ViewModelProvider(this).get(IdeaListViewModel::class.java)
        ideasModel.ideas.observe(viewLifecycleOwner, { value ->
            ideaListAdapter.dataSet = value
        })
        ideasModel.loading.observe(viewLifecycleOwner, { loading ->
            Log.i(TAG, "update loading")
            binding.progress.visibility = if (loading) View.VISIBLE else View.GONE
        })
        ideasModel.loadingError.observe(viewLifecycleOwner, { exception ->
            if (exception != null) {
                Log.i(TAG, "update loading error")
                val message = "Loading exception ${exception.message}"
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        })
        ideasModel.loadItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView")
        _binding = null
    }
}