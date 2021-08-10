package org.nekobucket.nekotools.item

import net.minecraft.item.Item
import net.minecraft.item.Item.Properties
import org.nekobucket.nekotools.mod.NekoObject
import org.nekobucket.nekotools.mod.tab.NekoToolsTab

class TestItem extends Item(new Properties().tab(NekoToolsTab).stacksTo(16))

object TestItem extends NekoObject[TestItem] {
  final val ID = "test_item"
}