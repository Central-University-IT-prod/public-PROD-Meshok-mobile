package com.trifcdr.domain.models

sealed class DataResource<out R> {
    data object Unauthorized: DataResource<Nothing>()
    data object Authorized: DataResource<Nothing>()
    data object Forbidden: DataResource<Nothing>()
    data object Empty: DataResource<Nothing>()
    data object BadRequest: DataResource<Nothing>()
    data object ValidationError: DataResource<Nothing>()
    data class Success<out R>(val result: R): DataResource<R>()
    data class Failure(val exception: Exception): DataResource<Nothing>()
}