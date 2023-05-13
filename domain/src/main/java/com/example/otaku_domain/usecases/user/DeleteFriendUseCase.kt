package com.example.otaku_domain.usecases.user

import com.example.otaku_domain.common.Results
import com.example.otaku_domain.repository.UserRepository
import javax.inject.Inject

class DeleteFriendUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute(id: Long): Results<String> = userRepository.deleteFriend(id = id)
}