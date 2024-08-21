package com.trifcdr.feedbacker.presentation.recycler.itemModels

import com.trifcdr.lifestylehub.presentation.recycler.itemModels.StringId

data class SeparatorItem(
    override val id: String = "separator",
    val text: String
) : StringId