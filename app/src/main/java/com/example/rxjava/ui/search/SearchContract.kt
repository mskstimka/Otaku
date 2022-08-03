package com.example.rxjava.ui.search

import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity

interface SearchContract {
    interface View<T> {
        fun initView()
        fun updateViewData(result: Results<T>)
    }

    interface Presenter {
        fun getAnimePostersFromSearch(searchName: String)
    }
}