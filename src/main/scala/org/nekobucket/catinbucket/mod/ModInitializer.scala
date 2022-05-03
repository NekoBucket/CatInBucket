package org.nekobucket.catinbucket.mod

import org.nekobucket.catinbucket.mod.registry.{ BlockRegistry, ItemRegistry }

/* Register block and items to mod */
trait ModInitializer {
  // scan and register annotated
  BlockRegistry.init()
  ItemRegistry.init()
  LOGGER.info("CatInBucket: Annotation scanned")

  // register to minecraft
  BlockRegistry.register()
  ItemRegistry.register()
  LOGGER.info("CatInBucket: Items registered")
}