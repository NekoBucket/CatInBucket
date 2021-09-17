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
class CatHelmet extends NekoArmorItem(NekoArmorMaterial.NEKO, EquipmentSlotType.HEAD)

object CatHelmet extends NekoObject[CatHelmet]("cat_helmet") with CatHelmetRecipe with CatHelmetItemModel

trait CatHelmetRecipe {
  this: CatHelmet.type =>

  Recipes +~ Recipe.of(s"${ID}_from_crafting") {
    ShapedRecipeBuilder.shaped(ItemRegistry.get[CatHelmet], 1)
      .pattern("OOO")
      .pattern("X X")
      .define('X', Items.LEATHER)
      .define('O', ItemRegistry.get[NekoIngot])
  }
}

trait CatHelmetItemModel {
  this: CatHelmet.type =>

  ItemModels += (_.getBuilder(ID)
    .parent(new UncheckedModelFile("minecraft:item/iron_helmet"))
    .texture("layer0", s"item/$ID")
    )
}
