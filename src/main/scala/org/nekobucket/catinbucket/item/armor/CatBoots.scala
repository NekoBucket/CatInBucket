package org.nekobucket.catinbucket.item.armor

import net.minecraft.core.NonNullList
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.{ CreativeModeTab, ItemStack, Items }
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile
import org.nekobucket.catinbucket.datagen.models.ItemModels
import org.nekobucket.catinbucket.datagen.recipes.{ Recipe, Recipes }
import org.nekobucket.catinbucket.item.CatIngot
import org.nekobucket.catinbucket.item.level.BaseArmorMaterial
import org.nekobucket.catinbucket.mod.BaseObject
import org.nekobucket.catinbucket.mod.registry.{ ItemRegistry, Register, Registry }
import org.nekobucket.catinbucket.util.Extensions.{ AnyExt, ItemExt }

@Register.AsItem
case class CatBoots() extends BaseArmorItem(BaseArmorMaterial.CAT, EquipmentSlot.FEET) {
  // when crafted, give a fall protection 2 enchantment
  override def onCraftedBy(pStack: ItemStack, pLevel: Level, pPlayer: Player): Unit = {
    pStack.enchant(Enchantments.FALL_PROTECTION, 2)
  }

  override def fillItemCategory(pCategory: CreativeModeTab, pItems: NonNullList[ItemStack]): Unit = {
    pItems.add(this.toItemStack.also {
      _.enchant(Enchantments.FALL_PROTECTION, 2)
    })
  }
}

object CatBoots extends BaseObject[CatBoots]("cat_boots") with CatBootsRecipe with CatBootsItemModel

trait CatBootsRecipe {
  this: CatBoots.type =>

  Recipes +~ Recipe.of(s"${ID}_from_crafting") {
    ShapedRecipeBuilder.shaped(Registry.get[CatBoots], 1)
      .pattern("X X")
      .pattern("O O")
      .define('X', Items.LEATHER)
      .define('O', Registry.get[CatIngot])
  }
}

trait CatBootsItemModel {
  this: CatBoots.type =>

  ItemModels += (_.getBuilder(CatBoots.ID)
    .parent(new UncheckedModelFile("minecraft:item/iron_boots"))
    .texture("layer0", s"item/$ID")
  )
}
