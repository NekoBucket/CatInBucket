package nekobucket.nekotools.mod.registry

import nekobucket.nekotools.item.TestItem
import nekobucket.nekotools.mod.MOD_ID
import net.minecraft.item.Item
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.{ DeferredRegister, ForgeRegistries }

object ItemRegistry {
  final val ITEMS: DeferredRegister[Item] = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID)

  // add items
  val testItem: RegistryObject[TestItem] = ITEMS.register(TestItem.ID, () => new TestItem)
}
