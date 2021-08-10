package org.nekobucket.nekotools.datagen.recipes

import net.minecraft.data.{ DataGenerator, IFinishedRecipe, RecipeProvider }
import org.nekobucket.nekotools.block.NekoBlock
import org.nekobucket.nekotools.item.NekoIngot

import java.util.function.Consumer


class Recipes(generator: DataGenerator) extends RecipeProvider(generator) {

  def recipeBuilders: List[Recipe] = {
    NekoBlock.Item.recipeBuilders ++
      NekoIngot.recipeBuilders
  }

  override def buildShapelessRecipes(consumer : Consumer[IFinishedRecipe]): Unit = recipeBuilders.foreach {
    _.save(consumer)
  }
}
