package com.example.moviesapp.util

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundSource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading())

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error("$throwable", it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}