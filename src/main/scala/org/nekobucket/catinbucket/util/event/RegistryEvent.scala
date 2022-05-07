package org.nekobucket.catinbucket.util.event

import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.fml.event.IModBusEvent

import scala.reflect.ClassTag

case class RegistryEvent[T](name: String)(implicit val classTag: ClassTag[T])
  extends Event with IModBusEvent {
  def registryType: Class[T] = classTag.runtimeClass.asInstanceOf[Class[T]]
}