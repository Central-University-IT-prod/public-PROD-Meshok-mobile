package com.trifcdr.data.network.models

sealed class Resource<out R> {

    data object Unauthorized: Resource<Nothing>()
    data object Authorized: Resource<Nothing>()
    data object Forbidden: Resource<Nothing>()
    data object BadRequest: Resource<Nothing>()
    data object ValidationError: Resource<Nothing>()
    data object Empty: Resource<Nothing>()

    data class Success<out R>(val result: R): Resource<R>()
    data class Failure(val exception: Exception): Resource<Nothing>()
}