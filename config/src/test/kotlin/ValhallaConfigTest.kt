import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.valhalla.config.models.ValhallaConfig
import io.kotest.assertions.json.shouldMatchJsonResource
import kotlin.test.Test

class ValhallaConfigTest {

  private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

  @Test
  fun testDefault() {
    val config = ValhallaConfigBuilder.DEFAULT
    val configJson = moshi.adapter(ValhallaConfig::class.java).toJson(config)

    configJson.shouldMatchJsonResource("/default-config.json")
  }
}
