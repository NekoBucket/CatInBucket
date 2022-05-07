package org.nekobucket.catinbucket.item.armor

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.{ ArmorItem, ArmorMaterial }
import net.minecraft.world.item.Item.Properties
import org.nekobucket.catinbucket.mod.tab.MainTab

abstract class BaseArmorItem protected(material: ArmorMaterial, slot: EquipmentSlot, property: Properties)
  extends ArmorItem(material, slot, property.tab(MainTab)) {

  protected def this(material: ArmorMaterial, slot: EquipmentSlot) = this(material, slot, new Properties)
}
