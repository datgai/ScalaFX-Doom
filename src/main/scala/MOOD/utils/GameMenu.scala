package MOOD.utils

import GameLogic.{enemyList, pickupList}
import GameScene.worldGroup
import MOOD.main.Main
import scalafx.Includes._
import scalafx.geometry.Pos
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{Pane, StackPane, VBox}
import scalafx.scene.paint.{Color, CycleMethod, LinearGradient, Stop}
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.{Font, Text}

object GameMenu {
  //How to use custom fonts : https://www.reddit.com/r/JavaFX/comments/ky7uik/comment/gjezqlj/?utm_source=share&utm_medium=web3x&utm_name=web3xcss&utm_term=1&utm_content=share_button
  val textFont: Font = Font.loadFont(getClass.getResourceAsStream("/fonts/DooM.ttf"), 40)
  val subtextFont: Font = Font.loadFont(getClass.getResourceAsStream("/fonts/DooM.ttf"), 15)

  val logo: ImageView = new ImageView(new Image("logo/Mood.png")) {
    fitWidth = 500
    fitHeight = 500
    preserveRatio = true
    smooth = true
  }

  // Why use Label : https://stackoverflow.com/questions/24374867/label-and-text-differences-in-javafx
  val start: Label = new Label("Click me!") {
    font = textFont
    text = "START"
    textFill = LinearGradient(0, 1, 0, 0, proportional = true, CycleMethod.NoCycle,List(Stop(0, Color.FireBrick),Stop(1,Color.OrangeRed)))
    onMouseClicked = (_: MouseEvent) => {
      startGame()
    }
    style = "-fx-cursor: hand;"
  }

  val instructions: Text = new Text() {
    font = subtextFont
    text =
      """WASD for movement
Mouse for camera
1,2,3 to switch between weapons""".stripMargin
    fill = LinearGradient(0, 1, 0, 0, proportional = true, CycleMethod.NoCycle,List(Stop(0, Color.Silver),Stop(1,Color.White)))
  }

  val menuOverlay: Rectangle = new Rectangle {
    fill = LinearGradient(0, 1, 0, 0, proportional = true, CycleMethod.NoCycle,List(Stop(-0.5, Color.Red), Stop(0.02, Color.Orange), Stop(0.2,Color.Transparent)))
    x = 0
    y = 0
    mouseTransparent = true
  }

  val menu: VBox = new VBox {
    minHeight = 520
    spacing = 100
    alignment = Pos.TopCenter
    style =
      """
        -fx-padding: 24px;
        -fx-background-color: gray;
        -fx-background-image: url("textures/Floor-Tar.png");
        -fx-background-repeat: repeat;
        -fx-background-size: 256px;
      """
    children = Seq(logo,start,instructions)
  }

  val menuScreen: StackPane = new StackPane {
    children = List(
      new Pane {
        children = List(menu)
      },
      menuOverlay
    )
  }

  menu.prefWidth <== menuScreen.width
  menu.prefHeight <== menuScreen.height
  menuOverlay.width <== menuScreen.width
  menuOverlay.height <== menuScreen.height

  def startGame(): Unit = {
    Main.showGame()
    GameLogic.playerScore = 0
    GameLogic.playerAmmo = 50
    GameLogic.playerHealth = 100
    for (keys <- GameLogic.pressedKeys ) {
      GameLogic.pressedKeys -= keys
    }
    for (enemy <- enemyList) {
      enemyList-=enemy
      worldGroup.children -= enemy
    }
    for (pickup <- pickupList) {
      pickupList-=pickup
      worldGroup.children -= pickup
    }
    GameScene.player.gameOver = false
    GameLogic.cameraX = 0
    GameLogic.cameraZ = 0
    GameScene.gameScene.requestFocus()
  }

}