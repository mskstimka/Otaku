package com.example.otaku.user.adapters.stats.models

import com.example.otaku_domain.models.user.status.RateStatus

data class Stats(val count: Int, val type: RateStatus, val color: Int)