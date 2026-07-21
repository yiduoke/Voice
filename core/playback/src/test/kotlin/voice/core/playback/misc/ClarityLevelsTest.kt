package voice.core.playback.misc

import kotlin.test.Test
import kotlin.test.assertEquals

class ClarityLevelsTest {

  @Test
  fun `level zero leaves the signal untouched`() {
    assertEquals(expected = 0F, actual = ClarityLevels.subBassGainDb(0))
    assertEquals(expected = 0F, actual = ClarityLevels.bassGainDb(0))
    assertEquals(expected = 0F, actual = ClarityLevels.presenceGainDb(0))
    assertEquals(expected = 1F, actual = ClarityLevels.compressionRatio(0))
    assertEquals(expected = 0F, actual = ClarityLevels.compressionPostGainDb(0))
  }

  @Test
  fun `max levels stay inside safe bounds`() {
    assertEquals(expected = -15F, actual = ClarityLevels.subBassGainDb(ClarityLevels.MAX_LEVEL))
    assertEquals(expected = -10F, actual = ClarityLevels.bassGainDb(ClarityLevels.MAX_LEVEL))
    assertEquals(expected = 9F, actual = ClarityLevels.presenceGainDb(ClarityLevels.MAX_LEVEL))
    assertEquals(expected = 4.5F, actual = ClarityLevels.compressionRatio(ClarityLevels.MAX_LEVEL))
    assertEquals(expected = -45F, actual = ClarityLevels.compressionThresholdDb(ClarityLevels.MAX_LEVEL))
    assertEquals(expected = 8F, actual = ClarityLevels.compressionPostGainDb(ClarityLevels.MAX_LEVEL))
  }
}
