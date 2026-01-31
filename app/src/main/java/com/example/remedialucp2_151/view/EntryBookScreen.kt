package com.example.remedialucp2_151.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remedialucp2_151.R
import com.example.remedialucp2_151.view.route.EntryBook
import com.example.remedialucp2_151.viewmodel.BookEntryViewModel
import com.example.remedialucp2_151.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBookScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookEntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BookTopAppBar(
                title = stringResource(EntryBook.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        EntryBookBody(
            uiState = viewModel.uiState,
            onBookValueChange = viewModel::updateState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insert(onDone = navigateBack)
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBookBody(
    uiState: BookDetail,
    onBookValueChange: (BookDetail) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ) {
        FormInputBook(
            bookDetail = uiState,
            onValueChange = onBookValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = uiState.isValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth().padding(bottom = dimensionResource(id = R.dimen.padding_large))
        ) {
            Text(stringResource(R.string.btn_submit))
        }
    }
}

@Composable
fun FormInputBook(
    bookDetail: BookDetail,
    modifier: Modifier = Modifier,
    onValueChange: (BookDetail) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = bookDetail.title,
            onValueChange = { onValueChange(bookDetail.copy(title = it)) },
            label = { Text(stringResource(R.string.book_title)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(text = stringResource(R.string.required_field), modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium)))
        }
    }
}