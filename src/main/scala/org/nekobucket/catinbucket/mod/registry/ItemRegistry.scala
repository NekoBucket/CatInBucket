package org.nekobucket.catinbucket.mod.registry

import net.minecraft.world.item.Item
import net.minecraftforge.registries.{ DeferredRegister, ForgeRegistries }
import org.nekobucket.catinbucket.mod.MOD_ID

private[catinbucket] object ItemRegistry extends StaticRegister[Item, Register.AsItem] with Registry[Item] {
  override val entries: DeferredRegister[Item] = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID)
}