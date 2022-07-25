package org.nekobucket.catinbucket.item

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity.RemovalReason
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.ProjectileUtil
import net.minecraft.world.entity.{ EntityType, MobSpawnType }
import net.minecraft.world.item.Item.{ Properties, getPlayerPOVHitResult }
import net.minecraft.world.item.{ CreativeModeTab, ItemStack, Items }
import net.minecraft.world.level.{ ClipContext, Level }
import net.minecraft.world.phys.{ BlockHitResult, HitResult }
import net.minecraft.world.{ InteractionHand, InteractionResultHolder }
import net.minecraftforge.client.model.generators.ModelFile
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import org.nekobucket.catinbucket.datagen.models.{ ItemModels, itemGenerated }
import org.nekobucket.catinbucket.mod.Predicates.catType
import org.nekobucket.catinbucket.mod.exception.CatTypeNotFoundException
import org.nekobucket.catinbucket.mod.registry.{ Register, Registry }
import org.nekobucket.catinbucket.mod.{ BaseObject, MOD_ID }
import org.nekobucket.catinbucket.util.EventBus
import org.nekobucket.catinbucket.util.EventBus.getEventBus
import org.nekobucket.catinbucket.util.Extensions._

@Register.AsItem
case class CatBucket protected() extends BaseItem(new Properties().stacksTo(1)) {

  override def use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder[ItemStack] =
    player.getItemInHand(hand) |> (itemStack =>
      if (!world.isClientSide) {
        val target = getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE)
        if (target.getType == HitResult.Type.BLOCK) {
          val blockPos = target.getBlockPos
          if (!world.getBlockState(blockPos).getMaterial.isLiquid) {
            // spawn the cat
            val catEntity = EntityType.CAT.spawn(world.asInstanceOf[ServerLevel], itemStack, player, blockPos.above, MobSpawnType.BUCKET, false, false)
            new CompoundTag().also(catEntity.save)
              .merge(itemStack.getTagElement("cat"))
              .also(catEntity.load)
            // consume the cat bucket
            itemStack -= 1
            // return a bucket to player
            player.getInventory.add(Items.BUCKET.toItemStack)
            InteractionResultHolder.consume(itemStack)
          } else InteractionResultHolder.pass(itemStack)
        } else InteractionResultHolder.pass(itemStack)
      } else InteractionResultHolder.success(itemStack)
      )

  override def fillItemCategory(group: CreativeModeTab, items: NonNullList[ItemStack]): Unit = {
    if (allowdedIn(group))
      (0 to 10).map(_.toFloat)
        .foreach { nbtValue =>
          items.add(this.toItemStack.also {
            _.addTagElement("cat", new CompoundTag().also(_.putFloat("CatType", nbtValue)))
          })
        }
  }
}

object CatBucket extends BaseObject[CatBucket]("cat_bucket") with CatBucketItemModel {
  EventBus.Forge.addListener(onBucketRightClick)
  EventBus.Mod.addListener(setChangeableModel)

  protected def setChangeableModel(event: FMLClientSetupEvent): Unit = {
    val func: Runnable = () => ItemProperties.register(Registry.get[CatBucket], catType, (itemStack, _, _, _) => {
      if (itemStack.hasTag) itemStack.getTagElement("cat").getFloat("CatType")
      else 10F
    })
    event.enqueueWork(func)
  }

  protected def onBucketRightClick(event: EntityInteract): Unit = if (!event.getWorld.isClientSide) {
    val itemStack = event.getItemStack
    val target = event.getTarget

    if ( {
      def useBucket: Boolean = itemStack.getItem == Items.BUCKET

      def targetCat: Boolean = target.getType == EntityType.CAT

      useBucket && targetCat
    }) {
      itemStack -= 1
      Registry.get[CatBucket].toItemStack.also {
        itemStack => {
          val nbt = new CompoundTag().also(target.saveWithoutId)
          nbt.putString("Owner", event.getPlayer.getName.getString)
          nbt.remove("Sitting")
          nbt.putByte("PersistenceRequired", 1)
          nbt.remove("Attributes")
          nbt.remove("Motion")
          nbt.remove("Pos")
          itemStack.addTagElement("cat", nbt)
        }
      } |> event.getPlayer.addItem
      target.remove(RemovalReason.DISCARDED)
    }
  }
}

sealed trait CatBucketItemModel {
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