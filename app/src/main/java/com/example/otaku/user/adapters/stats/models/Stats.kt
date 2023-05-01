package com.example.otaku.user.adapters.stats.models

import android.graphics.Color
import com.example.domain.models.user.RateStatus

data class Stats(val count: Int, val type: RateStatus, val color: Int)