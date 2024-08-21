package com.trifcdr.feedbacker.presentation.recycler.itemModels

import com.trifcdr.lifestylehub.presentation.recycler.itemModels.StringId

data class RangeItem(
    override val id: String = "range",
    val questionId: Int,
    val description: String,
    var value: Int = -2
) : StringId