package MOOD.utils

import scalafx.animation.AnimationTimer
import scalafx.geometry.Pos
import scalafx.scene.Node
import scalafx.scene.image.ImageView
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.{Color, CycleMethod, LinearGradient, Stop}
import scalafx.scene.text.{Font, Text}

object GameHUD {
  // Load Fonts
  private val textFont: Font = Font.loadFont(getClass.getResourceAsStream("/fonts/DooM.ttf"), 20)
  private val valueFont: Font = Font.loadFont(getClass.getResourceAsStream("/fonts/DooM.ttf"), 30)

  // Gradient Fills
  private def gradientFill(startColor: Color, endColor: Color): LinearGradient = {
    LinearGradient(0, 1, 0, 0, proportional = true, CycleMethod.NoCycle, List(Stop(0, startColor), Stop(1, endColor)))
  }

  // Common Styles
  private val hudStyle: String =
    """
      -fx-padding : 12px;
      -fx-background-color: gray;
      -fx-background-image: url("textures/HUD-Base.png");
      -fx-background-repeat: repeat;
      -fx-background-size: 128px;
      -fx-effect: dropshadow(three-pass-box, black, 20, 0.7, 0, 0);
    """

  // Create HUD Text Element
  private def createText(value: String, textFont: Font, textFill: LinearGradient): Text = {
    new Text {
      text = value
      this.font = textFont
      this.fill = textFill
    }
  }

  // Create HUD Box
  private def createHUDBox(value: Node, label: Text): VBox = {
    new VBox {
      alignment = Pos.Center
      style = hudStyle
      children = Seq(value, label)
    }
  }

  // HUD Elements
  private val ammoValue: Text = createText("50", valueFont, gradientFill(Color.FireBrick, Color.OrangeRed))
  private val ammoText: Text = createText("AMMO", textFont, gradientFill(Color.Silver, Color.White))
  private val ammoHUD: VBox = createHUDBox(ammoValue, ammoText)

  private val healthValue: Text = createText("100%", valueFont, gradientFill(Color.FireBrick, Color.OrangeRed))
  private val healthText: Text = createText("HEALTH", textFont, gradientFill(Color.Silver, Color.White))
  private val healthHUD: VBox = createHUDBox(healthValue, healthText)

  private val scoreValue: Text = createText("1", valueFont, gradientFill(Color.FireBrick, Color.OrangeRed))
  private val scoreText: Text = createText("SCORE", textFont, gradientFill(Color.Silver, Color.White))
  private val scoreHUD: VBox = createHUDBox(scoreValue, scoreText)

  val gunValue: ImageView = new ImageView("sprites/pistol.png")
  private val gunText: Text = createText("GUN", textFont, gradientFill(Color.Silver, Color.White))
  private val gunHUD: VBox = createHUDBox(gunValue, gunText)

  val hud: HBox = new HBox {
    minHeight = 200
    spacing = 100
    alignment = Pos.Center
    style =
      """
        -fx-padding: 24px;
        -fx-background-color: gray;
        -fx-background-image: url("textures/HUD-Base.png");
        -fx-background-repeat: repeat;
        -fx-background-size: 256px;
      """
    children = Seq(gunHUD, ammoHUD, healthHUD, scoreHUD)
  }

  val timer: AnimationTimer = AnimationTimer(_ => {
    healthValue.text = GameLogic.getHealth
    ammoValue.text = GameLogic.getAmmo
    scoreValue.text = GameLogic.getScore
  })

  timer.start()
}
