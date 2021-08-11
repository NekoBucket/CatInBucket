package org.nekobucket.nekotools.datagen

import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.ExistingFileHelper
import org.nekobucket.nekotools.mod.EventBus.Mod
import org.nekobucket.nekotools.mod.MOD_ID
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent
import org.nekobucket.nekotools.datagen.models.{ BlockModels, ItemModels }
import org.nekobucket.nekotools.datagen.recipes.Recipes

@EventBusSubscriber(bus = Mod, modid = MOD_ID)
object DataGenerator {

  @SubscribeEvent
  def gatherData(event: GatherDataEvent): Unit = {
    implicit val generator: DataGenerator = event.getGenerator
    implicit val fileHelper: ExistingFileHelper = event.getExistingFileHelper
    if (event.includeServer) {
      generator.addProvider(new Recipes)
      generator.addProvider(new ItemModels)
      generator.addProvider(new BlockModels)
    }
  }
}
