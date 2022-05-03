package org.nekobucket.catinbucket.mod.registry

import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.{ DeferredRegister, IForgeRegistryEntry, RegistryObject }
import org.nekobucket.catinbucket.mod.EventBus.getEventBus
import org.nekobucket.catinbucket.mod.exception.RequestRegistryError
import org.nekobucket.catinbucket.mod.{ BaseObject, EventBus }
import org.nekobucket.catinbucket.util.Extensions.AnyExt
import org.nekobucket.catinbucket.util.event.RegistryEvent

import scala.reflect.ClassTag


private[registry] trait Registry[T <: IForgeRegistryEntry[T]] {
  protected val entries: DeferredRegister[T]

  private[registry] def register[R <: T](name: String, supplier: BaseObject[R])(implicit tagR: ClassTag[R]): RegistryObject[R] =
    entries.register(name, supplier).also { _ =>
      EventBus.Mod.post(new RegistryEvent(tagR.runtimeClass, name))
    }
  private[registry] def register[R <: T](baseObj: BaseObject[R])(implicit tagR: ClassTag[R]): RegistryObject[R] = register[R](baseObj.ID, baseObj)(tagR)
  private[catinbucket] def register(): Unit = entries.register(EventBus.Mod)

  /* Caution: Only works after registration */
  private[catinbucket] def get[V <: T](implicit tagV: ClassTag[V]): V = {
    val fields = tagV.runtimeClass match {
      case cls if classOf[Item].isAssignableFrom(cls) => classOf[ItemRegistryObjects].getDeclaredFields
      case cls if classOf[Block].isAssignableFrom(cls) => classOf[BlockRegistryObjects].getDeclaredFields
    }

    fields.map(f => (f, f.getType))
      .filter(_._2 == tagV.runtimeClass)
      .map(_._1)
      .map { field =>
        field.setAccessible(true)
        field.get(this)
      }
      .ifElse[V](_.length > 0)({
        _.head.asInstanceOf[V]
      }, {
        _ => throw RequestRegistryError(s"item ${tagV.runtimeClass.getName} not found in RegistryObjects")
      }) match {
      case null => throw RequestRegistryError(s"item ${tagV.runtimeClass.getName} not injected")
      case value => value
    }
  }
}
