package org.nekobucket.nekotools.item.armor

import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.Items
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile
import org.nekobucket.nekotools.datagen.models.ItemModels
import org.nekobucket.nekotools.datagen.recipes.{ Recipe, Recipes }
import org.nekobucket.nekotools.item.NekoIngot
import org.nekobucket.nekotools.item.level.NekoArmorMaterial
import org.nekobucket.nekotools.mod.NekoObject
import org.nekobucket.nekotools.mod.registry.{ ItemRegistry, Register }
import space.controlnet.lightioc.annotation.Singleton

@Singleton
@Register.AsItem
class CatChestPlate extends NekoArmorItem(NekoArmorMaterial.NEKO, EquipmentSlotType.CHEST)

object CatChestPlate extends NekoObject[CatChestPlate]("cat_chestplate") with CatChestPlateRecipe with CatChestPlateItemModel

trait CatChestPlateRecipe {
  this: CatChestPlate.type =>

  Recipes +~ Recipe.of(s"${ID}_from_crafting") {
    ShapedRecipeBuilder.shaped(ItemRegistry.get[CatChestPlate], 1)
      .pattern("X X")
      .pattern("OOO")
      .pattern("OOO")
      .define('X', Items.LEATHER)
      .define('O', ItemRegistry.get[NekoIngot])
  }
}

trait CatChestPlateItemModel {
  this: CatChestPlate.type =>

  ItemModels += (_.getBuilder(ID)
    .parent(new UncheckedModelFile("minecraft:item/iron_chestplate"))
    .texture("layer0", s"item/$ID")
    )
}
