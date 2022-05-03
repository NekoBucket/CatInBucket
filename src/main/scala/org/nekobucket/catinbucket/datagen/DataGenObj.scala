package org.nekobucket.catinbucket.datagen

import scala.collection.mutable.ArrayBuffer


// The base class for datagen companion objects.
trait DataGenObj[T] {
  val array: ArrayBuffer[T] = ArrayBuffer.empty
  def add(newItem: => T): Unit = array += newItem
  def +=(newItem: => T): Unit = add(newItem)
}
