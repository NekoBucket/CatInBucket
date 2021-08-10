package org.nekobucket.nekotools.block

import net.minecraft.block.AbstractBlock.Properties
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.data.ShapedRecipeBuilder
import org.nekobucket.nekotools.datagen.recipes.Recipe
import org.nekobucket.nekotools.item.{ NekoBlockItem, NekoIngot }
import org.nekobucket.nekotools.mod.NekoObject
import org.nekobucket.nekotools.mod.registry.ItemRegistry

class NekoBlock extends NekoBlockBase(Properties
  .of(Material.METAL)
  .requiresCorrectToolForDrops
  .strength(5, 6)
  .sound(SoundType.METAL)
)

object NekoBlock extends NekoObject[NekoBlock] {
  override val ID: String = "neko_block"

  class Item extends NekoBlockItem(NekoBlock.get)

  object Item extends NekoObject[NekoBlock.Item] {
    override val ID: String = "neko_block"

    def recipeBuilders: List[Recipe] = List(
      // 9 NekoIngot -> 1 NekoBlock
      Recipe.of("neko_block_from_crafting_neko_ingot") {
        ShapedRecipeBuilder.shaped(ItemRegistry.get[NekoBlock.Item], 1)
          .pattern("XXX")
          .pattern("XXX")
          .pattern("XXX")
          .define('X', ItemRegistry.get[NekoIngot])
      }

    )
  }
}