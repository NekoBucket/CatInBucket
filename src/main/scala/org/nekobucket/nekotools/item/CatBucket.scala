package org.nekobucket.nekotools.item

import net.minecraft.block.FlowingFluidBlock
import net.minecraft.client.Minecraft
import net.minecraft.entity.{ EntityType, SpawnReason }
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item.Properties
import net.minecraft.item.{ ItemStack, Items }
import net.minecraft.util.math.{ BlockRayTraceResult, RayTraceResult }
import net.minecraft.util.{ ActionResult, Hand }
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.nekobucket.nekotools.mod.EventBus.getEventBus
import org.nekobucket.nekotools.mod.registry.ItemRegistry
import org.nekobucket.nekotools.mod.{ EventBus, NekoObject }
import org.nekobucket.nekotools.util.Extensions._

import scala.language.postfixOps

class CatBucket extends NekoItem(new Properties().stacksTo(1)) {

  override def use(world: World, player: PlayerEntity, hand: Hand): ActionResult[ItemStack] =
    player.getItemInHand(hand) |> (itemStack =>
      if (!world.isClientSide) {
          val target = Minecraft.getInstance.hitResult
          if (target.getType == RayTraceResult.Type.BLOCK) {
            val blockPos = target.asInstanceOf[BlockRayTraceResult].getBlockPos
            if (!world.getBlockState(blockPos).getBlock.isInstanceOf[FlowingFluidBlock]) {
              EntityType.CAT.spawn(world.asInstanceOf[ServerWorld], itemStack, player, blockPos.above, SpawnReason.BUCKET, false, false)
              itemStack -= 1
              ActionResult.consume(itemStack)
            } else {
              ActionResult.pass(itemStack)
            }
          } else {
            ActionResult.pass(itemStack)
          }
        }
      else ActionResult.success(itemStack)
    )
}

object CatBucket extends NekoObject[CatBucket] {
  override val ID: String = "cat_bucket"

  EventBus.Forge.register(this)

  @SubscribeEvent
  def onBucketRightClick(event: EntityInteract): Unit = if (!event.getWorld.isClientSide) {
    val itemStack = event.getItemStack
    val target = event.getTarget

    if ({
      def useBucket: Boolean = itemStack.getItem == Items.BUCKET
      def targetCat: Boolean = target.getType == EntityType.CAT
      useBucket && targetCat
    }) {
      itemStack -= 1
      target.remove()
      ItemRegistry.get[CatBucket].toItemStack |> {
        itemStack => event.getPlayer.addItem(itemStack)
      }
    }
  }
}
