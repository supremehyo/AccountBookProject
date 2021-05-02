package com.supremehyo.mvvmProject.ViewModel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseKotlinViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable();

    fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}