package org.nekobucket.nekotools.mod

import space.controlnet.lightioc.Container

import java.util.function.Supplier
import scala.reflect.ClassTag

abstract class NekoObject[T](final val ID: String)(implicit ct: ClassTag[T]) extends Supplier[T] {
  override def get: T = {
    val obj = Container.resolve[T](ct)
    obj
  }
}
