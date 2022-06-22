package org.nekobucket.catinbucket.block

import net.minecraft.core.BlockPos
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.animal.Cat
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.{ BlockEntity, BlockEntityTicker, BlockEntityType }
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.properties.{ BlockStateProperties, IntegerProperty }
import net.minecraft.world.level.block.state.{ BlockState, StateDefinition }
import net.minecraft.world.level.block.{ BaseEntityBlock, Block, Blocks, RenderShape }
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.{ AABB, BlockHitResult, Vec3 }
import net.minecraft.world.{ InteractionHand, InteractionResult }
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile
import org.nekobucket.catinbucket.block.Stove.IS_HEATING
import org.nekobucket.catinbucket.datagen.blockstates.{ BlockStates, BlockState => DatagenBlockState }
import org.nekobucket.catinbucket.datagen.models.ItemModels
import org.nekobucket.catinbucket.datagen.recipes.{ Recipe, Recipes }
import org.nekobucket.catinbucket.entity.{ BaseBlockEntity, BaseBlockEntityObject }
import org.nekobucket.catinbucket.item.BaseBlockItem
import org.nekobucket.catinbucket.mod.registry.{ BlockEntityRegistry, Register, Registry }
import org.nekobucket.catinbucket.mod.{ BaseObject, MOD_ID }
import org.nekobucket.catinbucket.util.Extensions.JavaListExt

import scala.util.Random

@Register.AsBlock
case class Stove protected() extends BaseEntityBlock(Properties
  .of(Material.STONE)
  .requiresCorrectToolForDrops
  .strength(3.5F)
  .lightLevel(it => if (it.getValue(IS_HEATING) == 1) 15 else 0 )
) {

  import Stove._

  def lateSetDefaultProperty(): Unit = {
    val defaultValue: Integer = 0
    registerDefaultState(stateDefinition.any.setValue(IS_HEATING, defaultValue))
  }

  override def createBlockStateDefinition(pBuilder: StateDefinition.Builder[Block, BlockState]): Unit = {
    pBuilder.add(IS_HEATING)
  }

  override def getRenderShape(pState: BlockState): RenderShape = RenderShape.MODEL

  override def newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity = Entity(pPos, pState)

  override def use(pState: BlockState, pLevel: Level, pPos: BlockPos, pPlayer: Player, pHand: InteractionHand,
    pHit: BlockHitResult): InteractionResult = {
    if (pHand == InteractionHand.MAIN_HAND) {
      val entity = pLevel.getBlockEntity(pPos).asInstanceOf[Stove.Entity]
      entity.use(pPlayer)
    }
    InteractionResult.SUCCESS
  }

  override def getTicker[T <: BlockEntity](pLevel: Level, pState: BlockState, pBlockEntityType: BlockEntityType[T]
  ): BlockEntityTicker[T] = BaseEntityBlock.createTickerHelper[Entity, T](
    pBlockEntityType,
    BlockEntityRegistry.getRegistry(ID).get,
    if (pLevel.isClientSide) Stove.Entity.clientTick else Stove.Entity.serverTick
  )
}

object Stove extends BaseObject[Stove]("stove") with StoveBlockState {
  private[block] val IS_HEATING: IntegerProperty = IntegerProperty.create("is_heating", 0, 1)

  @Register.AsItem
  case class Item() extends BaseBlockItem(Registry.get[Stove])

  object Item extends BaseObject[Stove.Item](ID) with StoveRecipe with StoveItemModel

  @Register.AsBlockEntity
  case class Entity(pPos: BlockPos, pState: BlockState) extends BaseBlockEntity[Entity](pPos, pState) {
    private var isHeating = false

    private def switchHeat(): Unit = {
      isHeating = !isHeating
      val newValue: Integer = if (isHeating) 1 else 0
      val newState = pState.setValue(IS_HEATING, newValue)
      level.setBlock(pPos, newState ,4)
    }

    def use(player: Player): Unit = {
      if (!level.isClientSide) {
        switchHeat()
        setChanged()
      }
    }

    override def getUpdateTag: CompoundTag = {
      val pTag = super.getUpdateTag
      pTag.putBoolean("isHeating", isHeating)
      pTag
    }

    override def saveAdditional(pTag: CompoundTag): Unit = {
      pTag.putBoolean("isHeating", isHeating)
      super.saveAdditional(pTag)
    }

    override def load(pTag: CompoundTag): Unit = {
      super.load(pTag)
      isHeating = pTag.getBoolean("isHeating")
      if (level != null) {
        val newValue: Integer = if (isHeating) 1 else 0
        val newState = pState.setValue(IS_HEATING, newValue)
        level.setBlock(pPos, newState ,4)
      }
      needSync = true
    }

    var needSync: Boolean = false

    def syncTick(): Unit = {
      if (needSync) {
        sync()
        needSync = false
      }
    }
  }

  object Entity extends BaseBlockEntityObject[Entity, Stove] {

    private final val horizontalRange = 5.0
    private final val verticalRange = 2.0

    // tick: Search the nearby cat, and attract/gather the nearby cat to the warm block
    def clientTick(level: Level, pos: BlockPos, state: BlockState, entity: Entity): Unit = {
    }

    def serverTick(level: Level, pos: BlockPos, state: BlockState, entity: Entity): Unit = {
      entity.sync()
      if (entity.isHeating) {
        val serverLevel = level.asInstanceOf[ServerLevel]
        val blockPosVec = Vec3.atCenterOf(pos)

        val allCatsNearby = serverLevel.getEntitiesOfClass(
          classOf[Cat],
          AABB.ofSize(blockPosVec, horizontalRange * 2, verticalRange * 2, horizontalRange * 2)).toList

        allCatsNearby.foreach(cat => {
          // only 1% possibility in each tick to move
          if (Random.nextDouble() < 0.05) {
            // calculate the probability of cat movement (target to warm block or not)
            val distance = cat.position().distanceTo(blockPosVec)
            val goInsideProb = (2 / horizontalRange) * distance

            val target = if (Random.nextDouble() < goInsideProb) {
              // go inside
              getNearbyRandomPos(blockPosVec.x, blockPosVec.y, blockPosVec.z)
            } else {
              // go random
              new Vec3(
                Random.between(blockPosVec.x - horizontalRange, blockPosVec.x + horizontalRange),
                Random.between(blockPosVec.y - horizontalRange, blockPosVec.y + horizontalRange),
                blockPosVec.z
              )
            }
            cat.getNavigation.moveTo(target.x, target.y, target.z, 1)
          }
        })
      }
    }

    def getNearbyRandomPos(x: Double, y: Double, z: Double): Vec3 = {
      var dx = if (Random.nextDouble() < 0.5) 2.0 else -2.0
      var dy = if (Random.nextDouble() < 0.5) 2.0 else -2.0
      dx += Random.nextDouble() - 0.5
      dy += Random.nextDouble() - 0.5
      new Vec3(x + dx, y + dy, z)
    }
  }
}

sealed trait StoveRecipe {
  this: Stove.Item.type =>

  Recipes += Recipe.of(s"${ ID }_from_crafting_furnace_and_${ CatBlock.ID }") {
    ShapedRecipeBuilder.shaped(Registry.get[Stove.Item], 1)
      .pattern("XXX")
      .pattern("XOX")
      .define('O', Registry.get[CatBlock])
      .define('X', Items.FURNACE)
  }
}

trait StoveItemModel {
  this: Stove.Item.type =>

  ItemModels += (
    _.getBuilder(ID)
      .parent(new UncheckedModelFile(s"$MOD_ID:block/$ID"))
    )
}

sealed trait StoveBlockState {
  this: Stove.type =>
  BlockStates += DatagenBlockState { it =>
    Registry.get[Stove].lateSetDefaultProperty()
    it.getVariantBuilder(Registry.get[Stove])
      .forAllStates(state => {
        state.getValue(IS_HEATING) match {
          case value if value == 1 => ConfiguredModel.builder.modelFile(it.models.getExistingFile(new ResourceLocation(s"$MOD_ID:block/${ id }_on"))).build
          case value if value == 0 => ConfiguredModel.builder.modelFile(it.models.getExistingFile(new ResourceLocation(s"$MOD_ID:block/$id"))).build
          case _ => throw new Exception("Undefined property")
        }
      })
  }
}
