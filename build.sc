import mill._ ,scalalib._

object downloader extends ScalaModule {
def scalaVersion = "2.13.8"


  // use `::` for scala deps, `:` for java deps
  override def ivyDeps = Agg(
    ivy"org.jsoup:jsoup:1.11.2",
    ivy"com.google.apis:google-api-services-youtube:v3-rev174-1.22.0"
  )

  object test extends Tests {
    override def ivyDeps = Agg(
      ivy"org.scalactic::scalactic:3.1.1",
      ivy"org.scalatest::scalatest:3.1.1"
    )
   
  }
}
// mill mill.scalalib.GenIdea/idea

