package org.nekobucket.nekotools.mod.tab

import org.nekobucket.nekotools.block.NekoBlock.Item
import org.nekobucket.nekotools.mod.MOD_ID
import org.nekobucket.nekotools.mod.registry.ItemRegistry
import net.minecraft.item.{ ItemGroup, ItemStack }
import org.nekobucket.nekotools.util.Extensions._

object NekoToolsTab extends ItemGroup(MOD_ID){
  override def makeIcon(): ItemStack = ItemRegistry.get[Item].toItemStack
}
