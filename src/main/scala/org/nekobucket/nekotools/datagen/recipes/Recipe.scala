package org.nekobucket.nekotools.datagen.recipes

import org.nekobucket.nekotools.mod.{ MOD_ID, getResourceLocation }
import org.nekobucket.nekotools.util.Extensions._
import net.minecraft.advancements.criterion.InventoryChangeTrigger
import net.minecraft.block.Blocks
import net.minecraft.data._

import java.util.function.Consumer

class Recipe(builder: Recipe.RecipeFunc) {
  def save(consumer: Consumer[IFinishedRecipe]): Unit = builder(consumer)
}

object Recipe {
  type RecipeFunc = Consumer[IFinishedRecipe] => Unit

  def of(id: String)(builder: Any): Recipe = matchBuilder(id, builder).let(new Recipe(_))

  def matchBuilder(id: String, builder: Any): RecipeFunc = builder match {
    case b: ShapedRecipeBuilder => b.group(MOD_ID)
      .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
      .save(_, getResourceLocation(id))

    case b: ShapelessRecipeBuilder => b.group(MOD_ID)
      .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
      .save(_, getResourceLocation(id))

    case b: CookingRecipeBuilder => b
      .unlockedBy("cobblestone", InventoryChangeTrigger.Instance.hasItems(Blocks.COBBLESTONE))
      .save(_, getResourceLocation(id))

    case b: SmithingRecipeBuilder => b.save(_, getResourceLocation(id))
    case b: SingleItemRecipeBuilder => b.save(_, getResourceLocation(id))
    case b: CustomRecipeBuilder => b.save(_, id)
    case _ => throw RecipeTypeNotFoundException
  }
}
