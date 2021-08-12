package org.nekobucket.nekotools.mod

import java.util.function.Supplier
import scala.reflect.ClassTag

abstract class NekoObject[T: ClassTag] extends Supplier[T] {
  // ID for registering mod objects
  val ID: String
  var instance: Option[T] = None

  // Get instance for generic type
  private def getInstance(implicit ct: ClassTag[T]): T = instance match {
    case Some(obj) => obj
    case None =>
      val obj: T = ct.runtimeClass.newInstance.asInstanceOf[T]
      instance = Some(obj)
      obj
  }
  override def get: T = getInstance

//  def getItemModelBuilder: ExistingFileHelper => ItemModelBuilder
}
