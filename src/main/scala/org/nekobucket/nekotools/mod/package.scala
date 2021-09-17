package org.nekobucket.nekotools

import net.minecraft.util.ResourceLocation
import net.minecraftforge.eventbus.api.{ Event, IEventBus }
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import org.apache.logging.log4j.LogManager

import scala.language.implicitConversions

package object mod {
  final val MOD_ID = "nekotools"
  final val MOD_NAME = "NekoTools"
  final val PACKAGE_NAME = "org.nekobucket.nekotools"
  final val LOGGER = LogManager.getLogger(MOD_ID)

  object EventBus {
    /* bus enum elements */
    final val Mod = EventBusSubscriber.Bus.MOD
    final val Forge = EventBusSubscriber.Bus.FORGE

    /* bus objects */
    implicit def getEventBus(enumElement: EventBusSubscriber.Bus): IEventBus = enumElement.bus.get
  }

  def getResourceLocation(id: String): ResourceLocation = new ResourceLocation(MOD_ID, id)
}


