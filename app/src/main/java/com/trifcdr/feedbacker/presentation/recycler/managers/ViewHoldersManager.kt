package com.trifcdr.lifestylehub.presentation.recycler.managers

import com.trifcdr.lifestylehub.presentation.recycler.visitor.ViewHolderVisitor

/**
 * Created by trifcdr.
 */
interface ViewHoldersManager {

    fun registerViewHolder(type: Int, viewHolder: ViewHolderVisitor)

    fun getViewHolder(itemType: Int): ViewHolderVisitor

    fun getItemType(item: Any): Int
}