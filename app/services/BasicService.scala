package services

import scala.concurrent._

class BasicService() {
    def helloFromTheFuture()(implicit ec: ExecutionContext): Future[String] = Future.successful("Hello :D")
}