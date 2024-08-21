package com.trifcdr.feedbacker.presentation.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.trifcdr.domain.models.DataResource
import com.trifcdr.domain.models.UserData
import com.trifcdr.feedbacker.R
import com.trifcdr.feedbacker.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val vm: RegisterViewModel by viewModels()

    private lateinit var email: EditText
    private lateinit var age: EditText
    private lateinit var gender: AutoCompleteTextView
    private lateinit var password: EditText

    private var countries = arrayOf(
        "Мужской", "Женский"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        email = binding.emailEt
        age = binding.ageEt
        gender = binding.genderEt
        password = binding.passwordEt
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(requireActivity(), android.R.layout.simple_dropdown_item_1line, countries)
        gender.setAdapter(adapter)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signInReg.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_registerFragment_to_authFragment)
        }
        binding.signUpReg.setOnClickListener {
            binding.pb.visibility = View.VISIBLE
            if (email.text.toString() != ""
                && age.text.toString() != ""
                && gender.text.toString() != ""
                && password.text.toString() != ""
            ) {
                val gender = if (gender.text.toString() == getString(R.string.mans)) "Male" else "Female"
                lifecycleScope.launch {
                    vm.registerUser(
                        UserData(
                            email = email.text.toString(),
                            password = password.text.toString(),
                            age = age.text.toString().toInt(),
                            gender = gender
                        )
                    ).collect {
                        if (it is DataResource.Success) {
                            Toast.makeText(context, getString(R.string.ok), Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(binding.root)
                                .navigate(R.id.action_registerFragment_to_profileFragment)
                        }
                        if (it is DataResource.ValidationError) {
                            Toast.makeText(
                                context,
                                getString(R.string.checkFields),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (it is DataResource.BadRequest) {
                            Toast.makeText(
                                context,
                                getString(R.string.alreadyExist),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        binding.pb.visibility = View.GONE
                    }
                }
            } else {
                Toast.makeText(context, getString(R.string.fillF), Toast.LENGTH_SHORT).show()
                binding.pb.visibility = View.GONE
            }
        }
    }
}