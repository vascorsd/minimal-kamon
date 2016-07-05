package services

import com.softwaremill.macwire._

trait ServicesModule { self =>
    lazy val theBasicService = wire[BasicService]
}