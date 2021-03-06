package Pen.hdfsscala

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

import java.io._
import java.net.URI
import scala.util.{Try, Using}

object Pen extends App {

  object HDFSFileService {
    private val conf = new Configuration()
    private val hdfsCoreSitePath = new Path("core-site.xml")
    private val hdfsHDFSSitePath = new Path("hdfs-site.xml")

    conf.addResource(hdfsCoreSitePath)
    conf.addResource(hdfsHDFSSitePath)

    private val fileSystem = FileSystem.get(new URI("hdfs://localhost:9000"), conf)

    def saveFile(filepath: String): Unit = {
      val file = new File(filepath)
      val out = fileSystem.create(new Path(file.getName))
      val in = new BufferedInputStream(new FileInputStream(file))
      var b = new Array[Byte](1024)
      var numBytes = in.read(b)
      while (numBytes > 0) {
        out.write(b, 0, numBytes)
        numBytes = in.read(b)
      }
      val lines: Try[Seq[String]] =
        Using(new BufferedReader(new FileReader("file"))) { reader =>
          Iterator.continually(reader.readLine()).takeWhile(_ != null).toSeq
        }
    }

    def removeFile(filename: String): Boolean = {
      val path = new Path(filename)
      fileSystem.delete(path, true)
    }

    def getFile(filename: String): InputStream = {
      val path = new Path(filename)
      fileSystem.open(path)
    }

    def createFolder(folderPath: String): Unit = {
      val path = new Path(folderPath)
      if (!fileSystem.exists(path)) {
        fileSystem.mkdirs(path)
      }
    }
  }
}
