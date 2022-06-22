package org.nekobucket.catinbucket.util

import net.minecraft.world.item.{ Item, ItemStack }

import scala.language.implicitConversions
import scala.jdk.CollectionConverters._

/**
 * Extension methods for several types.
 */
object Extensions {

  implicit class AnyExt[T](x: T) {
    def ifElse[R](predicate: T => Boolean)(doTrue: T => R, doFalse: T => R): R =
      if (predicate(x)) doTrue(x)
      else doFalse(x)

    // pipe operator
    def let[R](f: T => R): R = f(x)
    def |>[R](f: T => R) : R = let[R](f)

    def also(f: T => Any): T = {
      f(x)
      x
    }
  }

  implicit class ItemExt(item: Item) {
    def toItemStack = new ItemStack(item)
  }

  implicit class ItemStackExt(private val itemStack: ItemStack) {
    // adjust count in ItemStack
    def addCount(n: Int): ItemStack = this.also { _ =>
      itemStack.setCount(itemStack.getCount + n)
    }.itemStack
    def reduceCount(n: Int): ItemStack = addCount(-n)
    // custom operators
    def += (n: Int): ItemStack = addCount(n)
    def -= (n: Int): ItemStack = reduceCount(n)
    def ++ : ItemStack = this += 1
    def -- : ItemStack = this -= 1
  }

  implicit class JavaListExt[T](l: java.util.List[T]) {
    def toList: List[T] = l.asScala.toList
  }
}
