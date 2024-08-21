package com.trifcdr.lifestylehub.presentation.recycler.listener

/**
 * Created by trifcdr.
 */
class CustomItemClickListener(
    val clickListener: (itemId: String, id: Long?, buttonType: Int?) -> Unit
) {
    fun onClick(itemId: String, id: Long? = null, buttonType: Int? = null) = clickListener(itemId, id, buttonType)
}