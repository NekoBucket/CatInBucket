package org.nekobucket.catinbucket.datagen.blockstates

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.BlockStateProvider
import org.nekobucket.catinbucket.mod.MOD_ID

case class BlockState(func: BlockState.BlockStateFunc) {
  def save(provider: BlockStateProvider): Unit = func(provider)
}

object BlockState {
  type BlockStateFunc = BlockStateProvider => Unit

  def ofSimple(implicit id: String, getBlock: () => Block): BlockState = BlockState { it =>
    it.getVariantBuilder(getBlock())
      .partialState
      .modelForState
      .modelFile(it.models.getExistingFile(new ResourceLocation(s"$MOD_ID:block/$id")))
      .addModel()
  }
}
