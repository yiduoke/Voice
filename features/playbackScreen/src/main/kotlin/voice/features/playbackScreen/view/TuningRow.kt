package voice.features.playbackScreen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat
import voice.core.strings.R as StringsR
import voice.core.ui.icons.VoiceIcons

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
  OutlinedCard(modifier = modifier) {
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
      style = MaterialTheme.typography.bodyMedium,
      modifier = Modifier.width(56.dp),
    )
    OutlinedIconButton(
      onClick = onDecrement,
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .width(52.dp)
        .height(44.dp),
    ) {
      Icon(
        imageVector = VoiceIcons.Remove,
        contentDescription = stringResource(id = StringsR.string.playback_tuning_decrease),
      )
    }
    Text(
      text = valueFormatted,
      style = MaterialTheme.typography.titleMedium,
      textAlign = TextAlign.Center,
      modifier = Modifier
        .weight(1F)
        .clickable(onClick = onValueClick)
        .padding(vertical = 12.dp),
    )
    OutlinedIconButton(
      onClick = onIncrement,
      shape = RoundedCornerShape(12.dp),
      modifier = Modifier
        .width(52.dp)
        .height(44.dp),
    ) {
      Icon(
        imageVector = VoiceIcons.Add,
        contentDescription = stringResource(id = StringsR.string.playback_tuning_increase),
      )
    }
  }
}

private const val TUNING_STEP = 0.05F
