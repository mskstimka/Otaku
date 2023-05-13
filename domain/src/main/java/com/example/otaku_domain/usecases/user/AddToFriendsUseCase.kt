package com.example.otaku_domain.usecases.user

import com.example.otaku_domain.common.Results
import com.example.otaku_domain.repository.UserRepository
import javax.inject.Inject

class AddToFriendsUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(id: Long): Results<String> = userRepository.addToFriends(id = id)
}