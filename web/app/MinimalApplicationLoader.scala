import javax.inject.{Inject, Singleton}

import services.ServicesModule
import controllers.ControllersModule
import com.softwaremill.macwire._
import controllers.Assets
import kamon.Kamon
import play.api.ApplicationLoader.Context
import play.api._
import play.api.cache.EhCacheComponents
import play.api.db.{DBComponents, HikariCPComponents}
import play.api.i18n.I18nComponents
import play.api.inject.{ApplicationLifecycle, Injector, NewInstanceInjector, SimpleInjector}
import play.api.routing.Router
import play.filters.cors.CORSComponents
import router.Routes

class MinimalApplicationLoader extends ApplicationLoader {
  override def load(context: Context): Application = {
    Logger.configure(context.environment)

    (new BuiltInComponentsFromContext(context) with AppComponents).application
  }
}

trait AppComponents extends BuiltInComponents
                       with I18nComponents
                       with DBComponents
                       with HikariCPComponents
                       with EhCacheComponents
                       with CORSComponents
                       with ServicesModule
                       with ControllersModule
                       with KamonComponents {

  // ---- Play configurations:
  lazy val prefix: String = "/"
  lazy val assets: Assets = wire[Assets]
  lazy val router: Router = wire[Routes]

  // recreate the simple injector so we add the dbApi so the DB object works.
  // add the fallback injector to use the Macwire one so we support looking up
  // our own objects. This exists just to support the still existent global state access
  // from the ServicesRegistry that I was not able to kill totally
  override lazy val injector: Injector = new SimpleInjector(
    new MacwireInjector(NewInstanceInjector, wiredInModule(this))
  ) + router + crypto + httpConfiguration + dbApi + defaultCacheApi + tempFileCreator

  /* Custom Filters */
  override lazy val httpFilters = Seq(corsFilter)

  // ---- our configurations:

  // wanna be use, when refactoring into DBApi we can at same time use this way instead of java enums
  // lazy val defaultDatabase = dbApi.database("default")

  kamonApi // force instrumentation with Kamon to start
}
