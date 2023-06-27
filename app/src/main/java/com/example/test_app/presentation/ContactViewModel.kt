package com.example.test_app.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_app.domain.contact.Contact
import com.example.test_app.domain.repository.ContactRepository
import com.example.test_app.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val repository: ContactRepository,
): ViewModel() {

    var state by mutableStateOf(ContactState())
        private set


    fun loadContacts(context: Context) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )
            when (val result = repository.getContact(context)) {
                is Resource.Success -> {
                    state = state.copy(
                        contacts = result.data,
                        isLoading = false,
                        error = null,
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        contacts = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun getContactDetail(id: String){
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )
            when(val result = repository.getContactDetail(id)) {
                is Resource.Success -> {
                    state = state.copy(
                        contact = result.data,
                        isLoading = false,
                        error = null,
                    )

                }

                is Resource.Error -> {
                    state = state.copy(
                        contacts = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun updateContact(id:String, newData :Contact){
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null,
            )
            when(val result = repository.updateContact(id,newData)) {
                is Resource.Success -> {
                    val contacts = result.data?.get("contacts") as List<Contact>
                    val isUpdatingNewData = result.data?.get("isSuccess") as Boolean
                    state = state.copy(
                        isUpdatingNewData = isUpdatingNewData ,
                        isLoading = false,
                        error = null,
                        contacts = contacts
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        isUpdatingNewData = false,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}
