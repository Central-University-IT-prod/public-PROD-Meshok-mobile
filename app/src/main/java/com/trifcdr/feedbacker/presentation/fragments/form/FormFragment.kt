package com.trifcdr.feedbacker.presentation.fragments.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trifcdr.domain.models.DataResource
import com.trifcdr.domain.models.FormAnswerData
import com.trifcdr.feedbacker.R
import com.trifcdr.feedbacker.databinding.FragmentFormBinding
import com.trifcdr.feedbacker.presentation.recycler.BaseRecyclerAdapter
import com.trifcdr.feedbacker.presentation.recycler.itemModels.RangeItem
import com.trifcdr.feedbacker.presentation.recycler.itemModels.SmileItem
import com.trifcdr.lifestylehub.presentation.recycler.itemModels.StringId
import com.trifcdr.lifestylehub.presentation.recycler.managers.ViewHoldersManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class FormFragment : Fragment() {

    private lateinit var binding: FragmentFormBinding
    private val vm: FormViewModel by viewModels()

    private val args: FormFragmentArgs by navArgs()

    @Inject
    lateinit var viewHoldersManager: ViewHoldersManager
    private lateinit var recycler: RecyclerView
    private val items = mutableListOf<StringId>()
    private lateinit var itemsAdapter: BaseRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        binding.backBtn.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_formFargment_to_scanFragment)
        }
        binding.pb2.visibility = View.VISIBLE
        lifecycleScope.launch {
            vm.getForm(args.formId)
                .collect { dataResource ->
                    if (dataResource is DataResource.Success) {
                        binding.orgNameTv.text = dataResource.result.orgName
                        dataResource.result.questionData.forEach {
                            when (it.type) {
                                "Smile" -> {
                                    items.add(
                                        SmileItem(
                                            questionId = it.id,
                                            description = it.description
                                        )
                                    )
                                }

                                "Range" -> {
                                    items.add(
                                        RangeItem(
                                            questionId = it.id,
                                            description = it.description
                                        )
                                    )
                                }
                            }

                        }
                        itemsAdapter.submitList(items)
                    }
                    binding.pb2.visibility = View.GONE
                }
        }
        binding.sendFormBth.setOnClickListener {
            var fullFill = true
            val answerList: MutableList<FormAnswerData> = mutableListOf()
            itemsAdapter.currentList.forEach {
                when (it.id) {
                    "range" -> {
                        val range = it as RangeItem
                        if (range.value == -2) {
                            fullFill = false
                            return@forEach
                        }
                        answerList.add(
                            FormAnswerData(
                                questionId = range.questionId,
                                answer = range.value
                            )
                        )
                    }

                    "smile" -> {
                        val smile = it as SmileItem
                        if (smile.choice == -2) {
                            fullFill = false
                            return@forEach
                        }
                        answerList.add(
                            FormAnswerData(
                                questionId = smile.questionId,
                                answer = smile.choice
                            )
                        )

                    }
                }
            }
            if (fullFill) {
                lifecycleScope.launch {
                    vm.sendForm(answerList)
                        .collect {
                            if (it) {
                                Toast.makeText(context, "Спасибо!", Toast.LENGTH_SHORT).show()
                                Navigation.findNavController(binding.root)
                                    .navigate(R.id.action_formFargment_to_scanFragment)
                            } else {
                                Navigation.findNavController(binding.root)
                                    .navigate(R.id.action_formFargment_to_registerFragment)
                            }
                        }
                }
            } else {
                Toast.makeText(context, getString(R.string.fill), Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun initRecyclerView() {
        recycler = binding.recyclerForm
        itemsAdapter = BaseRecyclerAdapter(viewHoldersManager)
        recycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = itemsAdapter
        }

    }

}