package org.nekobucket.nekotools.util.event

import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.fml.event.lifecycle.IModBusEvent
import net.minecraftforge.registries.IForgeRegistryEntry

class ItemRegistryEvent(val item: IForgeRegistryEntry[_], val name: String) extends Event with IModBusEvent