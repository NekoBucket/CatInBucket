package org.nekobucket.nekotools.mod.registry

import org.nekobucket.nekotools.mod.MOD_ID
import net.minecraft.item.Item
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.{ DeferredRegister, ForgeRegistries }

import scala.reflect.ClassTag
import org.nekobucket.nekotools.util.Extensions._
import org.nekobucket.nekotools.item.{ TestItem, TestJavaItem }

private[nekotools] object ItemRegistry extends StaticRegister[Item, Register.AsItem] with Registry[Item] {
  override val entries: DeferredRegister[Item] = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID)

  // add items dynamically
  val testItem: RegistryObject[TestItem] = register(TestItem) // scala item example
  val testJavaItem: RegistryObject[TestJavaItem] = new TestJavaItem.Factory(ClassTag(classOf[TestJavaItem]))
    .let(factory => register[TestJavaItem](factory.ID, factory)) // java item example
}
