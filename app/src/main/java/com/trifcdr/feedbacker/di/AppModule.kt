package com.trifcdr.feedbacker.di

import com.trifcdr.feedbacker.presentation.recycler.holders.HistoryHolder
import com.trifcdr.feedbacker.presentation.recycler.holders.RangeHolder
import com.trifcdr.feedbacker.presentation.recycler.holders.SeparatorHolder
import com.trifcdr.feedbacker.presentation.recycler.holders.SmileHolder
import com.trifcdr.lifestylehub.presentation.recycler.managers.ViewHoldersManager
import com.trifcdr.lifestylehub.presentation.recycler.managers.ViewHoldersManagerImpl
import com.trifcdr.lifestylehub.presentation.recycler.types.ItemTypes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAdaptersManager(): ViewHoldersManager = ViewHoldersManagerImpl().apply {
        registerViewHolder(ItemTypes.SMILE, SmileHolder())
        registerViewHolder(ItemTypes.RANGE, RangeHolder())
        registerViewHolder(ItemTypes.HISTORY, HistoryHolder())
        registerViewHolder(ItemTypes.SEPARATOR, SeparatorHolder())
    }
}