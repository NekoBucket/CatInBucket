package org.nekobucket.nekotools.block

import net.minecraft.block.AbstractBlock.Properties
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.data.ShapedRecipeBuilder
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile
import org.nekobucket.nekotools.datagen.models.ItemModels
import org.nekobucket.nekotools.datagen.recipes.{ Recipe, Recipes }
import org.nekobucket.nekotools.item.{ NekoBlockItemBase, NekoIngot }
import org.nekobucket.nekotools.mod.{ MOD_ID, NekoObject }
import org.nekobucket.nekotools.mod.registry.{ ItemRegistry, Register }
import space.controlnet.lightioc.annotation.Singleton

@Singleton
@Register.AsBlock
class NekoBlock extends NekoBlockBase(Properties
  .of(Material.METAL)
  .requiresCorrectToolForDrops
  .strength(5, 6)
  .sound(SoundType.METAL)
)

object NekoBlock extends NekoObject[NekoBlock] {
  override val ID: String = "neko_block"

  @Singleton
  @Register.AsItem
  class Item extends NekoBlockItemBase(NekoBlock.get)

  object Item extends NekoObject[NekoBlock.Item] with NekoBlockRecipe with NekoBlockItemModel {
    override val ID: String = NekoBlock.ID
  }
}

trait NekoBlockRecipe {
  Recipes +~ Recipe.of("neko_block_from_crafting_neko_ingot") {
    ShapedRecipeBuilder.shaped(ItemRegistry.get[NekoBlock.Item], 1)
      .pattern("XXX")
      .pattern("XXX")
      .pattern("XXX")
      .define('X', ItemRegistry.get[NekoIngot])
  }
}

trait NekoBlockItemModel {
  ItemModels += (
    _.getBuilder(NekoBlock.ID)
      .parent(new UncheckedModelFile(s"$MOD_ID:block/${NekoBlock.ID}"))
    )
}
