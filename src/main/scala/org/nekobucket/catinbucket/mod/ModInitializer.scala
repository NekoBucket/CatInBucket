package org.nekobucket.catinbucket.mod

import org.nekobucket.catinbucket.mod.registry.{ BlockEntityRegistry, BlockRegistry, ItemRegistry }

/* Register block and items to mod */
trait ModInitializer {
  // scan and register annotated
  BlockRegistry.init()
  ItemRegistry.init()
  LOGGER.info(s"$MOD_NAME: Annotations scanned")

  // register to minecraft
  BlockRegistry.register()
  ItemRegistry.register()
  LOGGER.info(s"$MOD_NAME: Items registered")

  // register block entities, should be after items and blocks
  BlockEntityRegistry.init()
  BlockEntityRegistry.register()
  LOGGER.info(s"$MOD_NAME: Block entities registered")
}