package com.example.dailytodo.domain.usecase.todo

import com.example.dailytodo.domain.model.Supply
import com.example.dailytodo.domain.repository.TodoRepository
import javax.inject.Inject

class ToggleSupplyCheckUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(supply: Supply) {
        repository.updateSupply(supply.copy(isChecked = !supply.isChecked))
    }
}
