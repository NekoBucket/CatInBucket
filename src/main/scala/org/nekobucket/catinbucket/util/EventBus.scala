package org.nekobucket.catinbucket.util

import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

import scala.language.implicitConversions

object EventBus {
  // bus enum elements
  final val Mod = EventBusSubscriber.Bus.MOD
  final val Forge = EventBusSubscriber.Bus.FORGE

  // bus objects
  implicit def getEventBus(enumElement: EventBusSubscriber.Bus): IEventBus = enumElement.bus.get
}
