package org.nekobucket.nekotools.util

import net.minecraft.item.{ Item, ItemStack }


object Extensions {
  implicit class ItemExt(item: Item) {
    def toItemStack = new ItemStack(item)
  }

  implicit class AnyExt[T](x: T) {
    def ifElse[R](predicate: T => Boolean, doTrue: T => R, doFalse: T => R): R =
      if (predicate(x)) doTrue(x)
      else doFalse(x)

    // pipe operator
    def let[R](f: T => R): R = f(x)
    def let[R](f: () => R): R = f()
    def |>[R](f: T => R) : R = let[R](f)
    def |>[R](f: () => R): R = let[R](f)

    def also(f: T => Any): T = {
      f(x)
      x
    }
  }

  implicit class ItemStackExt(itemStack: ItemStack) {
    // adjust count in ItemStack
    def addCount(n: Int): ItemStack = {
      itemStack.setCount(itemStack.getCount + n)
      itemStack
    }
    def reduceCount(n: Int): ItemStack = addCount(-n)
    // custom operators
    def += (n: Int): ItemStack = addCount(n)
    def -= (n: Int): ItemStack = reduceCount(n)
    def ++ : ItemStack = this += 1
    def -- : ItemStack = this -= 1
  }
}
