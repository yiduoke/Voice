package voice.core.playback.misc

import android.media.audiofx.DynamicsProcessing
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import voice.core.data.ClaritySettings
import voice.core.logging.api.Logger
import kotlin.properties.Delegates

object ClarityLevels {

  const val MAX_LEVEL = 10

  fun subBassGainDb(rumble: Int): Float = 0F - 1.5F * rumble

  fun bassGainDb(rumble: Int): Float = 0F - 1.0F * rumble

  fun presenceGainDb(presence: Int): Float = 0.9F * presence

  fun compressionRatio(compression: Int): Float = 1F + 0.35F * compression

  fun compressionThresholdDb(compression: Int): Float = -20F - 2.5F * compression

  fun compressionPostGainDb(compression: Int): Float = 0.8F * compression
}

@Inject
class ClarityEffectSetter {

  private var current: CurrentConfiguration? = null

  fun set(
    settings: ClaritySettings,
    audioSession: Int,
  ) {
    Logger.v("set clarity=$settings, session=$audioSession")
    val configuration = current
    if (configuration != null && configuration.audioSession == audioSession) {
      if (configuration.settings != settings) {
        update(configuration, settings)
      }
    } else {
      createNew(audioSession, settings)
    }
  }

  fun reset() {
    Logger.v("reset clarity")
    try {
      current?.effect?.release()
    } catch (e: RuntimeException) {
      Logger.d(e)
    }
    current = null
  }

  private fun createNew(
    audioSession: Int,
    settings: ClaritySettings,
  ) {
    reset()
    val effect = createEffect(audioSession, settings) ?: return
    current = CurrentConfiguration(effect, settings, audioSession)
    Logger.v("new clarity configuration applied")
  }

  private fun update(
    configuration: CurrentConfiguration,
    settings: ClaritySettings,
  ) {
    try {
      configuration.effect.applySettings(settings)
      configuration.effect.enabled = settings.enabled
      current = configuration.copy(settings = settings)
      Logger.v("clarity configuration updated")
    } catch (e: RuntimeException) {
      Logger.d(e)
      createNew(configuration.audioSession, settings)
    }
  }

  private fun createEffect(
    audioSession: Int,
    settings: ClaritySettings,
  ): DynamicsProcessing? {
    return try {
      val config = DynamicsProcessing.Config.Builder(
        DynamicsProcessing.VARIANT_FAVOR_FREQUENCY_RESOLUTION,
        CHANNEL_COUNT,
        true,
        PRE_EQ_BAND_COUNT,
        true,
        MBC_BAND_COUNT,
        true,
        POST_EQ_BAND_COUNT,
        true,
      ).build()
      DynamicsProcessing(0, audioSession, config).apply {
        applySettings(settings)
        enabled = settings.enabled
      }
    } catch (e: RuntimeException) {
      Logger.d(e)
      null
    }
  }

  private fun DynamicsProcessing.applySettings(settings: ClaritySettings) {
    setPreEqBandAllChannelsTo(
      0,
      DynamicsProcessing.EqBand(true, SUB_BASS_CUTOFF_HZ, ClarityLevels.subBassGainDb(settings.rumble)),
    )
    setPreEqBandAllChannelsTo(
      1,
      DynamicsProcessing.EqBand(true, BASS_CUTOFF_HZ, ClarityLevels.bassGainDb(settings.rumble)),
    )
    setPreEqBandAllChannelsTo(2, DynamicsProcessing.EqBand(true, NYQUIST_CUTOFF_HZ, 0F))

    setPostEqBandAllChannelsTo(0, DynamicsProcessing.EqBand(true, PRESENCE_START_HZ, 0F))
    setPostEqBandAllChannelsTo(
      1,
      DynamicsProcessing.EqBand(true, PRESENCE_END_HZ, ClarityLevels.presenceGainDb(settings.presence)),
    )
    setPostEqBandAllChannelsTo(2, DynamicsProcessing.EqBand(true, NYQUIST_CUTOFF_HZ, 0F))

    setMbcBandAllChannelsTo(
      0,
      DynamicsProcessing.MbcBand(
        true,
        NYQUIST_CUTOFF_HZ,
        MBC_ATTACK_MS,
        MBC_RELEASE_MS,
        ClarityLevels.compressionRatio(settings.compression),
        ClarityLevels.compressionThresholdDb(settings.compression),
        MBC_KNEE_WIDTH_DB,
        NOISE_GATE_OFF_DB,
        1F,
        0F,
        ClarityLevels.compressionPostGainDb(settings.compression),
      ),
    )
  }

  private data class CurrentConfiguration(
    val effect: DynamicsProcessing,
    val settings: ClaritySettings,
    val audioSession: Int,
  )

  private companion object {
    const val CHANNEL_COUNT = 2
    const val PRE_EQ_BAND_COUNT = 3
    const val POST_EQ_BAND_COUNT = 3
    const val MBC_BAND_COUNT = 1
    const val SUB_BASS_CUTOFF_HZ = 150F
    const val BASS_CUTOFF_HZ = 300F
    const val PRESENCE_START_HZ = 1000F
    const val PRESENCE_END_HZ = 4000F
    const val NYQUIST_CUTOFF_HZ = 20000F
    const val MBC_ATTACK_MS = 3F
    const val MBC_RELEASE_MS = 120F
    const val MBC_KNEE_WIDTH_DB = 6F
    const val NOISE_GATE_OFF_DB = -90F
  }
}

@SingleIn(AppScope::class)
@Inject
class ClarityEffect(private val clarityEffectSetter: ClarityEffectSetter) {

  var settings: ClaritySettings by Delegates.observable(ClaritySettings.Default) { _, _, _ -> update() }
  var audioSessionId: Int? by Delegates.observable(null) { _, _, _ -> update() }

  private fun update() {
    Logger.v("update clarity(audioSessionId=$audioSessionId, settings=$settings)")
    val audioSessionId = audioSessionId
    if (audioSessionId != null) {
      clarityEffectSetter.set(settings, audioSessionId)
    } else {
      clarityEffectSetter.reset()
    }
  }
}
