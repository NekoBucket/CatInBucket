package org.nekobucket.catinbucket.item

import net.minecraft.data.recipes.{ ShapedRecipeBuilder, ShapelessRecipeBuilder }
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import org.nekobucket.catinbucket.block.CatBlock
import org.nekobucket.catinbucket.datagen.models.{ ItemModels, itemGenerated }
import org.nekobucket.catinbucket.datagen.recipes.{ Recipe, Recipes }
import org.nekobucket.catinbucket.mod.BaseObject
import org.nekobucket.catinbucket.mod.registry.ItemRegistry
import org.nekobucket.catinbucket.mod.registry.Register

@Register.AsItem
case class CatIngot() extends BaseItem

object CatIngot extends BaseObject[CatIngot]("cat_ingot") with CatIngotRecipe with CatIngotItemModel

trait CatIngotRecipe {
  this: CatIngot.type =>

  Recipes +~ Recipe.of(s"${ID}_from_crafting_3fish_1iron") {
    ShapedRecipeBuilder.shaped(ItemRegistry.get[CatIngot], 1)
      .pattern("XXX")
      .pattern(" O ")
      .define('X', Ingredient.of(Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH,
        Items.COOKED_COD, Items.COOKED_SALMON
      ))
      .define('O', Items.IRON_INGOT)
  }

  Recipes +~ Recipe.of(s"${ID}_from_crafting_${CatBlock.ID}") {
    ShapelessRecipeBuilder.shapeless(ItemRegistry.get[CatIngot], 9)
      .requires(ItemRegistry.get[CatBlock.Item], 1)
  }

}

trait CatIngotItemModel {
  this: CatIngot.type =>

  ItemModels += (_.getBuilder(ID)
    .parent(itemGenerated)
    .texture("layer0", s"item/$ID")
  )
}
