package com.example.rxjava.ui.search

import android.annotation.SuppressLint
import com.example.a16_rxjava_domain.common.Results
import com.example.a16_rxjava_domain.models.poster.AnimePosterEntity
import com.example.a16_rxjava_domain.usecases.GetAnimePostersFromSearchUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception
import javax.inject.Inject

class SearchPresenter @Inject constructor(
    private val getAnimePostersFromSearchUseCase: GetAnimePostersFromSearchUseCase,
    private val view: SearchContract.View
) : SearchContract.Presenter {

    override var list = mutableListOf<AnimePosterEntity>()

    @SuppressLint("CheckResult")
    override fun loadData(search: String) {
        if (search != "") {
                getAnimePostersFromSearchUseCase
                    .execute(search)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        list.apply {
                            clear()
                            addAll(it)
                        }
                        view.updateViewData(Results.Success(list))
                    }, { throwable ->
                        view.updateViewData(Results.Error(Exception(throwable)))
                    })
        } else {
            list.clear()
        }

    }

}