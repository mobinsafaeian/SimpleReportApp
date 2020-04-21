package mobinsafaeian.devipe.entrance.ui.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import mobinsafaeian.devipe.entrance.model.connections.ApiInterface
import mobinsafaeian.devipe.entrance.model.connections.responses.RollCall
import mobinsafaeian.devipe.entrance.utils.async
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ListViewModel : ViewModel(), KoinComponent {
    private val _data = MutableLiveData<ArrayList<RollCall>>()
    val data: LiveData<ArrayList<RollCall>> = _data
    var progressBarVisibility = MutableLiveData<Int>()
    var message = MutableLiveData<String>()
    private val compositeDisposable = CompositeDisposable()
    private val getApi: ApiInterface by inject()


    init {
        progressBarVisibility.value = View.VISIBLE
    }


    /**
     * Fetch List and Set the Result to LiveData
     */
    fun fetchList() {
        val disposable = getApi.fetchList()
            .async()
            .subscribe({
                if (it.isSuccessful) {
                    progressBarVisibility.value = View.GONE
                    _data.value = it.body()?.list
                }
            },{
                message.value = it.message.toString()
                progressBarVisibility.value = View.GONE
            })

        compositeDisposable.add(disposable)
    }


    /**
     * Dispose Observables to Avoid Crashes
     */
    fun unbind() {
        compositeDisposable.clear()
    }
}