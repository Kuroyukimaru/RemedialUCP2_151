package com.example.remedialucp2_151.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.remedialucp2_151.room.AuditLogEntity
import com.example.remedialucp2_151.view.route.ListAudit
import com.example.remedialucp2_151.viewmodel.AuditListViewModel
import com.example.remedialucp2_151.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAuditScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuditListViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BookTopAppBar(
                title = stringResource(ListAudit.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        BodyListAudit(
            listLogs = uiState.listLogs,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun BodyListAudit(
    listLogs: List<AuditLogEntity>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (listLogs.isEmpty()) {
            Text(
                text = stringResource(R.string.no_audit_logs),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            ListAuditLogs(
                listLogs = listLogs
            )
        }
    }
}

@Composable
fun ListAuditLogs(
    listLogs: List<AuditLogEntity>
) {
    LazyColumn {
        items(items = listLogs, key = { it.id }) { log ->
            ItemAuditLog(
                log = log,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun ItemAuditLog(
    log: AuditLogEntity,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))) {
            Text(text = "${log.operation} on ${log.tableName}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Timestamp: ${log.timestamp}", style = MaterialTheme.typography.bodyMedium)
            if (log.oldData != null) {
                Text(text = "Old Data: ${log.oldData}", style = MaterialTheme.typography.bodySmall)
            }
            if (log.newData != null) {
                Text(text = "New Data: ${log.newData}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}