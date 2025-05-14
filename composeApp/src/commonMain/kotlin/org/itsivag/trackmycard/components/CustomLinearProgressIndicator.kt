package org.itsivag.trackmycard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import org.itsivag.trackmycard.theme.surfaceColor

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun CustomLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color = surfaceColor,
    backgroundColor: Color = org.itsivag.trackmycard.theme.backgroundColor,
    clipShape: Shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp),
    hazeState: HazeState,
    hazeStyle: HazeStyle
) {
    Box(
        modifier = modifier
            .clip(clipShape)
            .background(backgroundColor)
            .fillMaxWidth()
            .height(48.dp).hazeEffect(hazeState, hazeStyle),
    ) {
        Box(
            modifier = Modifier
                .background(progressColor)
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .hazeEffect(hazeState, HazeMaterials.thick(containerColor = progressColor))
        )
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            CustomProgressIndicatorLabel(label = "Utilised", value = "8000")
            Spacer(Modifier.weight(1f))
            CustomProgressIndicatorLabel(label = "Limit", value = "10000")
        }
    }
}