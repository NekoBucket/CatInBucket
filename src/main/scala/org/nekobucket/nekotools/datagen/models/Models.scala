package org.nekobucket.nekotools.datagen.models

import net.minecraftforge.client.model.generators.{ ModelBuilder, ModelProvider }

import scala.collection.mutable.ArrayBuffer

trait Models[T <: ModelBuilder[T]] extends ModelProvider[T] {

  def array: ArrayBuffer[Models[T] => T]

  def registerModels(): Unit = array.foreach(_(this))
}