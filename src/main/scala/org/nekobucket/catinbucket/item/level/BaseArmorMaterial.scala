package org.nekobucket.catinbucket.item.level

import net.minecraft.sounds.{ SoundEvent, SoundEvents }
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.api.distmarker.{ Dist, OnlyIn }
import org.nekobucket.catinbucket.item.CatIngot
import org.nekobucket.catinbucket.mod.registry.{ ItemRegistry, Registry }

sealed abstract class BaseArmorMaterial(
  private val name: String,
  private val durabilityMultiplier: Int,
  private val slotProtections: Array[Int],
  private val enchantmentValue: Int,
  private val sound: SoundEvent,
  private val toughness: Float,
  private val knockbackResistance: Float,
  repairMaterial: => Ingredient
) extends ArmorMaterial {

  private val HEALTH_PER_SLOT: Array[Int] = Array[Int](13, 15, 16, 11)

  override def getDurabilityForSlot(pSlot: EquipmentSlot): Int =
    HEALTH_PER_SLOT(pSlot.getIndex) * this.durabilityMultiplier

  override def getDefenseForSlot(pSlot: EquipmentSlot): Int = this.slotProtections(pSlot.getIndex)

  override def getEnchantmentValue: Int = this.enchantmentValue

  override def getEquipSound: SoundEvent = this.sound

  override def getRepairIngredient: Ingredient = this.repairMaterial

  @OnlyIn(Dist.CLIENT) override def getName: String = this.name

  override def getToughness: Float = this.toughness

  /**
   * Gets the percentage of knockback resistance provided by armor of the material.
   */
  override def getKnockbackResistance: Float = this.knockbackResistance
}

object BaseArmorMaterial {
  // Attributes follow Leather
  object CAT extends BaseArmorMaterial("cat", 5, List(1, 2, 3, 1).toArray, 15,
    SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 0.0F, Ingredient.of(Registry.get[CatIngot]))
}
