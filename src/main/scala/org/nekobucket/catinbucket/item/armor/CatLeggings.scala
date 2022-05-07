package org.nekobucket.catinbucket.item.armor

import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.Items
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile
import org.nekobucket.catinbucket.datagen.models.ItemModels
import org.nekobucket.catinbucket.datagen.recipes.{ Recipe, Recipes }
import org.nekobucket.catinbucket.item.CatIngot
import org.nekobucket.catinbucket.item.level.BaseArmorMaterial
import org.nekobucket.catinbucket.mod.BaseObject
import org.nekobucket.catinbucket.mod.registry.{ ItemRegistry, Register, Registry }

@Register.AsItem
case class CatLeggings protected() extends BaseArmorItem(BaseArmorMaterial.CAT, EquipmentSlot.LEGS)

object CatLeggings extends BaseObject[CatLeggings]("cat_leggings") with CatLeggingsRecipe with CatLeggingsItemModel

sealed trait CatLeggingsRecipe {
  this: CatLeggings.type =>

  Recipes += Recipe.of(s"${ID}_from_crafting") {
    ShapedRecipeBuilder.shaped(Registry.get[CatLeggings], 1)
      .pattern("OOO")
      .pattern("O O")
      .pattern("X X")
      .define('X', Items.LEATHER)
      .define('O', Registry.get[CatIngot])
  }
}

sealed trait CatLeggingsItemModel {
  this: CatLeggings.type =>

  ItemModels += (_.getBuilder(ID)
    .parent(new UncheckedModelFile("minecraft:item/iron_leggings"))
    .texture("layer0", s"item/$ID")
    )
}
