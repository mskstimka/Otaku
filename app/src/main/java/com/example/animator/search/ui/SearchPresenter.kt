package com.example.animator.search.ui

import android.annotation.SuppressLint
import com.example.animator_domain.common.Results
import com.example.animator_domain.models.poster.AnimePosterEntity
import com.example.animator_domain.usecases.GetAnimePostersFromSearchUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception

class SearchPresenter(
    private val getAnimePostersFromSearchUseCase: GetAnimePostersFromSearchUseCase,
    private val view: SearchContract.View<List<AnimePosterEntity>>
) : SearchContract.Presenter {

    private val disposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getAnimePostersFromSearch(searchName: String) {
        if (searchName != "") {
            disposable.add(
                getAnimePostersFromSearchUseCase
                    .execute(searchName)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        view.updateViewData(Results.Success(it))
                    }, { throwable ->
                        view.updateViewData(Results.Error(Exception(throwable)))
                    })
            )
        } else {
            view.updateViewData(Results.Success(emptyList<AnimePosterEntity>().toMutableList()))
        }
    }

    fun clearDisposable() {
        disposable.clear()
    }
}