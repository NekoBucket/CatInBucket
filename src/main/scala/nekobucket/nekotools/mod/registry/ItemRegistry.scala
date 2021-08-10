package nekobucket.nekotools.mod.registry

import nekobucket.nekotools.block.NekoBlock
import nekobucket.nekotools.item.{ NekoIngot, TestItem, TestJavaItem }
import nekobucket.nekotools.mod.MOD_ID
import net.minecraft.item.Item
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.{ DeferredRegister, ForgeRegistries }

import scala.reflect.ClassTag
import nekobucket.nekotools.util.Extensions._

object ItemRegistry extends Registry[Item] {
  override val ENTRIES: DeferredRegister[Item] = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID)

  // add items
  val testItem: RegistryObject[TestItem] = register(TestItem) // scala item example
  val testJavaItem: RegistryObject[TestJavaItem] = new TestJavaItem.Factory(ClassTag(classOf[TestJavaItem]))
    .let(factory => register[TestJavaItem](factory.ID, factory)) // java item example

  val nekoIngot: RegistryObject[NekoIngot] = register(NekoIngot)

  // add block items
  val nekoBlock: RegistryObject[NekoBlock.Item] = register(NekoBlock.Item)
}
