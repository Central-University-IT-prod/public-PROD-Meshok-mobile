package com.trifcdr.feedbacker.presentation.recycler.holders

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.google.android.material.slider.Slider
import com.trifcdr.feedbacker.R
import com.trifcdr.feedbacker.databinding.ItemRangeBinding
import com.trifcdr.feedbacker.presentation.recycler.itemModels.RangeItem
import com.trifcdr.lifestylehub.presentation.recycler.listener.CustomItemClickListener
import com.trifcdr.lifestylehub.presentation.recycler.visitor.ViewHolderVisitor

class RangeHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.item_range
    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        context: Context,
        clickListener: CustomItemClickListener?
    ) {
        with(binding as ItemRangeBinding) {
            range = item as RangeItem
            descriptionTv.text = item.description
            val slide: Slider = rangeSlider
            slide.addOnChangeListener { slider, value, fromUser ->
                item.value = value.toInt()
            }
        }
    }


    override fun acceptBinding(item: Any): Boolean = item is RangeItem
}
