package voice.features.playbackScreen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import voice.core.data.ClaritySettings
import voice.core.strings.R as StringsR

@Composable
internal fun ClaritySheetContent(
  clarity: ClaritySettings,
  onToggle: (Boolean) -> Unit,
  onRumbleChange: (Float) -> Unit,
  onPresenceChange: (Float) -> Unit,
  onCompressionChange: (Float) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 24.dp),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text(
        text = stringResource(id = StringsR.string.clarity_title),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.weight(1F),
      )
      Switch(
        checked = clarity.enabled,
        onCheckedChange = onToggle,
      )
    }
    Spacer(modifier = Modifier.size(8.dp))
    ClaritySlider(
      label = stringResource(id = StringsR.string.clarity_rumble),
      value = clarity.rumble.toFloat(),
      enabled = clarity.enabled,
      onValueChange = onRumbleChange,
    )
    Spacer(modifier = Modifier.size(12.dp))
    ClaritySlider(
      label = stringResource(id = StringsR.string.clarity_presence),
      value = clarity.presence.toFloat(),
      enabled = clarity.enabled,
      onValueChange = onPresenceChange,
    )
    Spacer(modifier = Modifier.size(12.dp))
    ClaritySlider(
      label = stringResource(id = StringsR.string.clarity_compression),
      value = clarity.compression.toFloat(),
      enabled = clarity.enabled,
      onValueChange = onCompressionChange,
    )
    Spacer(modifier = Modifier.size(16.dp))
  }
}

@Composable
private fun ClaritySlider(
  label: String,
  value: Float,
  enabled: Boolean,
  onValueChange: (Float) -> Unit,
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = label,
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
      color = if (enabled) {
        MaterialTheme.colorScheme.onSurface
      } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38F)
      },
    )
    Slider(
      value = value,
      onValueChange = onValueChange,
      valueRange = 0F..10F,
      steps = 9,
      enabled = enabled,
    )
  }
}
