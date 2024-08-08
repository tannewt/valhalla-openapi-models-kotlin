import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.valhalla.config.models.AdditionalData
import com.valhalla.config.models.Httpd
import com.valhalla.config.models.Loki
import com.valhalla.config.models.Meili
import com.valhalla.config.models.Mjolnir
import com.valhalla.config.models.Odin
import com.valhalla.config.models.ServiceLimits
import com.valhalla.config.models.Statsd
import com.valhalla.config.models.Thor
import kotlin.test.Test
import kotlin.test.assertNotNull

data class AllModels(
  val additionalData: AdditionalData = AdditionalData(),
  val httpd: Httpd = Httpd(),
  val loki: Loki = Loki(),
  val meili: Meili = Meili(),
  val mjolnir: Mjolnir = Mjolnir(),
  val odin: Odin = Odin(),
  val serviceLimits: ServiceLimits = ServiceLimits(),
  val statsd: Statsd = Statsd(),
  val thor: Thor = Thor()
)

class MoshiAdapterTest {

  private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

  @Test
  fun testEncode() {
    val all = AllModels()
    val encoded = moshi.adapter(AllModels::class.java).toJson(all)
    assertNotNull(encoded)
  }
}
