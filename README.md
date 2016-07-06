# Run

 `~/d/minimal-kamon   master ±  sbt aspectj-runner:run -Dhttps.port=9001 -Dhttp.port=9000 -Dconfig.file=conf/application.conf`

# Problems

 When https accepts certificate, app reloads and kamon Kaputs.
 There is no token being reported, it's appearing always as `undefined`.

