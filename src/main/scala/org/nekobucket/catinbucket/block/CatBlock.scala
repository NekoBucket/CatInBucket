package org.nekobucket.catinbucket.block

import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.material.Material
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile
import org.nekobucket.catinbucket.datagen.blockstates.{ BlockState, BlockStates }
import org.nekobucket.catinbucket.datagen.models.ItemModels
import org.nekobucket.catinbucket.datagen.recipes.{ Recipe, Recipes }
import org.nekobucket.catinbucket.item.{ BaseBlockItem, CatIngot }
import org.nekobucket.catinbucket.mod.registry.{ Register, Registry }
import org.nekobucket.catinbucket.mod.{ BaseObject, MOD_ID }

@Register.AsBlock
case class CatBlock protected() extends BaseBlock(Properties
  .of(Material.METAL)
  .requiresCorrectToolForDrops
  .strength(5, 6)
  .sound(SoundType.METAL)
)

object CatBlock extends BaseObject[CatBlock]("cat_block") with CatBlockBlockState {
  @Register.AsItem
  case class Item() extends BaseBlockItem(Registry.get[CatBlock])
  object Item extends BaseObject[CatBlock.Item](ID) with CatBlockRecipe with CatBlockItemModel
}

sealed trait CatBlockRecipe {
  this: CatBlock.Item.type =>

  Recipes += Recipe.of(s"${ ID }_from_crafting_${CatIngot.ID}") {
    ShapedRecipeBuilder.shaped(Registry.get[CatBlock.Item], 1)
      .pattern("XXX")
      .pattern("XXX")
      .pattern("XXX")
      .define('X', Registry.get[CatIngot])
  }
}

sealed trait CatBlockItemModel {
  this: CatBlock.Item.type =>

  ItemModels += (
    _.getBuilder(ID)
      .parent(new UncheckedModelFile(s"$MOD_ID:block/$ID"))
    )
}

sealed trait CatBlockBlockState {
  this: CatBlock.type =>

  BlockStates += BlockState.ofSimple
}
