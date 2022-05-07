package org.nekobucket.catinbucket.datagen.models

import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.{ ItemModelBuilder, ItemModelProvider }
import net.minecraftforge.common.data.ExistingFileHelper
import org.nekobucket.catinbucket.datagen.DataGenObj
import org.nekobucket.catinbucket.mod.MOD_ID

import scala.collection.mutable.ArrayBuffer

private[datagen] class ItemModels(implicit generator: DataGenerator, implicit val fileHelper: ExistingFileHelper)
  extends ItemModelProvider(generator, MOD_ID, fileHelper) with Models[ItemModelBuilder] {

  override def registerModels(): Unit = array.foreach(_(this))

  override def array: ArrayBuffer[Models[ItemModelBuilder] => ItemModelBuilder] = ItemModels.array
}

private[catinbucket] object ItemModels extends DataGenObj[Models[ItemModelBuilder] => ItemModelBuilder]
