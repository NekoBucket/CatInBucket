package org.nekobucket.nekotools.item

import net.minecraft.block.FlowingFluidBlock
import net.minecraft.client.Minecraft
import net.minecraft.entity.{ EntityType, SpawnReason }
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item.Properties
import net.minecraft.item.{ ItemModelsProperties, ItemStack, Items }
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.math.{ BlockRayTraceResult, RayTraceResult }
import net.minecraft.util.{ ActionResult, Hand }
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.client.model.generators.ModelFile
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import org.nekobucket.nekotools.datagen.models.Predicates.catType
import org.nekobucket.nekotools.datagen.models.{ ItemModels, itemGenerated }
import org.nekobucket.nekotools.mod.EventBus.getEventBus
import org.nekobucket.nekotools.mod.exception.CatTypeNotFoundException
import org.nekobucket.nekotools.mod.registry.ItemRegistry
import org.nekobucket.nekotools.mod.registry.Register
import org.nekobucket.nekotools.mod.{ EventBus, MOD_ID, NekoObject }
import org.nekobucket.nekotools.util.Extensions._
import space.controlnet.lightioc.annotation.Singleton

import scala.language.postfixOps

@Singleton
@Register.AsItem
class CatBucket extends NekoItemBase(new Properties().stacksTo(1)) {

  override def use(world: World, player: PlayerEntity, hand: Hand): ActionResult[ItemStack] =
    player.getItemInHand(hand) |> (itemStack =>
      if (!world.isClientSide) {
        val target = Minecraft.getInstance.hitResult
        if (target.getType == RayTraceResult.Type.BLOCK) {
          val blockPos = target.asInstanceOf[BlockRayTraceResult].getBlockPos
          if (!world.getBlockState(blockPos).getBlock.isInstanceOf[FlowingFluidBlock]) {
            val catEntity = EntityType.CAT.spawn(world.asInstanceOf[ServerWorld], itemStack, player, blockPos.above, SpawnReason.BUCKET, false, false)
            new CompoundNBT().also(catEntity.save)
              .merge(itemStack.getTagElement("cat"))
              .also(catEntity.load)
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

object CatBucket extends NekoObject[CatBucket]("cat_bucket") with CatBucketItemModel {
  EventBus.Forge.register(this)
  EventBus.Mod.addListener(setChangeableModel)

  def setChangeableModel(event: FMLClientSetupEvent): Unit = {
    val func: Runnable = () => ItemModelsProperties.register(ItemRegistry.get[CatBucket], catType, (itemStack, _, _) => {
      if (itemStack.hasTag) itemStack.getTagElement("cat").getFloat("CatType")
      else 10F
    })
    event.enqueueWork(func)
  }

  @SubscribeEvent
  def onBucketRightClick(event: EntityInteract): Unit = if (!event.getWorld.isClientSide) {
    val itemStack = event.getItemStack
    val target = event.getTarget

    if ( {
      def useBucket: Boolean = itemStack.getItem == Items.BUCKET

      def targetCat: Boolean = target.getType == EntityType.CAT

      useBucket && targetCat
    }) {
      itemStack -= 1
      ItemRegistry.get[CatBucket].toItemStack.also {
        itemStack => {
          val nbt = new CompoundNBT().also(target.saveWithoutId)
          nbt.putString("Owner", event.getPlayer.getName.getString)
          nbt.remove("Sitting")
          nbt.putByte("PersistenceRequired", 1)
          nbt.remove("Attributes")
          nbt.remove("Motion")
          nbt.remove("Pos")
          itemStack.addTagElement("cat", nbt)
        }
      } |> event.getPlayer.addItem
      target.remove()
    }
  }
}

trait CatBucketItemModel {
  this: CatBucket.type =>

  private sealed abstract class CatType(val model: String)
  private object CatType {
    object Tabby extends CatType(s"${ ID }_tabby")
    object Tuxedo extends CatType(s"${ ID }_black")
    object Red extends CatType(s"${ ID }_red")
    object Siamese extends CatType(s"${ ID }_siamese")
    object BritishShorthair extends CatType(s"${ ID }_british_shorthair")
    object Calico extends CatType(s"${ ID }_calico")
    object Persian extends CatType(s"${ ID }_persian")
    object Ragdoll extends CatType(s"${ ID }_ragdoll")
    object White extends CatType(s"${ ID }_white")
    object Jellie extends CatType(s"${ ID }_jellie")
    object Black extends CatType(s"${ ID }_all_black")

    val get: Float => CatType = {
      case 0F => Tabby
      case 1F => Tuxedo
      case 2F => Red
      case 3F => Siamese
      case 4F => BritishShorthair
      case 5F => Calico
      case 6F => Persian
      case 7F => Ragdoll
      case 8F => White
      case 9F => Jellie
      case 10F => Black
      case _ => throw CatTypeNotFoundException()
    }
  }

  private def getPath(id: String) = s"$MOD_ID:item/$id"
  private def getModelFile(id: String): ModelFile = id |> getPath |> (new UncheckedModelFile(_))

  (0 to 10).map(_.toFloat)
    .foreach { nbtValue =>
      val model = CatType.get(nbtValue).model

      ItemModels += (_.getBuilder(ID)
        .parent(itemGenerated)
        .also(_.`override`().predicate(catType, nbtValue).model(getModelFile(model)).end()))

      ItemModels += (_.getBuilder(model)
        .parent(itemGenerated)
        .texture("layer0", getPath(model)))
    }
}