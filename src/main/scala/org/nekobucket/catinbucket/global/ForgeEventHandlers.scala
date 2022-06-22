package org.nekobucket.catinbucket.global

import net.minecraft.network.chat.{ ChatType, TranslatableComponent }
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.player.Player
import net.minecraftforge.event.entity.living.LivingDeathEvent
import org.nekobucket.catinbucket.util.EventBus
import org.nekobucket.catinbucket.util.EventBus.getEventBus


trait ForgeEventHandlers {
  EventBus.Forge.addListener(onPlayerKillCat)

  def onPlayerKillCat(event: LivingDeathEvent): Unit = {
    val killer = event.getSource.getEntity

    if (killer != null && killer.getType == EntityType.PLAYER && event.getEntityLiving.getType == EntityType.CAT) {

      killer.getLevel.getServer.getPlayerList.broadcastMessage(
        new TranslatableComponent("message.catinbucket.cat_kill_message", killer.asInstanceOf[Player].getDisplayName),
        ChatType.SYSTEM,
        killer.getUUID
      )
    }
  }
}
