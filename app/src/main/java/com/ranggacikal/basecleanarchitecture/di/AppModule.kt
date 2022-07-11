package com.ranggacikal.basecleanarchitecture.di

import com.ranggacikal.basecleanarchitecture.common.Constants
import com.ranggacikal.basecleanarchitecture.data.remote.ApiInterface
import com.ranggacikal.basecleanarchitecture.data.repository.UserRepositoryImpl
import com.ranggacikal.basecleanarchitecture.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiInterface(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun providesUserRepository(api: ApiInterface) : UserRepository {
        return UserRepositoryImpl(api)
    }
}