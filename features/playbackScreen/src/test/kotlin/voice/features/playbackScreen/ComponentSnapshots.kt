package voice.features.playbackScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import voice.core.data.BookId
import voice.core.data.ClaritySettings
import voice.core.ui.VoiceTheme
import voice.features.playbackScreen.view.BookPlayView
import voice.features.playbackScreen.view.ClaritySheetContent
import voice.features.playbackScreen.view.PlaybackRow
import voice.features.playbackScreen.view.TuningRow
import kotlin.test.Test
import kotlin.time.Duration.Companion.minutes

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class ComponentSnapshots {

  @Test
  fun playbackRow() {
    captureRoboImage {
      VoiceTheme {
        Surface {
          PlaybackRow(
            playing = false,
            seekTimeInSeconds = 20,
            onPlayClick = {},
            onRewindClick = {},
            onFastForwardClick = {},
          )
        }
      }
    }
  }

  @Test
  fun claritySheet() {
    captureRoboImage {
      VoiceTheme {
        Surface {
          ClaritySheetContent(
            clarity = ClaritySettings(enabled = true, rumble = 6, presence = 4, compression = 5),
            onToggle = {},
            onRumbleChange = {},
            onPresenceChange = {},
            onCompressionChange = {},
            modifier = Modifier.padding(vertical = 16.dp),
          )
        }
      }
    }
  }

  @Test
  fun claritySheetDisabled() {
    captureRoboImage {
      VoiceTheme {
        Surface {
          ClaritySheetContent(
            clarity = ClaritySettings(enabled = false, rumble = 6, presence = 4, compression = 5),
            onToggle = {},
            onRumbleChange = {},
            onPresenceChange = {},
            onCompressionChange = {},
            modifier = Modifier.padding(vertical = 16.dp),
          )
        }
      }
    }
  }

  @Test
  @Config(qualifiers = RobolectricDeviceQualifiers.Pixel7)
  fun claritySheetOnFullScreen() {
    captureRoboImage {
      VoiceTheme {
        Box(Modifier.fillMaxSize()) {
          BookPlayView(
            viewState = BookPlayViewState(
              chapterName = "Chapter one",
              showPreviousNextButtons = true,
              cover = null,
              duration = 10.minutes,
              playedTime = 3.minutes,
              playing = true,
              skipSilence = false,
              sleepTimerState = BookPlayViewState.SleepTimerViewState.Disabled,
              title = "Anna Karenina",
              playbackSpeed = 1.1F,
              pitch = 1F,
              seekTimeInSeconds = 20,
              clarity = ClaritySettings.Default,
            ),
            bookId = BookId("snapshot"),
            useLandscapeLayout = false,
            onPlayClick = {},
            onRewindClick = {},
            onFastForwardClick = {},
            onSeek = {},
            onSleepTimerClick = {},
            onBookmarkClick = {},
            onBookmarkLongClick = {},
            onSpeedChangeClick = {},
            onSkipSilenceClick = {},
            onVolumeBoostClick = {},
            onPitchClick = {},
            onSpeedStep = {},
            onPitchStep = {},
            claritySheetVisible = false,
            onClarityOpen = {},
            onClarityDismiss = {},
            onClarityToggle = {},
            onClarityRumbleChange = {},
            onClarityPresenceChange = {},
            onClarityCompressionChange = {},
            onSkipToNext = {},
            onSkipToPrevious = {},
            onCloseClick = {},
            onCurrentChapterClick = {},
          )
          Box(
            Modifier
              .fillMaxSize()
              .background(Color.Black.copy(alpha = 0.4F)),
          )
          Surface(
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            tonalElevation = 2.dp,
            modifier = Modifier
              .align(Alignment.BottomCenter)
              .fillMaxWidth(),
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Box(
                Modifier
                  .padding(top = 12.dp, bottom = 8.dp)
                  .size(width = 40.dp, height = 4.dp)
                  .background(
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4F),
                    RoundedCornerShape(2.dp),
                  ),
              )
              ClaritySheetContent(
                clarity = ClaritySettings(enabled = true, rumble = 6, presence = 4, compression = 5),
                onToggle = {},
                onRumbleChange = {},
                onPresenceChange = {},
                onCompressionChange = {},
                modifier = Modifier.padding(bottom = 24.dp),
              )
            }
          }
        }
      }
    }
  }

  @Test
  fun tuningRow() {
    captureRoboImage {
      VoiceTheme {
        Surface {
          Row {
            TuningRow(
              playbackSpeed = 1.2F,
              pitch = 1.4F,
              onSpeedStep = {},
              onPitchStep = {},
              onSpeedValueClick = {},
              onPitchValueClick = {},
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            )
          }
        }
      }
    }
  }
}
