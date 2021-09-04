package org.nekobucket.nekotools.mod.registry

import org.nekobucket.nekotools.mod.MOD_ID
import net.minecraft.block.Block
import net.minecraftforge.registries.{ DeferredRegister, ForgeRegistries }

private[nekotools] object BlockRegistry extends StaticRegister[Block, Register.AsBlock] with Registry[Block] {
  final val entries = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID)
}
