package com.trifcdr.feedbacker.presentation.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trifcdr.domain.models.DataResource
import com.trifcdr.feedbacker.R
import com.trifcdr.feedbacker.databinding.FragmentProfileBinding
import com.trifcdr.feedbacker.presentation.recycler.BaseRecyclerAdapter
import com.trifcdr.feedbacker.presentation.recycler.itemModels.HistoryItem
import com.trifcdr.feedbacker.presentation.recycler.itemModels.SeparatorItem
import com.trifcdr.lifestylehub.presentation.recycler.itemModels.StringId
import com.trifcdr.lifestylehub.presentation.recycler.managers.ViewHoldersManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val vm: ProfileViewModel by viewModels()

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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        CoroutineScope(Dispatchers.IO).launch {
            vm.getUserData()
                .collect {
                    if (it is DataResource.Success) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val gender = if (it.result.gender == "Male") getString(R.string.man) else getString(
                                R.string.woman
                            )
                            binding.ageTv.text = it.result.age.toString()
                            binding.emailTv.text = it.result.email
                            binding.genderTv.text = gender
                            binding.currentScoreTv.text = it.result.currentXp.toString()
                            binding.minScoreTv.text = it.result.minXp.toString()
                            binding.maxScoreTv.text = it.result.maxXp.toString()
                            binding.nowLevelTv.text = it.result.currentLevel.toString()
                            binding.nextLevelTv.text = it.result.nextLevel ?: "Максимум"
                            if (it.result.currentXp != 0 && it.result.maxXp != 0) {
                                val chisl: Float =
                                    (it.result.currentXp!!).minus((it.result.minXp!!)).toFloat()
                                val znapm: Float =
                                    (it.result.maxXp!!).minus((it.result.minXp!!)).toFloat()
                                val percent: Float = (chisl / znapm)
                                binding.progressBar.progress = (percent * 100).toInt()
                            }
                        }
                    }
                }
            vm.getAnswerHistory()
                .collect { resource ->
                    if (resource is DataResource.Success) {
                        resource.result.organizationsAns.keys.forEach { s ->
                            items.add(SeparatorItem(text = s))
                            resource.result.organizationsAns[s]?.forEach {
                                items.add(
                                    HistoryItem(
                                        qId = it.id,
                                        type = it.type,
                                        description = it.description,
                                        value = it.answer
                                    )
                                )
                            }
                        }
                        CoroutineScope(Dispatchers.Main).launch {
                            itemsAdapter.submitList(items)
                        }
                    }
                }
            binding.logoutBtn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    vm.logout()
                    CoroutineScope(Dispatchers.Main).launch {
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_profileFargment_to_registerFragment)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        recycler = binding.recycler
        itemsAdapter = BaseRecyclerAdapter(viewHoldersManager)
        recycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = itemsAdapter
        }
    }

    override fun onDestroy() {
        itemsAdapter.submitList(null)
        super.onDestroy()
    }
}