package nekobucket.nekotools.datagen

import nekobucket.nekotools.datagen.recipes.Recipes
import nekobucket.nekotools.mod.EventBus.MOD
import nekobucket.nekotools.mod.MOD_ID
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent

@EventBusSubscriber(bus = MOD, modid = MOD_ID)
object DataGenerator {

  @SubscribeEvent
  def gatherData(event: GatherDataEvent): Unit = {
    val generator = event.getGenerator
    if (event.includeServer()) {
      generator.addProvider(new Recipes(generator))
    }
  }
}
