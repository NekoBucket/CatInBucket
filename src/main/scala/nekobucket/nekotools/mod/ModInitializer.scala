package nekobucket.nekotools.mod

import nekobucket.nekotools.mod.registry.{ BlockRegistry, ItemRegistry }

trait ModInitializer {
  ItemRegistry.ITEMS.register(EventBus.Mod)
  BlockRegistry.BLOCKS.register(EventBus.Mod)
}
