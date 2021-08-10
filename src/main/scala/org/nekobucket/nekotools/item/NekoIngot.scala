package org.nekobucket.nekotools.item

import net.minecraft.data.{ ShapedRecipeBuilder, ShapelessRecipeBuilder }
import net.minecraft.item.Items
import net.minecraft.item.crafting.Ingredient
import org.nekobucket.nekotools.block.NekoBlock
import org.nekobucket.nekotools.datagen.recipes.Recipe
import org.nekobucket.nekotools.mod.NekoObject
import org.nekobucket.nekotools.mod.registry.ItemRegistry

class NekoIngot extends NekoItem

object NekoIngot extends NekoObject[NekoIngot] {
  override val ID = "neko_ingot"

  def recipeBuilders: List[Recipe] = List(
    Recipe.of("neko_ingot_from_crafting_3fish_1iron") {
      ShapedRecipeBuilder.shaped(ItemRegistry.get[NekoIngot], 1)
        .pattern("XXX")
        .pattern(" O ")
        .define('X', Ingredient.of(Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH,
          Items.COOKED_COD, Items.COOKED_SALMON
        ))
        .define('O', Items.IRON_INGOT)
    },

    Recipe.of("neko_ingot_from_crafting_neko_block") {
      ShapelessRecipeBuilder.shapeless(ItemRegistry.get[NekoIngot], 9)
        .requires(ItemRegistry.get[NekoBlock.Item], 1)
    }
  )
}