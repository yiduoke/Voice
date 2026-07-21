package voice.features.playbackScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.runner.RunWith
import org.robolectric.annotation.GraphicsMode
import voice.core.ui.VoiceTheme
import voice.features.playbackScreen.view.PlaybackRow
import voice.features.playbackScreen.view.TuningRow
import kotlin.test.Test

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
