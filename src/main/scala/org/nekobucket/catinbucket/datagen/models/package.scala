package org.nekobucket.catinbucket.datagen

import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile
import org.nekobucket.catinbucket.mod.MOD_ID

package object models {
  val itemGenerated = new UncheckedModelFile("item/generated")

  object Predicates {
    val catType = new ResourceLocation(MOD_ID, "cat_type")
  }
}
