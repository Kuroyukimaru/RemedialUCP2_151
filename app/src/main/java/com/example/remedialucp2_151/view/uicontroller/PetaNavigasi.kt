package com.example.remedialucp2_151.view.uicontroller


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.remedialucp2_151.repository.AplikasiBuku
import com.example.remedialucp2_151.viewmodel.*

object PenyediaViewModel {
    val Factory = viewModelFactory {

        initializer {
            BookListViewModel(aplikasiBuku().container.bookRepository)
        }

        initializer {
            BookEntryViewModel(
                aplikasiBuku().container.bookRepository,
                aplikasiBuku().container.auditRepository
            )
        }

        initializer {
            BookEditViewModel(
                aplikasiBuku().container.bookRepository,
                aplikasiBuku().container.auditRepository
            )
        }


        initializer {
            CategoryListViewModel(aplikasiBuku().container.categoryRepository)
        }

        initializer {
            CategoryEntryViewModel(
                aplikasiBuku().container.categoryRepository,
                aplikasiBuku().container.auditRepository
            )
        }

        initializer {
            CategoryEditViewModel(
                aplikasiBuku().container.categoryRepository,
                aplikasiBuku().container.auditRepository
            )
        }


        initializer {
            AuditListViewModel(aplikasiBuku().container.auditRepository)
        }

    }
}

fun CreationExtras.aplikasiBuku(): AplikasiBuku =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiBuku)