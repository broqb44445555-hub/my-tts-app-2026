package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.util.KhmerNumberConverter
import com.example.ui.TtsViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KhmerNumberScreen(
    viewModel: TtsViewModel,
    onSendToEditor: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val numberInput by viewModel.numberInput.collectAsState()
    val khmerWords = viewModel.getConvertedKhmerNumberWords()
    val khmerDigits = viewModel.getConvertedKhmerDigits()
    val rielWords = viewModel.getConvertedRiel()
    val usdWords = viewModel.getConvertedUsd()
    val yearWords = viewModel.getConvertedYear()
    val phoneWords = viewModel.getConvertedDigitByDigit()

    // Cambodian banknote values and year presets
    val presets = listOf(
        "500" to "៥០០ ៛",
        "1000" to "១,០០០ ៛",
        "5000" to "៥,០០០ ៛",
        "10000" to "១០,០០០ ៛",
        "20000" to "២០,០០០ ៛",
        "50000" to "៥០,០០០ ៛",
        "100000" to "១០០,០០០ ៛",
        "2026" to "ឆ្នាំ ២០២៦",
        "012345678" to "លេខទូរស័ព្ទ"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Card in Khmer Royal Gold/Indigo
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(MaterialTheme.colorScheme.secondary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Payments,
                        contentDescription = "Numbers & Currency",
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "បំលែងលេខ & ប្រាក់រៀលខ្មែរ",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "អានជាអក្សរ ប្រាក់រៀល (៛) ឆ្នាំ និងលេខទូរស័ព្ទ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }

        // Input Field
        OutlinedTextField(
            value = numberInput,
            onValueChange = { viewModel.updateNumberInput(it) },
            label = { Text("បញ្ចូលលេខ ឬ តម្លៃលុយ (Enter Number/Amount)") },
            placeholder = { Text("ឧ. 50000 ឬ ៥០០០០") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("number_input_field"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                if (numberInput.isNotEmpty()) {
                    IconButton(onClick = { viewModel.updateNumberInput("") }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            ),
            singleLine = true
        )

        // Preset Currency Banknotes & Samples
        Text(
            text = "ក្រដាសប្រាក់រៀល & លេខគំរូ:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            presets.forEach { (value, label) ->
                SuggestionChip(
                    onClick = { viewModel.updateNumberInput(value) },
                    label = { Text(label, fontWeight = FontWeight.SemiBold) }
                )
            }
        }

        // Conversion Result Display
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "លទ្ធផលបំលែងជាភាសាខ្មែរ:",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                // 1. Khmer Digits (លេខខ្មែរ)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "លេខខ្មែរ (Khmer Numerals):",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = if (khmerDigits.isBlank()) "-" else khmerDigits,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        IconButton(
                            onClick = { viewModel.copyToClipboard(khmerDigits) },
                            enabled = khmerDigits.isNotBlank()
                        ) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy digits")
                        }
                    }
                }

                // 2. Khmer Words (អានជាពាក្យ)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "អានជាពាក្យ (Standard Words):",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = khmerWords,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        IconButton(
                            onClick = { viewModel.copyToClipboard(khmerWords) },
                            enabled = khmerWords.isNotBlank() && !khmerWords.startsWith("សូម")
                        ) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy words")
                        }
                    }
                }

                // 3. Khmer Riel Currency Display (ប្រាក់រៀល)
                if (rielWords.isNotBlank()) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "អានជាប្រាក់រៀល (Khmer Riel):",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "$khmerDigits ៛ ($rielWords)",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }

                            IconButton(
                                onClick = { viewModel.speakConvertedRiel() }
                            ) {
                                Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Speak Riel", tint = MaterialTheme.colorScheme.secondary)
                            }
                        }
                    }
                }
            }
        }

        // Quick Speech Modes
        Text(
            text = "ជ្រើសរើសរបៀបអានសំឡេង:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.speakConvertedNumber() },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("speak_number_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("អានលេខ", fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { viewModel.speakConvertedRiel() },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                enabled = rielWords.isNotBlank()
            ) {
                Icon(imageVector = Icons.Default.Payments, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("អានប្រាក់រៀល", fontWeight = FontWeight.Bold)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { viewModel.speakConvertedYear() },
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = yearWords.isNotBlank()
            ) {
                Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("អានជាឆ្នាំ", fontSize = 12.sp)
            }

            OutlinedButton(
                onClick = { viewModel.speakConvertedDigitByDigit() },
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = phoneWords.isNotBlank()
            ) {
                Icon(imageVector = Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("អានលេខទូរស័ព្ទ", fontSize = 12.sp)
            }
        }

        FilledTonalButton(
            onClick = {
                val textToSend = if (rielWords.isNotBlank()) "$khmerDigits ៛ ($rielWords)" else khmerWords
                onSendToEditor(textToSend)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = khmerWords.isNotBlank() && !khmerWords.startsWith("សូម")
        ) {
            Text("ផ្ញើអក្សរទៅកាន់កន្លែងបំលែង (Send to Editor)", fontWeight = FontWeight.Bold)
        }
    }
}
