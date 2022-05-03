package org.nekobucket.catinbucket.mod.exception

case class RequestRegistryError(msg: String = "") extends Exception(msg)
