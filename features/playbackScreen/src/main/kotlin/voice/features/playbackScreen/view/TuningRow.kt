package voice.features.playbackScreen.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import voice.core.ui.icons.VoiceIcons
import java.text.DecimalFormat
import voice.core.strings.R as StringsR

@Composable
internal fun TuningRow(
  playbackSpeed: Float,
  pitch: Float,
  onSpeedStep: (Float) -> Unit,
  onPitchStep: (Float) -> Unit,
  onSpeedValueClick: () -> Unit,
  onPitchValueClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val formatter = remember { DecimalFormat("0.##") }
  OutlinedCard(
    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.onSurface),
    modifier = modifier,
  ) {
    StepperRow(
      label = stringResource(id = StringsR.string.playback_tuning_speed),
      valueFormatted = formatter.format(playbackSpeed) + "×",
      onDecrement = { onSpeedStep(-TUNING_STEP) },
      onIncrement = { onSpeedStep(TUNING_STEP) },
      onValueClick = onSpeedValueClick,
    )
    HorizontalDivider()
    StepperRow(
      label = stringResource(id = StringsR.string.playback_option_pitch),
      valueFormatted = formatter.format(pitch) + "×",
      onDecrement = { onPitchStep(-TUNING_STEP) },
      onIncrement = { onPitchStep(TUNING_STEP) },
      onValueClick = onPitchValueClick,
    )
  }
}

@Composable
private fun StepperRow(
  label: String,
  valueFormatted: String,
  onDecrement: () -> Unit,
  onIncrement: () -> Unit,
  onValueClick: () -> Unit,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp, vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.width(76.dp),
    )
    OutlinedIconButton(
      onClick = onDecrement,
      shape = RoundedCornerShape(14.dp),
      border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface),
      modifier = Modifier
        .width(72.dp)
        .height(56.dp),
    ) {
      Icon(
        imageVector = VoiceIcons.RemoveThick,
        contentDescription = stringResource(id = StringsR.string.playback_tuning_decrease),
        modifier = Modifier.size(30.dp),
      )
    }
    Text(
      text = valueFormatted,
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center,
      modifier = Modifier
        .weight(1F)
        .clickable(onClick = onValueClick)
        .padding(vertical = 16.dp),
    )
    OutlinedIconButton(
      onClick = onIncrement,
      shape = RoundedCornerShape(14.dp),
      border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface),
      modifier = Modifier
        .width(72.dp)
        .height(56.dp),
    ) {
      Icon(
        imageVector = VoiceIcons.AddThick,
        contentDescription = stringResource(id = StringsR.string.playback_tuning_increase),
        modifier = Modifier.size(30.dp),
      )
    }
  }
}

private const val TUNING_STEP = 0.05F
