package com.example.diario.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diario.data.model.Recuerdo
import com.example.diario.ui.components.RecuerdoCard
import com.example.diario.viewModel.RecuerdoViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: RecuerdoViewModel,
    onRecuerdoClick: (Recuerdo) -> Unit,
    onAddRecuerdoClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddRecuerdoClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir Recuerdo")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = "Mis Recuerdos",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(all = 16.dp)
            ) {
                items(viewModel.recuerdos) { recuerdo ->
                    RecuerdoCard(
                        recuerdo = recuerdo,
                        onClick = { onRecuerdoClick(recuerdo) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}