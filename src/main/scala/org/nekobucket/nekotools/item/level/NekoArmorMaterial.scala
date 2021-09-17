package org.nekobucket.nekotools.item.level

import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.{ LazyValue, SoundEvent, SoundEvents }
import net.minecraftforge.api.distmarker.{ Dist, OnlyIn }
import org.nekobucket.nekotools.item.NekoIngot
import org.nekobucket.nekotools.mod.registry.ItemRegistry

import java.util.function.Supplier

sealed abstract class NekoArmorMaterial(
  private val name: String,
  private val durabilityMultiplier: Int,
  private val slotProtections: Array[Int],
  private val enchantmentValue: Int,
  private val sound: SoundEvent,
  private val toughness: Float,
  private val knockbackResistance: Float,
  repairMaterial: Supplier[Ingredient]
) extends IArmorMaterial {

  private val HEALTH_PER_SLOT: Array[Int] = Array[Int](13, 15, 16, 11)
  private val repairIngredient: LazyValue[Ingredient] = new LazyValue[Ingredient](repairMaterial)

  override def getDurabilityForSlot(slotIn: EquipmentSlotType): Int =
    HEALTH_PER_SLOT(slotIn.getIndex) * this.durabilityMultiplier

  override def getDefenseForSlot(slotIn: EquipmentSlotType): Int = this.slotProtections(slotIn.getIndex)

  override def getEnchantmentValue: Int = this.enchantmentValue

  override def getEquipSound: SoundEvent = this.sound

  override def getRepairIngredient: Ingredient = this.repairIngredient.get

  @OnlyIn(Dist.CLIENT) override def getName: String = this.name

  override def getToughness: Float = this.toughness

  /**
   * Gets the percentage of knockback resistance provided by armor of the material.
   */
  override def getKnockbackResistance: Float = this.knockbackResistance
}

object NekoArmorMaterial {
  // Attributes follow Leather
  object NEKO extends NekoArmorMaterial("neko", 5, List(1, 2, 3, 1).toArray, 15,
    SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 0.0F, () => Ingredient.of(ItemRegistry.get[NekoIngot]))
}
