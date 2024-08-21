package com.trifcdr.lifestylehub.presentation.recycler.visitor

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.trifcdr.lifestylehub.presentation.recycler.listener.CustomItemClickListener

/**
 * Created by trifcdr.
 */
interface ViewHolderVisitor {
    val layout: Int

    fun bind(binding: ViewDataBinding, item: Any, context: Context, clickListener: CustomItemClickListener?)
    fun acceptBinding(item: Any): Boolean

}