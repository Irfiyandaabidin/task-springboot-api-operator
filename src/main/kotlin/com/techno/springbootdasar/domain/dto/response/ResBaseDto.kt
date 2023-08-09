package com.techno.springbootdasar.domain.dto.response

data class ResBaseDto<T>(
    val status : Boolean = true,
    val message : String = "success",
    val data : T? = null
)
