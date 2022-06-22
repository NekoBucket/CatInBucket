package org.nekobucket.catinbucket.mod.registry

import com.google.common.base.Supplier
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.{ BlockEntity, BlockEntityType }
import net.minecraftforge.registries.{ DeferredRegister, ForgeRegistries, RegistryObject }
import org.nekobucket.catinbucket.entity.BaseBlockEntityObject
import org.nekobucket.catinbucket.mod.MOD_ID
import org.nekobucket.catinbucket.mod.exception.RequestRegistryException
import org.nekobucket.catinbucket.util.EventBus
import org.nekobucket.catinbucket.util.EventBus.getEventBus
import org.nekobucket.catinbucket.util.Extensions.AnyExt
import org.nekobucket.catinbucket.util.event.RegistryEvent

import scala.collection.mutable
import scala.reflect.ClassTag

object BlockEntityRegistry extends StaticRegister[BlockEntityType[_], Register.AsBlockEntity] with Registry[BlockEntityType[_]] {
  override protected val entries: DeferredRegister[BlockEntityType[_]] = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID)
  private val registries: mutable.Map[String, RegistryObject[BlockEntityType[_]]] = mutable.Map()

  def addEntry[R <: BlockEntity, B <: Block](baseObj: BaseBlockEntityObject[R, B])(implicit tagR: ClassTag[R], tagB: ClassTag[B]): RegistryObject[BlockEntityType[R]] =
    addEntry[R, B](baseObj.ID, baseObj)

  def addEntry[R <: BlockEntity : ClassTag, B <: Block : ClassTag](name: String, supplier: BaseBlockEntityObject[R, B]): RegistryObject[BlockEntityType[R]] =
    entries.register(name, new Supplier[BlockEntityType[R]]() {
      override def get(): BlockEntityType[R] = BlockEntityType.Builder.of(supplier, Registry.get[B]).build(null)
    }) also { _ =>
      EventBus.Mod.post(RegistryEvent[R](name))
    } also { obj =>
      registries += name -> obj.asInstanceOf[RegistryObject[BlockEntityType[_]]]
    }

  def getRegistry[T <: BlockEntity: ClassTag](id: String): RegistryObject[BlockEntityType[T]] = registries.get(id) match {
    case Some(value) => value.asInstanceOf[RegistryObject[BlockEntityType[T]]]
    case None => throw RequestRegistryException(s"Not found $id")
  }

  def get[T <: BlockEntity](id: String): BlockEntityType[T] = {
    val fields = classOf[BlockEntityRegistryObjects].getDeclaredFields

    fields.map(f => (f, f.getName))
      .filter(_._2 == id)
      .map(_._1)
      .map { field =>
        field.setAccessible(true)
        field.get(this)
      }
      .ifElse[BlockEntityType[T]](_.length > 0)({
        _.head.asInstanceOf[BlockEntityType[T]]
      }, {
        _ => throw RequestRegistryException(s"item $id not found in BlockEntityRegistryObjects")
      }) match {
      case null => throw RequestRegistryException(s"item $id not injected")
      case value => value
    }
  }
}
