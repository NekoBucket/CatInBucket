package org.nekobucket.nekotools.item

import net.minecraft.data.{ ShapedRecipeBuilder, ShapelessRecipeBuilder }
import net.minecraft.item.Items
import net.minecraft.item.crafting.Ingredient
import org.nekobucket.nekotools.block.NekoBlock
import org.nekobucket.nekotools.datagen.models.{ ItemModels, itemGenerated }
import org.nekobucket.nekotools.datagen.recipes.{ Recipe, Recipes }
import org.nekobucket.nekotools.item.NekoIngot.ID
import org.nekobucket.nekotools.mod.NekoObject
import org.nekobucket.nekotools.mod.registry.ItemRegistry
import org.nekobucket.nekotools.mod.registry.Register

@Register.AsItem
class NekoIngot extends NekoItemBase

object NekoIngot extends NekoObject[NekoIngot] with NekoIngotRecipe with NekoIngotItemModel {
  override val ID = "neko_ingot"
}

trait NekoIngotRecipe {
  Recipes +~ Recipe.of("neko_ingot_from_crafting_3fish_1iron") {
    ShapedRecipeBuilder.shaped(ItemRegistry.get[NekoIngot], 1)
      .pattern("XXX")
      .pattern(" O ")
      .define('X', Ingredient.of(Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH,
        Items.COOKED_COD, Items.COOKED_SALMON
      ))
      .define('O', Items.IRON_INGOT)
  }

  Recipes +~ Recipe.of("neko_ingot_from_crafting_neko_block") {
    ShapelessRecipeBuilder.shapeless(ItemRegistry.get[NekoIngot], 9)
      .requires(ItemRegistry.get[NekoBlock.Item], 1)
  }

}

trait NekoIngotItemModel {
  ItemModels += (_.getBuilder(ID)
    .parent(itemGenerated)
    .texture("layer0", "item/neko_ingot")
  )
}
