package ro.ubb.myapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ro.ubb.myapp.databinding.IdeaListViewFragmentBinding

class IdeasListViewFragment : Fragment() {

    private val _binding : IdeaListViewFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = IdeasListViewFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.idea_list_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}