package org.nekobucket.catinbucket.datagen

import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent
import org.nekobucket.catinbucket.datagen.models.{ BlockModels, ItemModels }
import org.nekobucket.catinbucket.datagen.recipes.Recipes
import org.nekobucket.catinbucket.mod.EventBus.Mod
import org.nekobucket.catinbucket.mod.MOD_ID


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