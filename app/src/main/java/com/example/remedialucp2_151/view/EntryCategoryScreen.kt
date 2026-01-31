package com.example.remedialucp2_151.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remedialucp2_151.R
import com.example.remedialucp2_151.view.route.EntryCategory
import com.example.remedialucp2_151.viewmodel.CategoryDetail
import com.example.remedialucp2_151.viewmodel.CategoryEntryViewModel
import com.example.remedialucp2_151.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryCategoryScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CategoryEntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BookTopAppBar(
                title = stringResource(EntryCategory.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        EntryCategoryBody(
            uiState = viewModel.uiState,
            onCategoryValueChange = viewModel::updateState,
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
fun EntryCategoryBody(
    uiState: CategoryDetail,
    onCategoryValueChange: (CategoryDetail) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ) {
        FormInputCategory(
            categoryDetail = uiState,
            onValueChange = onCategoryValueChange,
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
fun FormInputCategory(
    categoryDetail: CategoryDetail,
    modifier: Modifier = Modifier,
    onValueChange: (CategoryDetail) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = categoryDetail.name,
            onValueChange = { onValueChange(categoryDetail.copy(name = it)) },
            label = { Text(stringResource(R.string.category_name)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(text = stringResource(R.string.required_field), modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium)))
        }
    }
}