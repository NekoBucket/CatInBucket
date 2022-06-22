package org.nekobucket.catinbucket.block

import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.{ BaseEntityBlock => ForgeEntityBlock }

abstract class BaseEntityBlock protected(properties: Properties) extends ForgeEntityBlock(properties)
