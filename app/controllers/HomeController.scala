package controllers

import play.api.i18n.MessagesApi
import play.api.mvc._

import services.BasicService

class HomeController(basicService: BasicService) extends Controller {

    import play.api.libs.concurrent.Execution.Implicits.defaultContext

    def showHomePage() = Action.async {
        basicService.helloFromTheFuture.map { msg =>
            Ok(s"app... ${msg}")
        }
    }

    def showInfo() = Action {
        BadRequest("no info")
    }

}