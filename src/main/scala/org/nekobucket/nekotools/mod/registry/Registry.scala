package org.nekobucket.nekotools.mod.registry

import org.nekobucket.nekotools.mod.{ EventBus, NekoObject }
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.{ DeferredRegister, IForgeRegistryEntry }

import scala.reflect.ClassTag


abstract class Registry[T <: IForgeRegistryEntry[T]] {
  val ENTRIES: DeferredRegister[T]

  protected def register[R <: T](name: String, supplier: NekoObject[R]): RegistryObject[R] = ENTRIES.register(name, supplier)
  protected def register[R <: T](nekoObj: NekoObject[R]): RegistryObject[R] = register(nekoObj.ID, nekoObj)
  def register(): Unit = ENTRIES.register(EventBus.Mod.bus.get)

  /* Caution: Only works after registration */
  def get[V <: T : ClassTag](implicit ct: ClassTag[V]): V =
    getClass.getDeclaredFields.map(f => (f, f.getType, f.getGenericType))
      .filter {
        case (_, t, g) =>
          def matchClass: Boolean = t == classOf[RegistryObject[_]]
          def matchGenericType: Boolean = g.getTypeName.contains(ct.runtimeClass.getName)
          matchClass && matchGenericType
      }.map(_._1)
      .map { field =>
        field.setAccessible(true)
        field.get(this)
      }.head.asInstanceOf[RegistryObject[V]].get
}
