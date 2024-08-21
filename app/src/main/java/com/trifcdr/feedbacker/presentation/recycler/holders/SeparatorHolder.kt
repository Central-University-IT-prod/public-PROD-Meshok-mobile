package com.trifcdr.feedbacker.presentation.recycler.holders

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.trifcdr.feedbacker.R
import com.trifcdr.feedbacker.databinding.ItemSeparatorBinding
import com.trifcdr.feedbacker.presentation.recycler.itemModels.SeparatorItem
import com.trifcdr.lifestylehub.presentation.recycler.listener.CustomItemClickListener
import com.trifcdr.lifestylehub.presentation.recycler.visitor.ViewHolderVisitor

class SeparatorHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.item_separator
    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        context: Context,
        clickListener: CustomItemClickListener?
    ) {
        with(binding as ItemSeparatorBinding) {
            separator = item as SeparatorItem
            separatorTv.text = item.text

        }
    }


    override fun acceptBinding(item: Any): Boolean = item is SeparatorItem
}