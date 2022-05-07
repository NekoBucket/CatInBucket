package org.nekobucket.catinbucket.mod.registry

import net.minecraftforge.fml.ModList
import net.minecraftforge.forgespi.language.ModFileScanData.AnnotationData
import net.minecraftforge.registries.IForgeRegistryEntry
import org.nekobucket.catinbucket.mod.{ MOD_ID, BaseObject }

import java.lang.annotation.Annotation
import scala.reflect.ClassTag

/**
 * Automatically register items and blocks with annotated classes.
 */
private[catinbucket] abstract class StaticRegister[T <: IForgeRegistryEntry[T] : ClassTag, R <: Annotation](implicit tag: ClassTag[R]) {
  this: Registry[T] =>

  private def loader = Thread.currentThread.getContextClassLoader

  private def getAnnotatedClassPairs: List[(Class[_ <: T], Class[R])] =
    ModList.get.getModFileById(MOD_ID).getFile.getScanResult.getAnnotations.toArray.toList
      .map(_.asInstanceOf[AnnotationData])
      .filter(data => data.annotationType.getClassName == tag.runtimeClass.getName)
      .map(data => (
        Class.forName(data.clazz.getClassName, true, loader).asInstanceOf[Class[T]],
        tag.runtimeClass.asInstanceOf[Class[R]]
      ))

  /**
   * Get pairs for matched annotation
    */
  protected lazy val pairs: List[(Class[_ <: T], Class[_ <: Annotation])] = getAnnotatedClassPairs

  protected def getCompanionObject(cls: Class[_ <: T]): BaseObject[T] = {
    val clazz = Class.forName(cls.getName + '$', true, loader)
    clazz.getField("MODULE$").get(clazz).asInstanceOf[BaseObject[T]]
  }

  /**
   * Scan and register classes annotated by `R`
   */
  private[catinbucket] def init(): Unit = pairs.foreach {
    case (cls, _) => addEntry(getCompanionObject(cls))(ClassTag(cls))
  }
}
