package org.nekobucket.catinbucket.mod.registry

import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.{ DeferredRegister, ForgeRegistries }
import org.nekobucket.catinbucket.mod.MOD_ID

object BlockRegistry extends StaticRegister[Block, Register.AsBlock] with Registry[Block] {
  override protected val entries: DeferredRegister[Block] = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID)
}
