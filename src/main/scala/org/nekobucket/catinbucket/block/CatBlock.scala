package org.nekobucket.catinbucket.block

import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.material.Material
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile
import org.nekobucket.catinbucket.datagen.models.ItemModels
import org.nekobucket.catinbucket.datagen.recipes.{ Recipe, Recipes }
import org.nekobucket.catinbucket.item.{ BaseBlockItem, CatIngot }
import org.nekobucket.catinbucket.mod.{ BaseObject, MOD_ID }
import org.nekobucket.catinbucket.mod.registry.{ BlockRegistry, ItemRegistry, Register }

@Register.AsBlock
case class CatBlock() extends BaseBlock(Properties
  .of(Material.METAL)
  .requiresCorrectToolForDrops
  .strength(5, 6)
  .sound(SoundType.METAL)
)

object CatBlock extends BaseObject[CatBlock]("cat_block") {
  @Register.AsItem
  case class Item() extends BaseBlockItem(BlockRegistry.get[CatBlock])
  object Item extends BaseObject[CatBlock.Item](ID) with CatBlockRecipe with CatBlockItemModel
}

trait CatBlockRecipe {
  this: CatBlock.Item.type =>

  Recipes +~ Recipe.of(s"${ ID }_from_crafting_${CatIngot.ID}") {
    ShapedRecipeBuilder.shaped(ItemRegistry.get[CatBlock.Item], 1)
      .pattern("XXX")
      .pattern("XXX")
      .pattern("XXX")
      .define('X', ItemRegistry.get[CatIngot])
  }
}

trait CatBlockItemModel {
  this: CatBlock.Item.type =>

  ItemModels += (
    _.getBuilder(ID)
      .parent(new UncheckedModelFile(s"$MOD_ID:block/$ID"))
    )
}
