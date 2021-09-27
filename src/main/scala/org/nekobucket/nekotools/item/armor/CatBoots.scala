package org.nekobucket.nekotools.item.armor

import net.minecraft.data.ShapedRecipeBuilder
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.{ ItemGroup, ItemStack, Items }
import net.minecraft.util.NonNullList
import net.minecraft.world.World
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile
import org.nekobucket.nekotools.datagen.models.ItemModels
import org.nekobucket.nekotools.datagen.recipes.{ Recipe, Recipes }
import org.nekobucket.nekotools.item.NekoIngot
import org.nekobucket.nekotools.item.level.NekoArmorMaterial
import org.nekobucket.nekotools.mod.NekoObject
import org.nekobucket.nekotools.mod.registry.{ ItemRegistry, Register }
import org.nekobucket.nekotools.util.Extensions.{ AnyExt, ItemExt }
import space.controlnet.lightioc.annotation.Singleton

@Singleton
@Register.AsItem
class CatBoots extends NekoArmorItem(NekoArmorMaterial.NEKO, EquipmentSlotType.FEET) {
  // when crafted, give a fall protection 2 enchantment
  override def onCraftedBy(stack: ItemStack, worldIn: World, playerIn: PlayerEntity): Unit = {
    stack.enchant(Enchantments.FALL_PROTECTION, 2)
  }

  override def fillItemCategory(group: ItemGroup, items: NonNullList[ItemStack]): Unit = {
    items.add(this.toItemStack.also {
      _.enchant(Enchantments.FALL_PROTECTION, 2)
    })
  }
}

object CatBoots extends NekoObject[CatBoots]("cat_boots") with CatBootsRecipe with CatBootsItemModel

trait CatBootsRecipe {
  this: CatBoots.type =>

  Recipes +~ Recipe.of(s"${ID}_from_crafting") {
    ShapedRecipeBuilder.shaped(ItemRegistry.get[CatBoots], 1)
      .pattern("X X")
      .pattern("O O")
      .define('X', Items.LEATHER)
      .define('O', ItemRegistry.get[NekoIngot])
  }
}

trait CatBootsItemModel {
  this: CatBoots.type =>

  ItemModels += (_.getBuilder(CatBoots.ID)
    .parent(new UncheckedModelFile("minecraft:item/iron_boots"))
    .texture("layer0", s"item/$ID")
  )
}