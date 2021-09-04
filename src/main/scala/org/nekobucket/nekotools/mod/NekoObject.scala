package org.nekobucket.nekotools.mod

import space.controlnet.lightioc.Container

import java.util.function.Supplier
import scala.reflect.ClassTag

abstract class NekoObject[T](implicit ct: ClassTag[T]) extends Supplier[T] {
  // ID for registering mod objects
  val ID: String
//  var instance: Option[T] = None

  // Get instance for generic type
  //  override def get: T = instance match {
  //    case Some(obj) => obj
  //    case None =>
  //      val obj: T = ct.runtimeClass.newInstance.asInstanceOf[T]
  //      instance = Some(obj)
  //      obj
  //  }
  override def get: T = {
    val obj = Container.resolve[T](ct)
    obj
  }
}
