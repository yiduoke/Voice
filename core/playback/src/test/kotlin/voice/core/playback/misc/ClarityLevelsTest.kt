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
    assertEquals(expected = 4F, actual = ClarityLevels.compressionRatio(ClarityLevels.MAX_LEVEL))
    assertEquals(expected = -35F, actual = ClarityLevels.compressionThresholdDb(ClarityLevels.MAX_LEVEL))
    assertEquals(expected = 18.75F, actual = ClarityLevels.compressionPostGainDb(ClarityLevels.MAX_LEVEL))
  }

  @Test
  fun `makeup gain keeps loud speech at its original level`() {
    for (level in 0..ClarityLevels.MAX_LEVEL) {
      val threshold = ClarityLevels.compressionThresholdDb(level)
      val ratio = ClarityLevels.compressionRatio(level)
      val makeup = ClarityLevels.compressionPostGainDb(level)
      val loudSpeechInDb = -10F
      val compressed = threshold + (loudSpeechInDb - threshold) / ratio
      assertEquals(expected = loudSpeechInDb, actual = compressed + makeup, absoluteTolerance = 0.01F)
    }
  }
}
