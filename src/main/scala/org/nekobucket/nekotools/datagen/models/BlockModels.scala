package org.nekobucket.nekotools.datagen.models

import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.{ BlockModelBuilder, BlockModelProvider }
import net.minecraftforge.common.data.ExistingFileHelper
import org.nekobucket.nekotools.datagen.DataGenObj
import org.nekobucket.nekotools.mod.MOD_ID

import scala.collection.mutable.ArrayBuffer

class BlockModels(implicit generator: DataGenerator, implicit val fileHelper: ExistingFileHelper)
  extends BlockModelProvider(generator, MOD_ID, fileHelper) with Models[BlockModelBuilder] {

  override def registerModels(): Unit = array.foreach(_(this))

  override def array: ArrayBuffer[Models[BlockModelBuilder] => BlockModelBuilder] = BlockModels.array
}

object BlockModels extends DataGenObj[Models[BlockModelBuilder] => BlockModelBuilder]