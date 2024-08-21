package com.trifcdr.feedbacker.di

import com.trifcdr.domain.repository.UserRepository
import com.trifcdr.domain.usecase.AuthUserUseCase
import com.trifcdr.domain.usecase.GetFormUseCase
import com.trifcdr.domain.usecase.GetUserDataUseCase
import com.trifcdr.domain.usecase.GetUserHistoryUseCase
import com.trifcdr.domain.usecase.IsUserLoginUseCase
import com.trifcdr.domain.usecase.LogoutUserUseCase
import com.trifcdr.domain.usecase.RegisterUserUseCase
import com.trifcdr.domain.usecase.SendFormUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {


    @Provides
    fun provideRegisterUserUseCase(repository: UserRepository): RegisterUserUseCase {
        return RegisterUserUseCase(
            userRepository = repository
        )
    }
    @Provides
    fun provideGetUserDataUseCase(repository: UserRepository): GetUserDataUseCase {
        return GetUserDataUseCase(
            userRepository = repository
        )
    }
    @Provides
    fun provideAuthUserUseCase(repository: UserRepository): AuthUserUseCase {
        return AuthUserUseCase(
            userRepository = repository
        )
    }
    @Provides
    fun provideGetFormUseCase(repository: UserRepository): GetFormUseCase {
        return GetFormUseCase(
            userRepository = repository
        )
    }
    @Provides
    fun provideSendFormUseCase(repository: UserRepository): SendFormUseCase {
        return SendFormUseCase(
            userRepository = repository
        )
    }
    @Provides
    fun provideGetUserHistoryUseCase(repository: UserRepository): GetUserHistoryUseCase {
        return GetUserHistoryUseCase(
            userRepository = repository
        )
    }
    @Provides
    fun provideIsUserLoginUseCase(repository: UserRepository): IsUserLoginUseCase {
        return IsUserLoginUseCase(
            userRepository = repository
        )
    }
    @Provides
    fun provideLogoutUseCase(repository: UserRepository): LogoutUserUseCase {
        return LogoutUserUseCase(
            repository = repository
        )
    }
}