package com.example.ecommerce.file.domain.file.exception

import com.example.ecommerce.global.exception.HttpStatusException
import org.springframework.http.HttpStatus

class UploadFailByUnknownReasonException : HttpStatusException(
    HttpStatus.INTERNAL_SERVER_ERROR,
    FileErrorCode.UPLOAD_FAIL_BY_UNKNOWN_REASON
)