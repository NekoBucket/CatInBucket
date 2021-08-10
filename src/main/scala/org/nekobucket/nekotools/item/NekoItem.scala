package org.nekobucket.nekotools.item

import net.minecraft.block.Block
import net.minecraft.item.{ BlockItem, Item }
import net.minecraft.item.Item.Properties
import org.nekobucket.nekotools.mod.tab.NekoToolsTab

abstract class NekoItem(properties: Properties) extends Item(properties.tab(NekoToolsTab)) {
  def this() = this(new Properties)
}

abstract class NekoBlockItem(block: Block, properties: Properties) extends BlockItem(block, properties.tab(NekoToolsTab)) {
  def this(block: Block) = this(block, new Properties)
}
