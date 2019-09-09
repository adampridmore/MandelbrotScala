package org.fractals.ui

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.BorderPane

object HelloSBT extends JFXApp {
  stage = new PrimaryStage {
    scene = new Scene {
      root = new BorderPane {
        padding = Insets(25)
        center = new Label("Hello SBT")
        bottom = new Button(text = "OK")
      }
    }
  }
}