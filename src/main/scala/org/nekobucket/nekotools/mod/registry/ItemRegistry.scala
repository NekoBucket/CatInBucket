package org.nekobucket.nekotools.mod.registry

import org.nekobucket.nekotools.mod.MOD_ID
import net.minecraft.item.Item
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.{ DeferredRegister, ForgeRegistries }

import scala.reflect.ClassTag
import org.nekobucket.nekotools.util.Extensions._
import org.nekobucket.nekotools.block.NekoBlock
import org.nekobucket.nekotools.item.{ CatBucket, NekoIngot, TestItem, TestJavaItem }

object ItemRegistry extends Registry[Item] {
  override val ENTRIES: DeferredRegister[Item] = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID)

  // add items
  val testItem: RegistryObject[TestItem] = register(TestItem) // scala item example
  val testJavaItem: RegistryObject[TestJavaItem] = new TestJavaItem.Factory(ClassTag(classOf[TestJavaItem]))
    .let(factory => register[TestJavaItem](factory.ID, factory)) // java item example

  val nekoIngot: RegistryObject[NekoIngot] = register(NekoIngot)
  val catBucket: RegistryObject[CatBucket] = register(CatBucket)

  // add block items
  val nekoBlock: RegistryObject[NekoBlock.Item] = register(NekoBlock.Item)
}
