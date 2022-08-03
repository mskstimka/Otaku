package com.example.rxjava.ui.search

import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity

interface SearchContract {
    interface View {
        fun initView()
        fun updateViewData(result: Results<*>)
    }

    interface Presenter {
        fun getAnimePostersFromSearch(searchName: String)
    }
}