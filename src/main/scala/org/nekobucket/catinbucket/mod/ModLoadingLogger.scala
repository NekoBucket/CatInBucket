package org.nekobucket.catinbucket.mod

import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.event.lifecycle._
import org.nekobucket.catinbucket.mod.EventBus.getEventBus
import org.nekobucket.catinbucket.util.event.RegistryEvent

import java.util.stream.Collectors

/* Provides a service as described. */
trait ModLoadingLogger {
  EventBus.Mod.register(this)

  @SubscribeEvent
  def setup(event: FMLCommonSetupEvent): Unit = {
    LOGGER.info(s"$MOD_NAME: Start initialization")
  }
  @SubscribeEvent
  def doClientStuff(event: FMLClientSetupEvent): Unit = {
//    LOGGER.info(s"Get game setting ${event.getMinecraftSupplier.get.options}")
  }
  @SubscribeEvent
  def processIMC(event: InterModProcessEvent): Unit = {
//    LOGGER.info(s"Got IMC ${event.getIMCStream.map(_.getMessageSupplier.get).collect(Collectors.toList)}")
  }

  @SubscribeEvent
  def onFinished(event: FMLLoadCompleteEvent): Unit = {
    LOGGER.info(s"$MOD_NAME: loading finished")
  }

  // custom event
  @SubscribeEvent
  def onRegisterItem(event: RegistryEvent): Unit = {
    val tStr = event.registryType match {
      case t if classOf[Item].isAssignableFrom(t) => "item"
      case t if classOf[Block].isAssignableFrom(t) => "block"
    }
    LOGGER.info(s"Register $tStr: ${event.name}")
  }
}
