package org.nekobucket.nekotools.mod.registry

import com.google.common.reflect.ClassPath
import net.minecraftforge.registries.IForgeRegistryEntry
import org.nekobucket.nekotools.mod.{ NekoObject, PACKAGE_NAME }

import java.lang.annotation.Annotation
import scala.reflect.ClassTag

/**
 * Automatically register items and blocks with annotated classes.
 */
private[nekotools] abstract class StaticRegister[T <: IForgeRegistryEntry[T], R <: Annotation : ClassTag](implicit tag: ClassTag[R]) {
  this: Registry[T] =>

  private def classPath = ClassPath.from(Thread.currentThread.getContextClassLoader)

  private def appendInnerClasses(cls: Class[_]): List[Class[_]] = cls :: cls.getClasses
    .filter(subclass => subclass.getPackage.getName.startsWith(PACKAGE_NAME) && subclass.getName != cls.getName)
    .flatMap(appendInnerClasses)
    .toList

  private def classes: List[Class[_]] = classPath.getTopLevelClassesRecursive(PACKAGE_NAME)
    .toArray.toList.map(_.asInstanceOf[ClassPath.ClassInfo].load)
    .flatMap(appendInnerClasses)

  private def getAnnotatedClassPairs: List[(Class[T], R)] =
    classes.map(cls => (cls, cls.getAnnotations))
      .map {
        case (cls, annotations) => (cls, annotations.find(_.annotationType.getName == tag.runtimeClass.getName))
      }
      .filter {
        case (_, Some(_)) => true
        case (_, None) => false
      }
      .map {
        case (cls, Some(annotation)) => (cls.asInstanceOf[Class[T]], annotation.asInstanceOf[R])
      }

  // Get pairs for matched annotation
  protected lazy val triplets: List[(Class[_ <: T], Annotation, String)] = getAnnotatedClassPairs
    .map {
      case (cls, annotation) =>
        (cls, annotation, annotation.getClass.getMethod("id").invoke(annotation).asInstanceOf[String])
    }

  protected def getCompanionObject(cls: Class[_ <: T]): NekoObject[T] = {
    val clazz = Class.forName(cls.getName + '$')
    clazz.getField("MODULE$").get(clazz).asInstanceOf[NekoObject[T]]
  }

  /**
   * Scan and register classes annotated by `R`
   */
  private[mod] def init(): Unit = triplets.foreach {
    case (cls, _, Register.NULL) => register(getCompanionObject(cls))(ClassTag(cls))
    case (cls, _, id) => register(id, getCompanionObject(cls))(ClassTag(cls))
  }
}
