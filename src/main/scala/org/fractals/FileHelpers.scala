package org.fractals

import com.sun.javafx.util.Utils.{isMac, isWindows}

object FileHelpers {
  def open(filename: String): Unit = {
    (isWindows, isMac) match {
      case (true, _) => Runtime.getRuntime.exec(Array("cmd.exe", "/c", filename))
      case (_, true) => Runtime.getRuntime.exec(s"open $filename")
      case _ => throw new Exception(s"Unknown OS: ${System.getProperty("os.name")}")
    }
  }
}
