package nekobucket.nekotools.mod

import net.minecraft.block.Blocks
import net.minecraftforge.fml.event.lifecycle.{ FMLClientSetupEvent, FMLCommonSetupEvent, InterModProcessEvent }

import java.util.stream.Collectors

/* Provides a service as described. */
trait ModLoadingLogger {
  EventBus.Mod.addListener(setup)
  EventBus.Mod.addListener(doClientStuff)
  EventBus.Mod.addListener(processIMC)

  private def setup(event: FMLCommonSetupEvent): Unit = {
    LOGGER.info("Hello from preinit")
    LOGGER.info(s"Dirt block >> ${Blocks.DIRT.getRegistryName}")
  }

  private def doClientStuff(event: FMLClientSetupEvent): Unit = {
    LOGGER.info(s"Get game setting ${event.getMinecraftSupplier.get.options}")
  }

  private def processIMC(event: InterModProcessEvent): Unit = {
    LOGGER.info(s"Got IMC ${event.getIMCStream.map(_.getMessageSupplier.get).collect(Collectors.toList)}")
  }
}
