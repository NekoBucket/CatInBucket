package nekobucket.nekotools

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager

package object mod {
  final val MOD_ID = "nekotools"
  final val MOD_NAME = "Neko Tools"
  final val LOGGER = LogManager.getLogger(MOD_ID)

  object EventBus {
    val Mod: IEventBus = FMLJavaModLoadingContext.get.getModEventBus
    val Forge: IEventBus = MinecraftForge.EVENT_BUS
  }
}


