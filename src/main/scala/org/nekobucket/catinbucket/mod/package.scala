package org.nekobucket.catinbucket

import net.minecraft.resources.ResourceLocation
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import org.apache.logging.log4j.LogManager

import scala.language.implicitConversions

package object mod {
  final val MOD_ID = "catinbucket"
  final val MOD_NAME = "Cat in Bucket"
  final val LOGGER = LogManager.getLogger(MOD_ID)

  object EventBus {
    // bus enum elements
    final val Mod = EventBusSubscriber.Bus.MOD
    final val Forge = EventBusSubscriber.Bus.FORGE

    // bus objects
    implicit def getEventBus(enumElement: EventBusSubscriber.Bus): IEventBus = enumElement.bus.get
  }

  def getResourceLocation(id: String): ResourceLocation = new ResourceLocation(MOD_ID, id)
}


