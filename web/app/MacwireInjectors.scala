import com.softwaremill.macwire.Wired
import play.api.inject.{BindingKey, Injector}

import scala.reflect.ClassTag

// the two references I found on how to wrap the runtime injector around the wiredModule
class SimpleMacwireInjector(wired: Wired) extends Injector {
  def instanceOf[T](clazz: Class[T]) = wired.lookupSingleOrThrow(clazz)

  def instanceOf[T](key: BindingKey[T]) = instanceOf(key.clazz).asInstanceOf[T]

  def instanceOf[T](implicit ct: ClassTag[T]) = instanceOf(ct.runtimeClass).asInstanceOf[T]
}

class MacwireInjector(fallback: Injector, wired: Wired) extends Injector {
  def instanceOf[T](clazz: Class[T]) = wired.lookup(clazz) match {
    case instance :: Nil => instance
    case Nil => fallback.instanceOf(clazz)
    case set => throw new RuntimeException(s"Multiple instances returned for $clazz: $set")
  }

  def instanceOf[T](key: BindingKey[T]) = instanceOf(key.clazz).asInstanceOf[T]

  def instanceOf[T](implicit ct: ClassTag[T]) = instanceOf(ct.runtimeClass).asInstanceOf[T]
}