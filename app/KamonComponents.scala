import javax.inject.{Inject, Provider, Singleton}

import kamon.Kamon
import kamon.metric.MetricsModule
import kamon.trace.TracerModule
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

trait KamonApi {
  def metrics(): MetricsModule
  def tracer(): TracerModule
}

@Singleton
class KamonApiProvider @Inject() (lifecycle: ApplicationLifecycle) extends Provider[KamonApi] {
  lazy val get: KamonApi = {
    Kamon.start()

    lifecycle.addStopHook { () ⇒
      Future.successful(Kamon.shutdown())
    }

    new KamonApi {
      override def tracer(): TracerModule = Kamon.tracer
      override def metrics(): MetricsModule = Kamon.metrics
    }
  }
}

trait KamonComponents {
  def applicationLifecycle: ApplicationLifecycle

  lazy val kamonApi: KamonApi = new KamonApiProvider(applicationLifecycle).get
}