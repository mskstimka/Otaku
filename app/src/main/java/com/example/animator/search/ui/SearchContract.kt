package com.example.animator.search.ui

import com.example.animator_domain.common.Results

interface SearchContract {
    interface View<T> {
        fun updateViewData(result: Results<T>)
    }

    interface Presenter {
        fun getAnimePostersFromSearch(searchName: String)
    }
}