package org.nekobucket.catinbucket.datagen.recipes

import net.minecraft.data.recipes.{ RecipeProvider, FinishedRecipe}
import net.minecraft.data.DataGenerator
import org.nekobucket.catinbucket.datagen.DataGenObj

import java.util.function.Consumer


class Recipes(implicit generator: DataGenerator) extends RecipeProvider(generator) {
  import Recipes._

  override def buildCraftingRecipes(pFinishedRecipeConsumer: Consumer[FinishedRecipe]): Unit = {
    array.foreach(_ ().save(pFinishedRecipeConsumer))
  }
}

object Recipes extends DataGenObj[() => Recipe] {
  def addAsFunc(recipe: => Recipe): Unit = array += (() => recipe)
  def +~(recipe: => Recipe): Unit = addAsFunc(recipe)
}
