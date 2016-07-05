import javax.inject.{Inject, Singleton}

import kamon.Kamon
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

/**
  * Created by vdias on 05-07-2016.
  */
@Singleton
class KamonInstrumentation @Inject()(lifecycle: ApplicationLifecycle) {
  Kamon.start()

  lifecycle.addStopHook { () => Future.successful(Kamon.shutdown()) }
}
