package voice.features.playbackScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import java.text.DecimalFormat
import voice.core.strings.R as StringsR

@Composable
internal fun PitchDialog(
  dialogState: BookPlayDialogViewState.PitchDialog,
  viewModel: BookPlayViewModel,
) {
  val pitchFormatter = remember { DecimalFormat("0.00 x") }

  AlertDialog(
    onDismissRequest = { viewModel.dismissDialog() },
    confirmButton = {},
    title = {
      Text(stringResource(id = StringsR.string.playback_option_pitch))
    },
    text = {
      Column {
        Text(stringResource(id = StringsR.string.playback_option_pitch) + ": " + pitchFormatter.format(dialogState.pitch))
        val valueRange = 0.5F..2F
        val rangeSize = valueRange.endInclusive - valueRange.start
        val stepSize = 0.05
        val steps = (rangeSize / stepSize).toInt() - 1
        Slider(
          steps = steps,
          valueRange = valueRange,
          value = dialogState.pitch,
          onValueChange = {
            viewModel.onPlaybackPitchChanged(it)
          },
        )
      }
    },
  )
}
