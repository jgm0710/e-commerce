package com.example.ecommerce.global.exception

abstract class AbstractNotFoundException(
    errorCode: ErrorCode,
) : HttpStatusException(
    errorCode = errorCode,
)
