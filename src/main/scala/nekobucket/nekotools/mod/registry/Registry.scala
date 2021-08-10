package nekobucket.nekotools.mod.registry

import nekobucket.nekotools.mod.{ EventBus, NekoObject }
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.{ DeferredRegister, IForgeRegistryEntry }

import scala.reflect.ClassTag


abstract class Registry[T <: IForgeRegistryEntry[T]] {
  val ENTRIES: DeferredRegister[T]
  var mapping: Map[Class[_ <: T], () => T] = Map()

  protected def register[R <: T: ClassTag](name: String, supplier: NekoObject[R])(implicit ct: ClassTag[R]): RegistryObject[R] = {
    val registered: RegistryObject[R] = ENTRIES.register(name, supplier)
    mapping = mapping + (ct.runtimeClass.asInstanceOf[Class[R]] -> (() => registered.get))
    registered
  }

  protected def register[R <: T: ClassTag](nekoObj: NekoObject[R]): RegistryObject[R] = register(nekoObj.ID, nekoObj)
  def register(): Unit = ENTRIES.register(EventBus.Mod)

  /* Caution: Only work after registration */
  def get[V <: T : ClassTag](implicit ct: ClassTag[V]): V =
    mapping(ct.runtimeClass.asInstanceOf[Class[V]])
      .apply
      .asInstanceOf[V]
}
