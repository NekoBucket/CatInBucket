package org.nekobucket.catinbucket.mod.tab

import net.minecraft.world.item.{ CreativeModeTab, ItemStack }
import org.nekobucket.catinbucket.block.CatBlock.Item
import org.nekobucket.catinbucket.mod.MOD_ID
import org.nekobucket.catinbucket.mod.registry.ItemRegistry
import org.nekobucket.catinbucket.util.Extensions._

object ModTab extends CreativeModeTab(MOD_ID){
  override def makeIcon(): ItemStack = ItemRegistry.get[Item].toItemStack
}
