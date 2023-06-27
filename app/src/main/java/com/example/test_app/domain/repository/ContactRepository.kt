package com.example.test_app.domain.repository

import android.content.Context
import com.example.test_app.domain.contact.Contact
import com.example.test_app.domain.util.Resource

interface ContactRepository {
    suspend fun getContact(context: Context): Resource<List<Contact>>
    suspend fun getContactDetail(id: String): Resource<Contact>
    suspend fun updateContact(id:String, newData: Contact): Resource<Map<String,Any>>
}