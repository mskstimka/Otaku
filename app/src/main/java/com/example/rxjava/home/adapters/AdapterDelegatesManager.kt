package com.example.rxjava.home.adapters

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView

class AdapterDelegatesManager<T>(vararg delegates: AdapterDelegate<T>) {
    private val delegates: SparseArrayCompat<AdapterDelegate<T>> = SparseArrayCompat()

    init {
        for (element in delegates) {
            addDelegate(element)
        }
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegate: AdapterDelegate<T> = delegates[viewType]
            ?: throw error("On create View Holder error")
        return delegate.onCreateViewHolder(parent)
    }

    fun onBindViewHolder(items: List<T>, position: Int, holder: RecyclerView.ViewHolder) {
        val delegate: AdapterDelegate<T> =
            delegates[holder.itemViewType] ?: throw error("On bind view holder error")
        delegate.onBindViewHolder(holder, items, position)
    }

    private fun addDelegate(delegate: AdapterDelegate<T>) {
        var viewType = delegates.size()
        while (delegates[viewType] != null) {
            viewType++
        }
        delegates.put(viewType, delegate)
    }

    fun getItemViewType(items: List<T>, position: Int): Int {
        val delegatesCount = delegates.size()
        for (i in 0 until delegatesCount) {
            val delegate: AdapterDelegate<T> = delegates.valueAt(i)
            if (delegate.isForViewType(items, position)) {
                return delegates.keyAt(i)
            }
        }
        throw error("Not found item View Type!")
    }
}