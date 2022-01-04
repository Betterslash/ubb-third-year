package ro.ubb.ideasmanager.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ro.ubb.ideasmanager.R
import ro.ubb.ideasmanager.core.log.TAG
import ro.ubb.ideasmanager.databinding.FragmentLoginBinding
import ro.ubb.ideasmanager.model.view_model.LoginViewModel
import ro.ubb.ideasmanager.core.auth.Result

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding:FragmentLoginBinding get() = _binding!!

    private lateinit var viewModel : LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(TAG, "onCreateView")
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setupLoginForm()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView")
        _binding = null
    }

    private fun setupLoginForm() {
        viewModel.loginFormState.observe(viewLifecycleOwner, { loginState ->
            binding.loginButton.isEnabled = loginState.isDataValid
            if (loginState.usernameError != null) {
                binding.usernameTextView.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.passwordTextView.error = getString(loginState.passwordError)
            }
        })
        viewModel.loginResult.observe(viewLifecycleOwner, { loginResult ->
            binding.loading.visibility = View.GONE
            if (loginResult is Result.Success<*>) {
                findNavController().navigate(R.id.ideaListFragment2)
            } else if (loginResult is Result.Error) {
                binding.errorText.text = "Login error ${loginResult.exception.message}"
                binding.errorText.visibility = View.VISIBLE
            }
        })
        binding.usernameTextView.afterTextChanged {
            viewModel.loginDataChanged(
                binding.usernameTextView.text.toString(),
                binding.passwordTextView.text.toString()
            )
        }
        binding.passwordTextView.afterTextChanged {
            viewModel.loginDataChanged(
                binding.usernameTextView.text.toString(),
                binding.passwordTextView.text.toString()
            )
        }
        binding.loginButton.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            binding.errorText.visibility = View.GONE
            viewModel.login(binding.usernameTextView.text.toString(), binding.passwordTextView.text.toString())
        }
    }
}
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}