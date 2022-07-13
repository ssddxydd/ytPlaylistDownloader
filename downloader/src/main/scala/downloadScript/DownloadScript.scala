package main.scala.downloadScript

import scala.sys.process._
import com.google.api.services.youtube.{YouTube, YouTubeRequestInitializer}
//import com.google.api.services.youtube.model.Video
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestInitializer
import scala.jdk.CollectionConverters._
//import scala.collection.JavaConversions._

object DownloadScript extends App{

  //access credentials and playlist link
  val API_key = "AIzaSyD94RmN_pdi6R6myC2yfhftWG1GuJMjF4Q"
  val playlistID : String = "PLPTdRKLoJF489HDUt2Jjcat2hsUQk9ULa"

  // init google api access
  val transport = new NetHttpTransport()
  val factory =  new JacksonFactory()
  val httpRequestInit = new HttpRequestInitializer {
    override def initialize(re: HttpRequest ): Unit =  { }
  }

  val service = new YouTube
  .Builder(transport, factory, httpRequestInit)
    .setApplicationName("test")
    .setYouTubeRequestInitializer(new YouTubeRequestInitializer(API_key))
    .build()


  def downloadSong(songLink :String): Int = {
    val URL  = s"https://www.youtube.com/watch?v=$songLink "
    def executeCommand(ytURL : String) :Int ={
      val exitCode : Int =   s"C:\\Program Files\\youtube-dl\\youtube-dl.exe -x --audio-format mp3 $ytURL".!
      exitCode
    }
    //uses youtube_dl to download song with specified ID
    executeCommand(URL)
  }


  def getLinks(playlistID :String): List[String] = {
    //val playlistId : String = "PLPTdRKLoJF489HDUt2Jjcat2hsUQk9ULa"
    val list = service.playlistItems().list("snippet,contentDetails")
    list.setMaxResults(5L)
    list.setPlaylistId(playlistID)
    val videos = list.execute().getItems
    // videos type is java.util.List[com.google.api.services.youtube.model.PlaylistItem]
    //printing youtube id of every video in the list
    val IdList = videos.asScala.map(v => v.getContentDetails.getVideoId  ).toList
    IdList
  }
  getLinks(playlistID).map(downloadSong)
}


