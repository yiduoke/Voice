package voice.features.playbackScreen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import voice.core.data.BookId
import voice.features.playbackScreen.BookPlayViewState
import kotlin.time.Duration

@Composable
internal fun BookPlayContent(
  contentPadding: PaddingValues,
  viewState: BookPlayViewState,
  bookId: BookId,
  onPlayClick: () -> Unit,
  onRewindClick: () -> Unit,
  onFastForwardClick: () -> Unit,
  onSeek: (Duration) -> Unit,
  onSkipToNext: () -> Unit,
  onSkipToPrevious: () -> Unit,
  onCurrentChapterClick: () -> Unit,
  onSpeedStep: (Float) -> Unit,
  onPitchStep: (Float) -> Unit,
  onSpeedValueClick: () -> Unit,
  onPitchValueClick: () -> Unit,
  useLandscapeLayout: Boolean,
) {
  if (useLandscapeLayout) {
    Row(Modifier.padding(contentPadding)) {
      CoverRow(
        bookId = bookId,
        cover = viewState.cover,
        playing = viewState.playing,
        seekTimeInSeconds = viewState.seekTimeInSeconds,
        onPlayClick = onPlayClick,
        onRewindClick = onRewindClick,
        onFastForwardClick = onFastForwardClick,
        sleepTimerState = viewState.sleepTimerState,
        modifier = Modifier
          .fillMaxHeight()
          .weight(1F)
          .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
      )
      Column(
        modifier = Modifier
          .fillMaxHeight()
          .weight(1F),
        verticalArrangement = Arrangement.Center,
      ) {
        viewState.chapterName?.let { chapterName ->
          ChapterRow(
            chapterName = chapterName,
            nextPreviousVisible = viewState.showPreviousNextButtons,
            onSkipToNext = onSkipToNext,
            onSkipToPrevious = onSkipToPrevious,
            onCurrentChapterClick = onCurrentChapterClick,
          )
        }
        Spacer(modifier = Modifier.size(20.dp))
        SliderRow(
          duration = viewState.duration,
          playedTime = viewState.playedTime,
          onSeek = onSeek,
        )
        Spacer(modifier = Modifier.size(16.dp))
        TuningRow(
          playbackSpeed = viewState.playbackSpeed,
          pitch = viewState.pitch,
          onSpeedStep = onSpeedStep,
          onPitchStep = onPitchStep,
          onSpeedValueClick = onSpeedValueClick,
          onPitchValueClick = onPitchValueClick,
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.size(16.dp))
        PlaybackRow(
          playing = viewState.playing,
          seekTimeInSeconds = viewState.seekTimeInSeconds,
          onPlayClick = onPlayClick,
          onRewindClick = onRewindClick,
          onFastForwardClick = onFastForwardClick,
        )
      }
    }
  } else {
    Column(Modifier.padding(contentPadding)) {
      CoverRow(
        bookId = bookId,
        onPlayClick = onPlayClick,
        onRewindClick = onRewindClick,
        onFastForwardClick = onFastForwardClick,
        cover = viewState.cover,
        playing = viewState.playing,
        seekTimeInSeconds = viewState.seekTimeInSeconds,
        sleepTimerState = viewState.sleepTimerState,
        modifier = Modifier
          .fillMaxWidth()
          .weight(1F)
          .padding(start = 16.dp, end = 16.dp, top = 8.dp),
      )
      viewState.chapterName?.let { chapterName ->
        Spacer(modifier = Modifier.size(16.dp))
        ChapterRow(
          chapterName = chapterName,
          nextPreviousVisible = viewState.showPreviousNextButtons,
          onSkipToNext = onSkipToNext,
          onSkipToPrevious = onSkipToPrevious,
          onCurrentChapterClick = onCurrentChapterClick,
        )
      }
      Spacer(modifier = Modifier.size(20.dp))
      SliderRow(
        duration = viewState.duration,
        playedTime = viewState.playedTime,
        onSeek = onSeek,
      )
      Spacer(modifier = Modifier.size(16.dp))
      TuningRow(
        playbackSpeed = viewState.playbackSpeed,
        pitch = viewState.pitch,
        onSpeedStep = onSpeedStep,
        onPitchStep = onPitchStep,
        onSpeedValueClick = onSpeedValueClick,
        onPitchValueClick = onPitchValueClick,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
      )
      Spacer(modifier = Modifier.size(16.dp))
      PlaybackRow(
        playing = viewState.playing,
        seekTimeInSeconds = viewState.seekTimeInSeconds,
        onPlayClick = onPlayClick,
        onRewindClick = onRewindClick,
        onFastForwardClick = onFastForwardClick,
      )
      Spacer(modifier = Modifier.size(24.dp))
    }
  }
}
