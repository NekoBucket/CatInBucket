package org.nekobucket.catinbucket.datagen.recipes

import net.minecraft.advancements.critereon.InventoryChangeTrigger
import org.nekobucket.catinbucket.mod.{ MOD_ID, getResourceLocation }
import org.nekobucket.catinbucket.util.Extensions._
import net.minecraft.world.level.block.Blocks
import net.minecraft.data.recipes.{ FinishedRecipe, RecipeBuilder, ShapedRecipeBuilder, ShapelessRecipeBuilder, SimpleCookingRecipeBuilder, SingleItemRecipeBuilder }

import java.util.function.Consumer

class Recipe(func: Recipe.RecipeFunc) {
  def save(consumer: Consumer[FinishedRecipe]): Unit = func(consumer)
}

object Recipe {
  type RecipeFunc = Consumer[FinishedRecipe] => Unit

  def of(id: String)(builder: => RecipeBuilder): Recipe = buildRecipe(id, builder) |> (new Recipe(_))

  def buildRecipe(id: String, builder: RecipeBuilder): RecipeFunc = builder.group(MOD_ID)
    .unlockedBy("cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.COBBLESTONE))
    .save(_, getResourceLocation(id))
}
