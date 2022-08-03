package com.example.rxjava.search.ui

import com.example.a16_rxjava_domain.common.Results

interface SearchContract {
    interface View<T> {
        fun updateViewData(result: Results<T>)
    }

    interface Presenter {
        fun getAnimePostersFromSearch(searchName: String)
    }
}