package ro.ubb.ideasmanager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ro.ubb.ideasmanager.R
import ro.ubb.ideasmanager.databinding.FragmentIdeaEditBinding


class IdeaEditFragment : Fragment() {
    private var _binding : FragmentIdeaEditBinding? = null
    private val binding get() : FragmentIdeaEditBinding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIdeaEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.updateIdeaButton.setOnClickListener{
            findNavController()
                .navigate(R.id.action_ideaEditFragment_to_ideaListFragment2)
        }
    }
}