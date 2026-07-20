package voice.features.playbackScreen.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import voice.core.data.BookId
import voice.core.ui.icons.VoiceIcons
import voice.core.ui.sharedCoverElementModifier
import voice.core.strings.R as StringsR
import voice.core.ui.R as UiR

private enum class CoverTapFeedback {
  PlayPause,
  Rewind,
  FastForward,
}

@Composable
internal fun Cover(
  bookId: BookId,
  playing: Boolean,
  seekTimeInSeconds: Int,
  cover: String?,
  onPlayPause: () -> Unit,
  onRewind: () -> Unit,
  onFastForward: () -> Unit,
) {
  val currentPlaying by rememberUpdatedState(playing)
  var feedback by remember { mutableStateOf<CoverTapFeedback?>(null) }
  var feedbackTrigger by remember { mutableIntStateOf(0) }
  var showPauseIcon by remember { mutableStateOf(false) }

  Box(Modifier.fillMaxSize()) {
    AsyncImage(
      modifier = Modifier
        .fillMaxSize()
        .sharedCoverElementModifier(bookId)
        .pointerInput(Unit) {
          detectTapGestures(
            onTap = {
              showPauseIcon = !currentPlaying
              feedback = CoverTapFeedback.PlayPause
              feedbackTrigger++
              onPlayPause()
            },
            onDoubleTap = { offset ->
              if (offset.x < size.width / 2) {
                feedback = CoverTapFeedback.Rewind
                feedbackTrigger++
                onRewind()
              } else {
                feedback = CoverTapFeedback.FastForward
                feedbackTrigger++
                onFastForward()
              }
            },
          )
        }
        .clip(RoundedCornerShape(20.dp)),
      contentScale = ContentScale.Crop,
      model = cover,
      placeholder = painterResource(id = UiR.drawable.album_art),
      error = painterResource(id = UiR.drawable.album_art),
      contentDescription = stringResource(id = StringsR.string.cover_title),
    )
    LaunchedEffect(feedbackTrigger) {
      if (feedback != null) {
        delay(600)
        feedback = null
      }
    }
    val lastFeedback = remember { mutableStateOf<CoverTapFeedback?>(null) }
    if (feedback != null) {
      lastFeedback.value = feedback
    }
    AnimatedVisibility(
      visible = feedback != null,
      enter = fadeIn(),
      exit = fadeOut(),
      modifier = Modifier.matchParentSize(),
    ) {
      lastFeedback.value?.let { shownFeedback ->
        Box(Modifier.fillMaxSize()) {
          val alignment = when (shownFeedback) {
            CoverTapFeedback.PlayPause -> Alignment.Center
            CoverTapFeedback.Rewind -> Alignment.CenterStart
            CoverTapFeedback.FastForward -> Alignment.CenterEnd
          }
          Box(
            modifier = Modifier
              .align(alignment)
              .padding(horizontal = 28.dp)
              .size(64.dp)
              .background(Color(0x66000000), CircleShape),
            contentAlignment = Alignment.Center,
          ) {
            when (shownFeedback) {
              CoverTapFeedback.PlayPause -> {
                Icon(
                  imageVector = if (showPauseIcon) VoiceIcons.Pause else VoiceIcons.PlayArrow,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(40.dp),
                )
              }
              CoverTapFeedback.Rewind,
              CoverTapFeedback.FastForward,
              -> {
                val forward = shownFeedback == CoverTapFeedback.FastForward
                Icon(
                  imageVector = VoiceIcons.Replay,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier
                    .size(36.dp)
                    .scale(scaleX = if (forward) -1f else 1f, scaleY = 1f),
                )
                Text(
                  text = seekTimeInSeconds.toString(),
                  style = MaterialTheme.typography.labelSmall,
                  color = Color.White,
                  modifier = Modifier.padding(top = 3.dp),
                )
              }
            }
          }
        }
      }
    }
  }
}
