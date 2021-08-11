package org.nekobucket.nekotools.datagen.recipes

import net.minecraft.data.{ DataGenerator, IFinishedRecipe, RecipeProvider }
import org.nekobucket.nekotools.datagen.DataGenObj

import java.util.function.Consumer


class Recipes(implicit generator: DataGenerator) extends RecipeProvider(generator) {
  import Recipes._

  override def buildShapelessRecipes(consumer : Consumer[IFinishedRecipe]): Unit = array.foreach(_().save(consumer))
}

object Recipes extends DataGenObj[() => Recipe] {
  def addAsFunc(recipe: => Recipe): Unit = array += (() => recipe)
  def ~=(recipe: => Recipe): Unit = addAsFunc(recipe)
}
