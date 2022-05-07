package org.nekobucket.catinbucket.datagen.models

import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.{ BlockModelBuilder, BlockModelProvider }
import net.minecraftforge.common.data.ExistingFileHelper
import org.nekobucket.catinbucket.datagen.DataGenObj
import org.nekobucket.catinbucket.mod.MOD_ID

import scala.collection.mutable.ArrayBuffer

private[datagen] class BlockModels(implicit generator: DataGenerator, implicit val fileHelper: ExistingFileHelper)
  extends BlockModelProvider(generator, MOD_ID, fileHelper) with Models[BlockModelBuilder] {

  override def registerModels(): Unit = array.foreach(_(this))

  override def array: ArrayBuffer[Models[BlockModelBuilder] => BlockModelBuilder] = BlockModels.array
}

private[catinbucket] object BlockModels extends DataGenObj[Models[BlockModelBuilder] => BlockModelBuilder]