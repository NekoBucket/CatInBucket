package org.nekobucket.catinbucket.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier
import net.minecraft.world.level.block.state.BlockState
import org.nekobucket.catinbucket.mod.BaseObject
import org.nekobucket.catinbucket.mod.registry.Registry

import scala.reflect.ClassTag

abstract class BaseBlockEntityObject[T <: BlockEntity, B <: Block] protected(implicit ID: String, ct: ClassTag[T], val tagB: ClassTag[B])
  extends BaseObject[T](ID) with BlockEntitySupplier[T] {
  override def create(pPos: BlockPos, pState: BlockState): T = this.apply(pPos, pState)
  def apply(pPos: BlockPos, pState: BlockState): T
  override def apply: T = throw new Exception("No argument is not allowed.")

  override implicit protected def id: String = ID
  override implicit def resolve: () => T = () => Registry.get(ct)
}
