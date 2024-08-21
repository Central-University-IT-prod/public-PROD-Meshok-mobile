package com.trifcdr.feedbacker.presentation.recycler.holders

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.trifcdr.feedbacker.R
import com.trifcdr.feedbacker.databinding.ItemHistoryBinding
import com.trifcdr.feedbacker.presentation.recycler.itemModels.HistoryItem
import com.trifcdr.lifestylehub.presentation.recycler.listener.CustomItemClickListener
import com.trifcdr.lifestylehub.presentation.recycler.visitor.ViewHolderVisitor

class HistoryHolder : ViewHolderVisitor {
    override val layout: Int = R.layout.item_history
    override fun bind(
        binding: ViewDataBinding,
        item: Any,
        context: Context,
        clickListener: CustomItemClickListener?
    ) {
        with(binding as ItemHistoryBinding) {
            history = item as HistoryItem
            descriptionTv.text = item.description
            when (item.type) {
                "Smile" -> {
                    smileDrawable.visibility = View.VISIBLE
                    when (item.value) {
                        -1 -> {
                            smileDrawable.background =
                                ContextCompat.getDrawable(context, R.drawable.fluentemojiangryface)
                        }

                        0 -> {
                            smileDrawable.background = ContextCompat.getDrawable(
                                context,
                                R.drawable.fluentemojineutralface
                            )
                        }

                        1 -> {
                            smileDrawable.background = ContextCompat.getDrawable(
                                context,
                                R.drawable.fluentemojibeamingfacewithsmilingeyes
                            )
                        }
                    }
                }

                "Range" -> {
                    rateTv.visibility = View.VISIBLE
                    digitTv.text = item.value.toString()
                }
            }
        }
    }


    override fun acceptBinding(item: Any): Boolean = item is HistoryItem
}