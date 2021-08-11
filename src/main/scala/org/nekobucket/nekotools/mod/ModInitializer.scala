package org.nekobucket.nekotools.mod

import org.nekobucket.nekotools.mod.registry.{ BlockRegistry, ItemRegistry }

/* Register block and items to mod */
trait ModInitializer {
  ItemRegistry.register()
  BlockRegistry.register()
}