package com.trifcdr.lifestylehub.presentation.recycler.managers

import com.trifcdr.lifestylehub.presentation.recycler.types.ItemTypes
import com.trifcdr.lifestylehub.presentation.recycler.visitor.ViewHolderVisitor

class ViewHoldersManagerImpl : ViewHoldersManager {

    private val holdersMap = emptyMap<Int, ViewHolderVisitor>().toMutableMap()

    override fun registerViewHolder(type: Int, viewHolder: ViewHolderVisitor) {
        holdersMap += type to viewHolder
    }

    override fun getViewHolder(itemType: Int) = holdersMap[itemType] ?: throw TypeCastException("Unknown recycler type of item")


    override fun getItemType(item: Any): Int {
        holdersMap.forEach { (type, holder) ->
            if(holder.acceptBinding(item)) return type
        }
        return ItemTypes.UNKNOWN
    }
}