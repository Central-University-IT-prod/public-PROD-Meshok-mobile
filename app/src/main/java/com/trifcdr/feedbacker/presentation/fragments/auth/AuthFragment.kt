package com.trifcdr.feedbacker.presentation.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.trifcdr.domain.models.DataResource
import com.trifcdr.feedbacker.R
import com.trifcdr.feedbacker.databinding.FragmentAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private val vm: AuthViewModel by viewModels()

    private lateinit var emailEt: TextInputEditText
    private lateinit var passwordEt: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        emailEt = binding.emailEt
        passwordEt = binding.passwordEt
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUp.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_authFragment_to_registerFragment)
        }

        binding.signIn.setOnClickListener {
            binding.pb1.visibility = View.VISIBLE
            if (emailEt.text.toString() != ""
                && passwordEt.text.toString() != ""
            ) {
                lifecycleScope.launch {
                    vm.authUser(emailEt.text.toString(), passwordEt.text.toString())
                        .collect {
                            if (it is DataResource.Authorized) {
                                Navigation.findNavController(binding.root)
                                    .navigate(R.id.action_authFragment_to_profileFragment)
                            } else {
                                Toast.makeText(
                                    context,
                                    getString(R.string.wrLP),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            binding.pb1.visibility = View.GONE

                        }
                }
            }
            else{
                Toast.makeText(context, getString(R.string.fillF), Toast.LENGTH_SHORT).show()
                binding.pb1.visibility = View.GONE
            }
        }
    }
}