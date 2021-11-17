package ro.ubb.ideasmanager.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ro.ubb.ideasmanager.adapter.IdeaListAdapter
import ro.ubb.ideasmanager.data.DataGenerator
import ro.ubb.ideasmanager.databinding.FragmentMainBinding
import ro.ubb.ideasmanager.log.TAG

class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding? = null
    private val binding: FragmentMainBinding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(TAG, "onCreateView")
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = IdeaListAdapter(DataGenerator().generateObjects())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView")
        _binding = null
    }
}