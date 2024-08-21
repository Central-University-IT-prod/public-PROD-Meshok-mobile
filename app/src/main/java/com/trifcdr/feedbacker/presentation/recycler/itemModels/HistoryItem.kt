package com.trifcdr.feedbacker.presentation.recycler.itemModels

import com.trifcdr.lifestylehub.presentation.recycler.itemModels.StringId

data class HistoryItem(
    override val id: String = "history",
    val qId: Int,
    val type: String,
    val description: String,
    var value: Int
) : StringId