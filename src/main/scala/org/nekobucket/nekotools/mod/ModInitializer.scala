package org.nekobucket.nekotools.mod

import org.nekobucket.nekotools.mod.registry.{ BlockRegistry, ItemRegistry }

/* Register block and items to mod */
trait ModInitializer {
  // scan and register annotated
  ItemRegistry.init()
  BlockRegistry.init()
  // register to minecraft
  ItemRegistry.register()
  BlockRegistry.register()
}