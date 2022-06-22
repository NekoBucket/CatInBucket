package org.nekobucket.catinbucket.mod

import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraftforge.fml.event.lifecycle._
import org.nekobucket.catinbucket.util.EventBus
import org.nekobucket.catinbucket.util.EventBus.getEventBus
import org.nekobucket.catinbucket.util.event.RegistryEvent

/* Provides a service as described. */
trait ModLoadingLogger {
  EventBus.Mod.addListener(setup)
  EventBus.Mod.addListener(onFinished)
  EventBus.Mod.addListener(onRegisterItem)

  protected def setup(event: FMLCommonSetupEvent): Unit = {
    LOGGER.info(s"$MOD_NAME: Start initialization")
  }

  protected def onFinished(event: FMLLoadCompleteEvent): Unit = {
    LOGGER.info(s"$MOD_NAME: loading finished")
  }

  // custom event
  protected def onRegisterItem(event: RegistryEvent[_]): Unit = {
    val tStr = event.registryType match {
      case t if classOf[Item].isAssignableFrom(t) => "item"
      case t if classOf[Block].isAssignableFrom(t) => "block"
      case t if classOf[BlockEntity].isAssignableFrom(t) => "block entity"
    }
    LOGGER.info(s"Register $tStr: ${event.name}")
  }
}
