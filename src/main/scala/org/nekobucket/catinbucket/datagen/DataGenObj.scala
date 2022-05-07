package org.nekobucket.catinbucket.datagen

import scala.collection.mutable.ArrayBuffer


// The base class for datagen companion objects.
private[datagen] trait DataGenObj[T] {
  val array: ArrayBuffer[T] = ArrayBuffer.empty
  def add(newItem: => T): Unit = array += newItem
  def += (newItem: => T): Unit = add(newItem)
}

private[datagen] trait DataGenObjAddableWithFunction[T] extends DataGenObj[() => T] {
  def addAsFunc(newItem: => T): Unit = array += (() => newItem)
  def += (newItem: => T)(implicit d: DummyImplicit): Unit = addAsFunc(newItem)
}