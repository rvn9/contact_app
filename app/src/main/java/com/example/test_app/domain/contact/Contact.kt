package com.example.test_app.domain.contact

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class Contact(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String?,
    val dob: String?
)
