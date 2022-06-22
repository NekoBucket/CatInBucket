package org.nekobucket.catinbucket.entity

import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import org.nekobucket.catinbucket.mod.registry.BlockEntityRegistry

abstract class BaseBlockEntity[T <: BaseBlockEntity[T]](pWorldPosition: BlockPos, pBlockState: BlockState)(implicit ID: String)
  extends BlockEntity(BlockEntityRegistry.get[T](ID), pWorldPosition, pBlockState) {

  def sync(): Unit = {
    if (!level.isClientSide) {
      val p = ClientboundBlockEntityDataPacket.create(this)
      level.asInstanceOf[ServerLevel].getChunkSource.chunkMap.getPlayers(new ChunkPos(getBlockPos), false)
        .forEach(_.connection.send(p))
      setChanged()
    }
  }
}

