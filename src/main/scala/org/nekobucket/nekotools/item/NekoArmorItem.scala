package org.nekobucket.nekotools.item

import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.Item.Properties
import net.minecraft.item.{ ArmorItem, IArmorMaterial }
import org.nekobucket.nekotools.mod.tab.NekoToolsTab

class NekoArmorItem(material: IArmorMaterial, slot: EquipmentSlotType, property: Properties)
  extends ArmorItem(material, slot, property.tab(NekoToolsTab)) {

  def this(material: IArmorMaterial, slot: EquipmentSlotType) = this(material, slot, new Properties)
}
