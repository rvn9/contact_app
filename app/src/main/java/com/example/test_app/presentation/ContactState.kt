package com.example.test_app.presentation

import com.example.test_app.domain.contact.Contact

data class ContactState(
    val contacts: List<Contact>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val contact: Contact? = null,
    val isUpdatingNewData: Boolean = false,
)
