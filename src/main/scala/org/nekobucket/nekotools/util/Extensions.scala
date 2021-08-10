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

    def let[R](f: T => R): R = f(x)
  }
}
