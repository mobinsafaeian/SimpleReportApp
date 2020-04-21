package mobinsafaeian.devipe.entrance.utils

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.async(): Single<T> = observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())