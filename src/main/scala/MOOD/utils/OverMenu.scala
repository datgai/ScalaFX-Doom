package MOOD.utils

import MOOD.main.Main
import scalafx.Includes._
import scalafx.geometry.Pos
import scalafx.scene.control.Label
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{Pane, StackPane, VBox}
import scalafx.scene.paint.{Color, CycleMethod, LinearGradient, Stop}
import scalafx.scene.text.{Font, Text}

object OverMenu {
  //https://www.reddit.com/r/JavaFX/comments/ky7uik/comment/gjezqlj/?utm_source=share&utm_medium=web3x&utm_name=web3xcss&utm_term=1&utm_content=share_button
  val overFont: Font = Font.loadFont(getClass.getResourceAsStream("/fonts/DooM.ttf"), 60)
  val textFont: Font = Font.loadFont(getClass.getResourceAsStream("/fonts/DooM.ttf"), 40)

  val gameOver: Text = new Text("X.X") {
    font = overFont
    text = "GAME OVER"
    fill = LinearGradient(0, 1, 0, 0, proportional = true, CycleMethod.NoCycle,List(Stop(0, Color.Silver),Stop(1,Color.White)))
  }

  val back: Label = new Label("Click me!") {
    font = textFont
    text = "BACK TO MENU"
    textFill = LinearGradient(0, 1, 0, 0, proportional = true, CycleMethod.NoCycle,List(Stop(0, Color.FireBrick),Stop(1,Color.OrangeRed)))
    onMouseClicked = (_: MouseEvent) => {
      Main.showMenu()
    }
    style = "-fx-cursor: hand;"
  }

  val menu: VBox = new VBox {
    minHeight = 520
    spacing = 100
    alignment = Pos.Center
    style =
      """
        -fx-padding: 24px;
        -fx-background-color: black;
      """
    children = Seq(gameOver,back)
  }

  val menuScreen: StackPane = new StackPane {
    children = List(
      new Pane {
        children = List(menu)
      },
    )
  }

  menu.prefWidth <== menuScreen.width
  menu.prefHeight <== menuScreen.height

}