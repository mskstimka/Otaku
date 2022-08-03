package com.example.rxjava.utils

import android.annotation.SuppressLint
import android.widget.SearchView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object RxSearchObservable {
    @SuppressLint("CheckResult")
    fun fromView(searchView: androidx.appcompat.widget.SearchView): Observable<String> {
        return Observable.create<String> { observable ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextChange(p0: String?): Boolean {
                    if (!observable.isDisposed) {
                        observable.onNext(p0.toString())
                    }
                    return false
                }

                override fun onQueryTextSubmit(p0: String?): Boolean {
                    if (!observable.isDisposed) {
                        observable.onNext(p0.toString())
                    }
                    return false
                }
            })
        }
            .debounce(TIMEOUT, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


        const val TIMEOUT = 300L

}

