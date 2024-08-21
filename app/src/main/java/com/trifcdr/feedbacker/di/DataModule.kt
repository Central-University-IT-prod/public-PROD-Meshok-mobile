package com.trifcdr.feedbacker.di

import android.content.Context
import com.trifcdr.data.network.api.UserApi
import com.trifcdr.data.network.api.UserApiImpl
import com.trifcdr.data.network.ktor.FeedbackerHttpClient
import com.trifcdr.data.repository.UserRepositoryImpl
import com.trifcdr.data.storage.AppStorage
import com.trifcdr.data.storage.AppStorageImpl
import com.trifcdr.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    private val httpClient = FeedbackerHttpClient().getHttpClient()

    @Provides
    @Singleton
    fun provideUserApi(): UserApi {
        return UserApiImpl(
            httpClient
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserApi,
        appStorage: AppStorage
    ): UserRepository {
        return UserRepositoryImpl(
            userApi,
            appStorage
        )
    }

    @Provides
    @Singleton
    fun provideAppStorage(@ApplicationContext context: Context): AppStorage {
        return AppStorageImpl(context = context)
    }

}