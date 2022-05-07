package org.nekobucket.catinbucket

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager

package object mod {
  final val MOD_ID = "catinbucket"
  final val MOD_NAME = "Cat in Bucket"
  final val LOGGER = LogManager.getLogger(MOD_ID)

  def getResourceLocation(id: String): ResourceLocation = new ResourceLocation(MOD_ID, id)
}
