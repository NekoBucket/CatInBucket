package nekobucket.nekotools.util

import net.minecraft.item.{ Item, ItemStack }


object Extensions {
  implicit class ItemExt(item: Item) {
    def toItemStack = new ItemStack(item)
  }
}
