package nekobucket.nekotools.mod.tab

import nekobucket.nekotools.block.NekoBlock.Item
import nekobucket.nekotools.mod.MOD_ID
import nekobucket.nekotools.mod.registry.ItemRegistry
import net.minecraft.item.{ ItemGroup, ItemStack }
import nekobucket.nekotools.util.Extensions._

object NekoToolsTab extends ItemGroup(MOD_ID){
  override def makeIcon(): ItemStack = ItemRegistry.get[Item].toItemStack
}
