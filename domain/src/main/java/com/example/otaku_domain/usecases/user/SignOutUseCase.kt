package com.example.otaku_domain.usecases.user

import com.example.otaku_domain.common.Results
import com.example.otaku_domain.repository.UserRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(): Results<String> = userRepository.signOut()
}