package nekobucket.nekotools.block

import nekobucket.nekotools.datagen.recipes.Recipe
import nekobucket.nekotools.mod.NekoObject
import net.minecraft.block.AbstractBlock.Properties
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import nekobucket.nekotools.item.{ NekoIngot, NekoBlockItem => NekoBlockItemBase }
import nekobucket.nekotools.mod.registry.ItemRegistry
import net.minecraft.data.ShapedRecipeBuilder

class NekoBlock extends NekoBlockBase(Properties
  .of(Material.METAL)
  .requiresCorrectToolForDrops
  .strength(5, 6)
  .sound(SoundType.METAL)
)

object NekoBlock extends NekoObject[NekoBlock] {
  override val ID: String = "neko_block"

  class Item extends NekoBlockItemBase(NekoBlock.get)

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