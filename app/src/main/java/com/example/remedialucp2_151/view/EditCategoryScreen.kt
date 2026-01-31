package com.example.remedialucp2_151.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remedialucp2_151.R
import com.example.remedialucp2_151.view.route.EditCategory
import com.example.remedialucp2_151.viewmodel.CategoryEditViewModel
import com.example.remedialucp2_151.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CategoryEditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            BookTopAppBar(
                title = stringResource(EditCategory.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EntryCategoryBody(
            uiState = viewModel.uiState,
            onCategoryValueChange = viewModel::updateState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.update(onDone = navigateBack)
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )

        // Dialog Opsi Hapus Dinamis
        var deleteDialogRequired by rememberSaveable { mutableStateOf(false) }
        if (deleteDialogRequired) {
            DeleteCategoryDialog(
                onDeleteConfirm = { deleteBooks ->
                    coroutineScope.launch {
                        viewModel.deleteWithOptions(deleteBooks, onDone = navigateBack)
                    }
                    deleteDialogRequired = false
                },
                onDeleteCancel = { deleteDialogRequired = false }
            )
        }

        // Trigger dialog (misalnya, button di UI)
        OutlinedButton(
            onClick = { deleteDialogRequired = true },
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(stringResource(R.string.delete_category))
        }
    }
}

@Composable
private fun DeleteCategoryDialog(
    onDeleteConfirm: (Boolean) -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(R.string.delete_category)) },
        text = { Text(stringResource(R.string.choose_delete_option)) },
        modifier = modifier,
        confirmButton = {
            TextButton(onClick = { onDeleteConfirm(true) }) {
                Text(stringResource(R.string.delete_books))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDeleteConfirm(false) }) {
                Text(stringResource(R.string.set_no_category))
            }
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}