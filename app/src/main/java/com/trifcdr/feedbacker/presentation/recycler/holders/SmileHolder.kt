package com.trifcdr.feedbacker.presentation.recycler.holders

import android.content.Context
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageButton
import androidx.databinding.ViewDataBinding
import com.trifcdr.feedbacker.R
import com.trifcdr.feedbacker.databinding.ItemSmileBinding
import com.trifcdr.feedbacker.presentation.recycler.itemModels.SmileItem
import com.trifcdr.lifestylehub.presentation.recycler.listener.CustomItemClickListener
import com.trifcdr.lifestylehub.presentation.recycler.visitor.ViewHolderVisitor

class SmileHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.item_smile
    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        context: Context,
        clickListener: CustomItemClickListener?
    ) {
        with(binding as ItemSmileBinding) {
            smile = item as SmileItem
            descriptionTv.text = item.description
            funnyBtn.setOnClickListener {
                chooseButton(funnyBtn, context)
                resetButton(neutralBtn, angryBtn, context)
                item.choice = 1
            }
            angryBtn.setOnClickListener {
                chooseButton(angryBtn, context)
                resetButton(neutralBtn, funnyBtn, context)
                item.choice = -1

            }
            neutralBtn.setOnClickListener {
                chooseButton(neutralBtn, context)
                resetButton(angryBtn, funnyBtn, context)
                item.choice = 0
            }
        }
    }

    private fun chooseButton(button: ImageButton, context: Context) {
        val newSize = 80 * context.resources.displayMetrics.density.toInt()
        button.animate()
            .scaleX(1.2F)
            .scaleY(1.2F)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(300)
    }

    private fun resetButton(button1: ImageButton, button2: ImageButton, context: Context) {
//        val newSize = 50 * context.resources.displayMetrics.density.toInt()
//        val params = FrameLayout.LayoutParams(newSize, newSize)
//        params.gravity = Gravity.CENTER
//        button1.layoutParams = params
//        button2.layoutParams = params
        button1.animate()
            .scaleX(0.8F)
            .scaleY(0.8F)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(300)
        button2.animate()
            .scaleX(0.8F)
            .scaleY(0.8F)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(300)
    }

    override fun acceptBinding(item: Any): Boolean = item is SmileItem
}
