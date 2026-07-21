package voice.features.playbackScreen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import voice.core.strings.R
import voice.core.ui.icons.VoiceIcons

@Composable
internal fun SkipButton(
  forward: Boolean,
  seekTimeInSeconds: Int,
  onClick: () -> Unit,
) {
  Box(
    modifier = Modifier
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(bounded = false),
        onClick = onClick,
      )
      .size(48.dp),
  ) {
    Icon(
      modifier = Modifier
        .size(44.dp)
        .align(Alignment.TopCenter)
        .scale(scaleX = if (forward) -1f else 1F, scaleY = 1f),
      imageVector = VoiceIcons.BendArrow,
      contentDescription = stringResource(
        id = if (forward) {
          R.string.playback_action_fast_forward
        } else {
          R.string.playback_action_rewind
        },
      ),
    )
    Text(
      text = seekTimeInSeconds.toString(),
      style = MaterialTheme.typography.labelLarge,
      fontWeight = FontWeight.Bold,
      modifier = Modifier
        .align(if (forward) Alignment.BottomEnd else Alignment.BottomStart)
        .offset(x = if (forward) (-7).dp else 7.dp, y = (-6).dp),
    )
  }
}
