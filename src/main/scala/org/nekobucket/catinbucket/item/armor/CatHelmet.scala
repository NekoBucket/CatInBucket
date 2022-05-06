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
case class CatHelmet() extends BaseArmorItem(BaseArmorMaterial.CAT, EquipmentSlot.HEAD)

object CatHelmet extends BaseObject[CatHelmet]("cat_helmet") with CatHelmetRecipe with CatHelmetItemModel

trait CatHelmetRecipe {
  this: CatHelmet.type =>

  Recipes +~ Recipe.of(s"${ID}_from_crafting") {
    ShapedRecipeBuilder.shaped(Registry.get[CatHelmet], 1)
      .pattern("OOO")
      .pattern("X X")
      .define('X', Items.LEATHER)
      .define('O', Registry.get[CatIngot])
  }
}

trait CatHelmetItemModel {
  this: CatHelmet.type =>

  ItemModels += (_.getBuilder(ID)
    .parent(new UncheckedModelFile("minecraft:item/iron_helmet"))
    .texture("layer0", s"item/$ID")
    )
}
