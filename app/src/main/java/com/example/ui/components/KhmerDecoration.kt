package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.util.KhmerDateHelper

@Composable
fun KhmerLotusCanvas(
    modifier: Modifier = Modifier,
    goldColor: Color = MaterialTheme.colorScheme.secondary,
    petalColor: Color = MaterialTheme.colorScheme.tertiary
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f

        // Center lotus bud/petal
        val centerPetal = Path().apply {
            moveTo(cx, cy - h * 0.45f)
            cubicTo(cx + w * 0.18f, cy - h * 0.2f, cx + w * 0.15f, cy + h * 0.15f, cx, cy + h * 0.25f)
            cubicTo(cx - w * 0.15f, cy + h * 0.15f, cx - w * 0.18f, cy - h * 0.2f, cx, cy - h * 0.45f)
            close()
        }
        drawPath(centerPetal, color = petalColor)

        // Left petal
        val leftPetal = Path().apply {
            moveTo(cx - w * 0.05f, cy + h * 0.2f)
            cubicTo(cx - w * 0.35f, cy + h * 0.05f, cx - w * 0.42f, cy - h * 0.2f, cx - w * 0.28f, cy - h * 0.28f)
            cubicTo(cx - w * 0.18f, cy - h * 0.1f, cx - w * 0.1f, cy, cx - w * 0.05f, cy + h * 0.2f)
            close()
        }
        drawPath(leftPetal, color = goldColor.copy(alpha = 0.9f))

        // Right petal
        val rightPetal = Path().apply {
            moveTo(cx + w * 0.05f, cy + h * 0.2f)
            cubicTo(cx + w * 0.35f, cy + h * 0.05f, cx + w * 0.42f, cy - h * 0.2f, cx + w * 0.28f, cy - h * 0.28f)
            cubicTo(cx + w * 0.18f, cy - h * 0.1f, cx + w * 0.1f, cy, cx + w * 0.05f, cy + h * 0.2f)
            close()
        }
        drawPath(rightPetal, color = goldColor.copy(alpha = 0.9f))

        // Bottom base arch (lotus pedestal)
        val baseArch = Path().apply {
            moveTo(cx - w * 0.35f, cy + h * 0.25f)
            quadraticTo(cx, cy + h * 0.45f, cx + w * 0.35f, cy + h * 0.25f)
            quadraticTo(cx, cy + h * 0.32f, cx - w * 0.35f, cy + h * 0.25f)
            close()
        }
        drawPath(baseArch, color = goldColor)
    }
}

@Composable
fun KhmerHeaderBanner(
    modifier: Modifier = Modifier
) {
    val todayDate = remember { KhmerDateHelper.getTodayKhmerDate() }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Date Row (Khmer Solar & Buddhist Era Calendar)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = todayDate,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Divider with Gold Lotus
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color.Transparent, MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f))
                            )
                        )
                )
                KhmerLotusCanvas(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(horizontal = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f), Color.Transparent)
                            )
                        )
                )
            }

            // Title & Description in authentic Khmer
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Text to សំឡេងខ្មែរ",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "កម្មវិធីអានសំឡេងខ្មែរ រួមទាំងអក្សរ ស្រៈ សុភាសិត និងលេខ",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
