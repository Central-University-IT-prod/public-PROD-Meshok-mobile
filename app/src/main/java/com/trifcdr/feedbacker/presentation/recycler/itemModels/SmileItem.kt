package com.trifcdr.feedbacker.presentation.recycler.itemModels

import com.trifcdr.lifestylehub.presentation.recycler.itemModels.StringId

data class SmileItem(
    override val id: String = "smile",
    val questionId: Int,
    val description: String,
    var choice: Int = -2
) : StringId