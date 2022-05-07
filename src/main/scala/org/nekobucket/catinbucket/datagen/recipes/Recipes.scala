package org.nekobucket.catinbucket.datagen.recipes

import net.minecraft.data.recipes.{ FinishedRecipe, RecipeProvider }
import net.minecraft.data.DataGenerator
import org.nekobucket.catinbucket.datagen.DataGenObjAddableWithFunction

import java.util.function.Consumer


private[datagen] class Recipes(implicit generator: DataGenerator)
  extends RecipeProvider(generator) {

  import Recipes._

  override def buildCraftingRecipes(pFinishedRecipeConsumer: Consumer[FinishedRecipe]): Unit = {
    array.foreach(_ ().save(pFinishedRecipeConsumer))
  }
}

private[catinbucket] object Recipes extends DataGenObjAddableWithFunction[Recipe]
