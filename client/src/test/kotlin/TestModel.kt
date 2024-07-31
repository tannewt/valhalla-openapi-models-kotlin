import com.valhalla.api.models.AutoCostingOptions
import kotlin.test.Test
import com.valhalla.api.models.CostingOptions
import kotlin.test.assertEquals

class ModelTest {
  @Test
  fun testCostingOptions() {
    val costing = CostingOptions(
      auto = AutoCostingOptions(maneuverPenalty = 2)
    )
    assertEquals(2, costing.auto?.maneuverPenalty)
  }
}
