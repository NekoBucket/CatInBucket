package org.nekobucket.catinbucket.item

import net.minecraft.world.level.block.Block
import net.minecraft.world.item.{ BlockItem, Item }
import net.minecraft.world.item.Item.Properties
import org.nekobucket.catinbucket.mod.tab.MainTab

abstract class BaseItem protected(properties: Properties) extends Item(properties.tab(MainTab)) {
  protected def this() = this(new Properties)
}

abstract class BaseBlockItem protected(block: Block, properties: Properties)
  extends BlockItem(block, properties.tab(MainTab)) {
  protected def this(block: Block) = this(block, new Properties)
}
