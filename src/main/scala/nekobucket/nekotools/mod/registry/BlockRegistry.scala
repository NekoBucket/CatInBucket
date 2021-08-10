package nekobucket.nekotools.mod.registry

import nekobucket.nekotools.block.NekoBlock
import nekobucket.nekotools.mod.MOD_ID
import net.minecraft.block.Block
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.{ DeferredRegister, ForgeRegistries }

object BlockRegistry extends Registry[Block] {
  final val ENTRIES = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID)

  // blocks
  val nekoBlock: RegistryObject[NekoBlock] = register[NekoBlock](NekoBlock)
}
