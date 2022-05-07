package org.nekobucket.catinbucket.mod

import org.nekobucket.catinbucket.mod.registry.Registry

import java.util.function.Supplier
import scala.reflect.ClassTag

abstract class BaseObject[T] protected(final val ID: String)(implicit ct: ClassTag[T]) extends Supplier[T] {
  override def get: T = this.apply
  def apply: T

  implicit protected def id: String = ID
  implicit def resolve: () => T = () => Registry.get(ct)
}
