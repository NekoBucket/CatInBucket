package org.nekobucket.nekotools.block

import net.minecraft.block.AbstractBlock.Properties
import net.minecraft.block.{ BlockState, SoundType }
import net.minecraft.block.material.Material
import net.minecraft.tileentity.{ TileEntity => ForgeTileEntity }
import net.minecraft.world.IBlockReader
import org.nekobucket.nekotools.mod.NekoObject
import org.nekobucket.nekotools.mod.registry.Register

//@Register.AsBlock
//class CatGenerator extends NekoBlockBase(Properties
//  .of(Material.METAL)
//  .strength(1.5F, 6.0F)
//  .sound(SoundType.STONE)
//) {
//  override def hasTileEntity(state: BlockState): Boolean = true
//  override def createTileEntity(state: BlockState, world: IBlockReader): ForgeTileEntity = new CatGenerator.TileEntity
//
//}
//
//object CatGenerator extends NekoObject[CatGenerator] {
//  override val ID: String = "cat_generator"
//
//  class TileEntity extends ForgeTileEntity(TileEntityTypeRegistry) {}
//
//
//}
