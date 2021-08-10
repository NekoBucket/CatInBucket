package nekobucket.nekotools

import net.minecraft.util.ResourceLocation
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import org.apache.logging.log4j.LogManager

package object mod {
  final val MOD_ID = "nekotools"
  final val MOD_NAME = "Neko Tools"
  final val LOGGER = LogManager.getLogger(MOD_ID)

  object EventBus {
    /* bus enum elements */
    final val MOD = EventBusSubscriber.Bus.MOD
    final val FORGE = EventBusSubscriber.Bus.FORGE

    /* bus objects */
    val Mod: IEventBus = MOD.bus.get
    val Forge: IEventBus = FORGE.bus.get
  }

  def getResourceLocation(id: String): ResourceLocation = new ResourceLocation(MOD_ID, id)
}


