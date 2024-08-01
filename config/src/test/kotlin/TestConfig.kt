import com.valhalla.config.models.AdditionalData
import kotlin.test.Test
import kotlin.test.assertEquals

class AdditionalDataTest {
  @Test
  fun testDefault() {
    val additionalData = AdditionalData()
    assertEquals("/custom_files/elevation_data", additionalData.elevation)
  }

  @Test
  fun testCustom() {
    val additionalData = AdditionalData(
      "/folder/elevation_data"
    )
    assertEquals("/folder/elevation_data", additionalData.elevation)
  }
}
