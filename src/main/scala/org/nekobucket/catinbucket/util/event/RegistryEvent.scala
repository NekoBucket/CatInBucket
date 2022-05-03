package org.nekobucket.catinbucket.util.event

import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.fml.event.IModBusEvent

class RegistryEvent(val registryType: Class[_], val name: String) extends Event with IModBusEvent