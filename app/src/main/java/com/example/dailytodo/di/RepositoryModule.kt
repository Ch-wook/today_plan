package com.example.dailytodo.di

import com.example.dailytodo.data.repository.AlarmRepositoryImpl
import com.example.dailytodo.data.repository.TodoRepositoryImpl
import com.example.dailytodo.domain.repository.AlarmRepository
import com.example.dailytodo.domain.repository.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTodoRepository(impl: TodoRepositoryImpl): TodoRepository

    @Binds
    @Singleton
    abstract fun bindAlarmRepository(impl: AlarmRepositoryImpl): AlarmRepository
}
