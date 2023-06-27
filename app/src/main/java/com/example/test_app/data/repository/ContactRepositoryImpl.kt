package com.example.test_app.data.repository

import android.content.Context
import com.example.test_app.domain.contact.Contact
import com.example.test_app.domain.repository.ContactRepository
import com.example.test_app.domain.util.Resource
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Inject


class ContactRepositoryImpl @Inject constructor(): ContactRepository {
    val savedContacts = ArrayList<Contact?>()
    override suspend fun getContact(context: Context): Resource<List<Contact>> {
        return try {
            val contacts = getContactsAssets(context)
            if(contacts != null) {
                savedContacts.addAll(contacts)
            }
            Resource.Success(
                data = contacts
            )

        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getContactDetail(id: String): Resource<Contact> {
        return try {
            Resource.Success(
                data = savedContacts.single { it?.id == id }
            )

        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun updateContact(id: String, newData: Contact): Resource<Map<String,Any>> {
        return try {
            var index = savedContacts.indexOfFirst { it?.id == id }
            val newContact = savedContacts[index]?.copy(
                id,
                newData.firstName,
                newData.lastName,
                newData.email,
                newData.dob
            )
            savedContacts[index] = newContact
            val mapWithValues = mapOf("isSuccess" to true, "contacts" to savedContacts)
            Resource.Success(
                data = mapWithValues
            )

        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }


    private fun getContactsAssets(context: Context): List<Contact>? {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val listType = Types.newParameterizedType(List::class.java, Contact::class.java)
        val adapter: JsonAdapter<List<Contact>> = moshi.adapter(listType)
        val myJson = context.assets.open("data.json").bufferedReader().use{ it.readText()}
        return adapter.fromJson(myJson)
    }
}