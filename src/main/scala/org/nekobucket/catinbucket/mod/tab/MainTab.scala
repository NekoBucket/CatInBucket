package org.nekobucket.catinbucket.mod.tab

import net.minecraft.world.item.{ CreativeModeTab, ItemStack }
import org.nekobucket.catinbucket.item.CatBucket
import org.nekobucket.catinbucket.mod.MOD_ID
import org.nekobucket.catinbucket.mod.registry.Registry
import org.nekobucket.catinbucket.util.Extensions._

private[catinbucket] object MainTab extends CreativeModeTab(MOD_ID){
  override def makeIcon(): ItemStack = Registry.get[CatBucket].toItemStack
}
