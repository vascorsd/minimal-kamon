package controllers

import com.softwaremill.macwire._

import services.ServicesModule

trait ControllersModule { self: ServicesModule =>
    lazy val theHomeController = wire[HomeController]
}