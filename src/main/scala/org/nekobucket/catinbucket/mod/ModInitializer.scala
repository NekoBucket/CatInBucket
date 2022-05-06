package org.nekobucket.catinbucket.mod

import org.nekobucket.catinbucket.mod.registry.{ BlockRegistry, ItemRegistry }

/* Register block and items to mod */
trait ModInitializer {
  // scan and register annotated
  BlockRegistry.init()
  ItemRegistry.init()
  LOGGER.info(s"$MOD_NAME: Annotation scanned")

  // register to minecraft
  BlockRegistry.register()
  ItemRegistry.register()
  LOGGER.info(s"$MOD_NAME: Items registered")
}