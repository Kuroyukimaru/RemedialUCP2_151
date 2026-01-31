package com.example.remedialucp2_151.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remedialucp2_151.R
import com.example.remedialucp2_151.room.BookEntity
import com.example.remedialucp2_151.view.route.ListBook
import com.example.remedialucp2_151.viewmodel.BookListViewModel
import com.example.remedialucp2_151.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListBookScreen(
    navigateToEntry: () -> Unit,
    navigateToUpdate: (Long) -> Unit,
    navigateToCategory: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookListViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BookTopAppBar(
                title = stringResource(ListBook.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateToCategory
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.entry_book)
                )
            }
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        BodyListBook(
            listBooks = uiState.listBooks,
            onBookClick = { navigateToUpdate(it.id) },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun BodyListBook(
    listBooks: List<BookEntity>,
    onBookClick: (BookEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (listBooks.isEmpty()) {
            Text(
                text = stringResource(R.string.no_books),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            ListBooks(
                listBooks = listBooks,
                onBookClick = onBookClick
            )
        }
    }
}

@Composable
fun ListBooks(
    listBooks: List<BookEntity>,
    onBookClick: (BookEntity) -> Unit
) {
    LazyColumn {
        items(items = listBooks, key = { it.id }) { book ->
            ItemBook(
                book = book,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onBookClick(book) }
            )
        }
    }
}

@Composable
fun ItemBook(
    book: BookEntity,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))) {
            Text(text = book.title, style = MaterialTheme.typography.titleLarge)
        }
    }
}