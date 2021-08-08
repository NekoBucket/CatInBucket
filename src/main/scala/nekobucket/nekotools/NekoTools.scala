package nekobucket.nekotools

import nekobucket.nekotools.NekoTools.MOD_ID
import net.minecraft.block.Blocks
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.{ FMLClientSetupEvent, FMLCommonSetupEvent, InterModProcessEvent }
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager

import java.util.stream.Collectors


@Mod(MOD_ID)
object NekoTools {
  final val MOD_ID = "nekotools"
  final val LOGGER = LogManager.getLogger(MOD_ID)

  FMLJavaModLoadingContext.get.getModEventBus.addListener(setup)
  FMLJavaModLoadingContext.get.getModEventBus.addListener(doClientStuff)
  FMLJavaModLoadingContext.get.getModEventBus.addListener(processIMC)

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
