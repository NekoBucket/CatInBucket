package org.nekobucket.nekotools.mod

import org.nekobucket.nekotools.NekoTools
import org.nekobucket.nekotools.mod.registry.{ BlockRegistry, ItemRegistry }
import space.controlnet.lightioc.Container

/* Register block and items to mod */
trait ModInitializer {
  // init container. due to some unknown reason, the same `Class` is changed to resolve, so force to use StringId
  private val classLoader = NekoTools.getClass.getClassLoader  // magic, don't touch
  Container.init(PACKAGE_NAME, allStringId = true, classLoader = classLoader)
  LOGGER.info("NekoTools: Classes loaded")

  // scan and register annotated
  ItemRegistry.init()
  BlockRegistry.init()
  LOGGER.info("NekoTools: Annotation scanned")

  // register to minecraft
  ItemRegistry.register()
  BlockRegistry.register()
  LOGGER.info("NekoTools: Items registered")
}