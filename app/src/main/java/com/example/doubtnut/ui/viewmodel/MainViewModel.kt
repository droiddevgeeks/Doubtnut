package com.example.doubtnut.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doubtnut.common.Event
import com.example.doubtnut.model.Article
import com.example.doubtnut.usecase.GetNewsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import io.reactivex.schedulers.Schedulers


class MainViewModel @Inject constructor(private val useCase: GetNewsUseCase) :
    ViewModel() {

    private val _articleLiveData by lazy { MutableLiveData<Event<List<Article>>>() }
    val articleLiveData: LiveData<Event<List<Article>>> by lazy { _articleLiveData }


    var loadingState = MutableLiveData<Boolean>()

    private val _apiError by lazy { MutableLiveData<Event<Throwable>>() }
    val apiError: LiveData<Event<Throwable>> by lazy { _apiError }

    private val disposable by lazy { CompositeDisposable() }

    fun fetchNewsData(country: String) {
        val issueDisposable = useCase.getNewsData(country)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingState.value = true }
            .doOnEvent { _, _ -> loadingState.value = false }
            .doOnError { loadingState.value = false }
            .subscribe(
                { it.articles?.let { list -> Event(list).run(_articleLiveData::postValue) } }
                ,
                { Event(it).run(_apiError::postValue) }
            )
        disposable.add(issueDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}