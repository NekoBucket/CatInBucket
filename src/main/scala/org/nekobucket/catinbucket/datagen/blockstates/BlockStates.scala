package org.nekobucket.catinbucket.datagen.blockstates

import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.common.data.ExistingFileHelper
import org.nekobucket.catinbucket.datagen.DataGenObj
import org.nekobucket.catinbucket.mod.MOD_ID

class BlockStates(implicit generator: DataGenerator, implicit val fileHelper: ExistingFileHelper)
  extends BlockStateProvider(generator, MOD_ID, fileHelper) {
  import BlockStates._

  override def registerStatesAndModels(): Unit = array.foreach(_.save(this))
}

object BlockStates extends DataGenObj[BlockState]