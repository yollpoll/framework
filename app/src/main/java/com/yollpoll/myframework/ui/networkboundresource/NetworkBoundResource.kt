package com.yollpoll.myframework.ui.networkboundresource

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

/**
 * Created by spq on 2021/1/25
 */
// ResultType: Type for the Resource data.
// RequestType: Type for the API response.
abstract class NetworkBoundResource<ResultType, RequestType> {
    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract fun shouldFetch(data: ResultType): Boolean

    // Called to get the cached data from the database.
    @MainThread
    protected abstract fun loadFromDb():LiveData<ResultType>

    // Called to create the API call.
    @MainThread
    protected abstract fun createCall():LiveData<RequestType>

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    protected open fun onFetchFailed() {}

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    fun asLiveData(): LiveData<ResultType> = TODO()
}