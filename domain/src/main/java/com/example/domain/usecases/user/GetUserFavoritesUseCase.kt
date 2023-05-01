package com.example.domain.usecases.user

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetUserFavoritesUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(id: Long) = userRepository.getUserFavourites(id = id)
}