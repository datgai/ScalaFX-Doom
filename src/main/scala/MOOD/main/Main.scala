package MOOD.main

import MOOD.utils.{GameHUD, GameMenu, GameScene, OverMenu, Sound}
import MOOD.utils.GameScene.gameScene
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.beans.binding.Bindings
import scalafx.scene.Scene
import scalafx.scene.image.ImageView
import scalafx.scene.layout.{BorderPane, Pane, StackPane}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

object Main extends JFXApp {
  val playerGunOverlay: ImageView =new ImageView("sprites/Pistol-Overlay.png") {
    preserveRatio = true
    smooth = true
    x = 600
    y = 0
    mouseTransparent = true
  }

  private val overlay: Rectangle = new Rectangle {
    fill = Color.Red.opacity(0.0)
    x = 0
    y = 0
    mouseTransparent = true
  }

  private val stackPane: StackPane = new StackPane {
    children = List(
      new Pane {
        children = List(gameScene)
      },
      playerGunOverlay,
      overlay
    )
  }

  private val clipRect: Rectangle = new Rectangle {
    width <== stackPane.width
    height <== stackPane.height
  }

  // Set the clipping rectangle to the stackPane https://stackoverflow.com/questions/22612508/hide-overflow-on-javafx-node
  stackPane.setClip(clipRect)

  val layout: BorderPane = new BorderPane {
    center = GameMenu.menuScreen
  }

  private val game : Scene = new Scene(layout, 1280, 720)

  // Binding https://stackoverflow.com/questions/47413814/scalafx-live-binding
  gameScene.width <== stackPane.width
  gameScene.height <== stackPane.height

  playerGunOverlay.fitHeight <== stackPane.height / 2
  playerGunOverlay.translateY <== (stackPane.height - playerGunOverlay.fitHeight - 100)

  overlay.width <== stackPane.width
  overlay.height <== stackPane.height

//https://stackoverflow.com/questions/32536096/javafx-bindings-not-working-as-expected
  stackPane.prefWidth <== layout.width
  stackPane.prefHeight <== Bindings.createDoubleBinding(
    () => layout.height() - GameHUD.hud.minHeight(),
    layout.heightProperty()
  )

  def showGame(): Unit = {
    Sound.BGM()
    layout.center = Main.stackPane
    layout.setBottom(GameHUD.hud)
  }

  def showOver(): Unit = {
    layout.center = OverMenu.menuScreen
    layout.bottom  = null
  }

  def showMenu(): Unit = {
    Main.layout.center = GameMenu.menuScreen
  }

  stage = new PrimaryStage {
    title = "MOOD"
    height = 770
    width = 1280
    scene = game
  }

  GameScene.gameScene.requestFocus()
}