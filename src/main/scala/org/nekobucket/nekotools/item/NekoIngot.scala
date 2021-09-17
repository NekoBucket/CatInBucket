package org.nekobucket.nekotools.item

import net.minecraft.data.{ ShapedRecipeBuilder, ShapelessRecipeBuilder }
import net.minecraft.item.Items
import net.minecraft.item.crafting.Ingredient
import org.nekobucket.nekotools.block.NekoBlock
import org.nekobucket.nekotools.datagen.models.{ ItemModels, itemGenerated }
import org.nekobucket.nekotools.datagen.recipes.{ Recipe, Recipes }
import org.nekobucket.nekotools.mod.NekoObject
import org.nekobucket.nekotools.mod.registry.ItemRegistry
import org.nekobucket.nekotools.mod.registry.Register
import space.controlnet.lightioc.annotation.Singleton

@Singleton
@Register.AsItem
class NekoIngot extends NekoItemBase

object NekoIngot extends NekoObject[NekoIngot]("neko_ingot") with NekoIngotRecipe with NekoIngotItemModel

trait NekoIngotRecipe {
  this: NekoIngot.type =>

  Recipes +~ Recipe.of(s"${ID}_from_crafting_3fish_1iron") {
    ShapedRecipeBuilder.shaped(ItemRegistry.get[NekoIngot], 1)
      .pattern("XXX")
      .pattern(" O ")
      .define('X', Ingredient.of(Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH,
        Items.COOKED_COD, Items.COOKED_SALMON
      ))
      .define('O', Items.IRON_INGOT)
  }

  Recipes +~ Recipe.of(s"${ID}_from_crafting_neko_block") {
    ShapelessRecipeBuilder.shapeless(ItemRegistry.get[NekoIngot], 9)
      .requires(ItemRegistry.get[NekoBlock.Item], 1)
  }

}

trait NekoIngotItemModel {
  this: NekoIngot.type =>

  ItemModels += (_.getBuilder(ID)
    .parent(itemGenerated)
    .texture("layer0", s"item/$ID")
  )
}
