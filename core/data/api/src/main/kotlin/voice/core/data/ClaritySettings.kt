package voice.core.data

import kotlinx.serialization.Serializable

@Serializable
public data class ClaritySettings(
  val enabled: Boolean = false,
  val rumble: Int = 6,
  val presence: Int = 4,
  val compression: Int = 5,
) {
  public companion object {
    public val Default: ClaritySettings = ClaritySettings()
  }
}
