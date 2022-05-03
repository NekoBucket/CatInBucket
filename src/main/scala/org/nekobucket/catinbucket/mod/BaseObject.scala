package org.nekobucket.catinbucket.mod

import java.util.function.Supplier
import scala.reflect.ClassTag

abstract class BaseObject[T : ClassTag](final val ID: String)(implicit ct: ClassTag[T]) extends Supplier[T] {
  override def get: T = this.apply
  def apply: T
}
