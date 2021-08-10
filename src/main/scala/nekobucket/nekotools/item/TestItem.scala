package nekobucket.nekotools.item

import nekobucket.nekotools.mod.NekoObject
import nekobucket.nekotools.mod.tab.NekoToolsTab
import net.minecraft.item.Item
import net.minecraft.item.Item.Properties

class TestItem extends Item(new Properties().tab(NekoToolsTab).stacksTo(16))

object TestItem extends NekoObject[TestItem] {
  final val ID = "test_item"
}