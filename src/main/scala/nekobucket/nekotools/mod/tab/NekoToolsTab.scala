package nekobucket.nekotools.mod.tab

import nekobucket.nekotools.mod.{ MOD_ID }
import net.minecraft.item.{ ItemGroup, ItemStack, Items }
import nekobucket.nekotools.util.Extensions._

object NekoToolsTab extends ItemGroup(MOD_ID){
  override def makeIcon(): ItemStack = Items.PINK_WOOL.toItemStack  // TODO
}
