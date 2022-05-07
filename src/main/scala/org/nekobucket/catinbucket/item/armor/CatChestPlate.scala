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
import org.nekobucket.catinbucket.mod.registry.{ Register, Registry }

@Register.AsItem
case class CatChestPlate protected() extends BaseArmorItem(BaseArmorMaterial.CAT, EquipmentSlot.CHEST)

object CatChestPlate extends BaseObject[CatChestPlate]("cat_chestplate")
  with CatChestPlateRecipe with CatChestPlateItemModel

sealed trait CatChestPlateRecipe {
  this: CatChestPlate.type =>

  Recipes += Recipe.of(s"${ID}_from_crafting") {
    ShapedRecipeBuilder.shaped(Registry.get[CatChestPlate], 1)
      .pattern("X X")
      .pattern("OOO")
      .pattern("OOO")
      .define('X', Items.LEATHER)
      .define('O', Registry.get[CatIngot])
  }
}

sealed trait CatChestPlateItemModel {
  this: CatChestPlate.type =>

  ItemModels += (_.getBuilder(ID)
    .parent(new UncheckedModelFile("minecraft:item/iron_chestplate"))
    .texture("layer0", s"item/$ID")
    )
}
