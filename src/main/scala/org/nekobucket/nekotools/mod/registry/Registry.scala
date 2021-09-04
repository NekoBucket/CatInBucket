package org.nekobucket.nekotools.mod.registry

import org.nekobucket.nekotools.mod.{ EventBus, NekoObject }
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.{ DeferredRegister, IForgeRegistryEntry }
import org.nekobucket.nekotools.util.Extensions.AnyExt

import scala.collection.mutable
import scala.reflect.ClassTag


private[registry] trait Registry[T <: IForgeRegistryEntry[T]] {
  protected val entries: DeferredRegister[T]
  private val mappings: mutable.Map[String, AnyRef] = mutable.Map()

  private[registry] def register[R <: T](name: String, supplier: NekoObject[R])(implicit tagR: ClassTag[R]): RegistryObject[R] =
    entries.register(name, supplier).also {
      _ => mappings.addOne(tagR.runtimeClass.getName -> supplier.get)
    }
  private[registry] def register[R <: T](nekoObj: NekoObject[R])(implicit tagR: ClassTag[R]): RegistryObject[R] = register[R](nekoObj.ID, nekoObj)(tagR)
  private[mod] def register(): Unit = entries.register(EventBus.Mod.bus.get)

  /* Caution: Only works after registration */
  private[nekotools] def get[V <: T : ClassTag](implicit tag: ClassTag[V]): V = mappings(tag.runtimeClass.getName).asInstanceOf[V]

//  private def get[V <: T](implicit tagV: ClassTag[V]): Option[V] = {
//    getClass.getDeclaredFields.map(f => (f, f.getType, f.getGenericType))
//      .filter {
//        case (_, t, g) =>
//          def matchClass: Boolean = t == classOf[RegistryObject[_]]
//          def matchGenericType: Boolean = g.getTypeName.contains(tagV.runtimeClass.getName)
//          matchClass && matchGenericType
//      }
//      .map(_._1)
//      .map { field =>
//        field.setAccessible(true)
//        field.get(this)
//      }
//      .ifElse[Option[V]](_.length > 0) ({
//        list => Some(list.head.asInstanceOf[RegistryObject[V]].get)
//      }, {
//        _ => None
//      })
//  }
}
