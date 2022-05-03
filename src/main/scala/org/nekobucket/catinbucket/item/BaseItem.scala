package org.nekobucket.catinbucket.item

import net.minecraft.world.level.block.Block
import net.minecraft.world.item.{ BlockItem, Item }
import net.minecraft.world.item.Item.Properties
import org.nekobucket.catinbucket.mod.tab.ModTab

abstract class BaseItem(properties: Properties) extends Item(properties.tab(ModTab)) {
  def this() = this(new Properties)
}

abstract class BaseBlockItem(block: Block, properties: Properties) extends BlockItem(block, properties.tab(ModTab)) {
  def this(block: Block) = this(block, new Properties)
}