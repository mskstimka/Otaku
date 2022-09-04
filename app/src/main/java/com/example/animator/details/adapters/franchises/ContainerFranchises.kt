package com.example.animator.details.adapters.franchises

import androidx.annotation.Keep
import com.example.animator_domain.models.details.franchise.AnimeDetailsFranchisesEntity
import java.util.*

@Keep
data class ContainerFranchises(
    val list: List<AnimeDetailsFranchisesEntity>,
    val id: String = UUID.randomUUID().toString()
)