import javax.inject.{Inject, Provider, Singleton}

import kamon.Kamon
import kamon.metric.MetricsModule
import kamon.play.di.{Kamon, KamonAPI}
import kamon.trace.TracerModule
import play.api.Environment
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future


//@Singleton
//class KamonInstrumentation @Inject()(lifecycle: ApplicationLifecycle) extends {
//}

trait KamonApi2 {
  def register(); Unit
  def metrics(): MetricsModule
  def tracer(): TracerModule
}

@Singleton
class KamonApiProvider @Inject() (lifecycle: ApplicationLifecycle) extends Provider[KamonApi2] {
  lazy val get: KamonApi2 = {
    new KamonApi2 {
      override def register(): Unit = {
        Kamon.start()

        lifecycle.addStopHook { () ⇒
          Future.successful(Kamon.shutdown())
        }
      }
      override def tracer(): TracerModule = Kamon.tracer
      override def metrics(): MetricsModule = Kamon.metrics
    }
  }
}

trait KamonComponentsMy {
  def applicationLifecycle: ApplicationLifecycle
//  def environment: Environment

  lazy val kamonApi: KamonApi2 = new KamonApiProvider(applicationLifecycle).get

//  lazy val kamonApi: KamonInstrumentation = new KamonInstrumentation(applicationLifecycle).get
}

//trait KamonComponents2 {
//  def applicationLifecycle: ApplicationLifecycle
//  def environment: Environment
//
//  lazy val kamonApi: Kamon = new KamonAPI(applicationLifecycle, environment)
//}