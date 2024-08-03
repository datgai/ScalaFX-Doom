package MOOD.utils

import GameLogic.playerScore
import MOOD.entities.{AmmoPack, Deer, Demon, HealthPack, Player, Revenant}
import scalafx.scene.{AmbientLight, Group, SceneAntialiasing, SubScene}
import scalafx.scene.paint.{Color, PhongMaterial}
import scalafx.scene.shape.Box
import scalafx.Includes._
import scalafx.scene.image.Image

object GameScene {

  private val ambientLight = new AmbientLight(Color.White)

  val levelFloor: Group = createTileGrid(10, 10, 500, "textures/Floor-Moss.png")

  val player = new Player
  val worldGroup = new Group(levelFloor, player,ambientLight)

  val playerCamera: PlayerCamera.type = PlayerCamera

  val gameScene: SubScene = new SubScene(new Group(worldGroup), 1280, 500, true, SceneAntialiasing.Balanced) {
    fill = Color.Red
    content = new Group(worldGroup)
    camera = playerCamera
  }

  EventHandlers.initialize()

  private def createTileGrid(rows: Int, cols: Int, tileSize: Double, imagePath: String): Group = {
    val tileGroup = new Group()
    val image = new Image(imagePath)
    val floorMaterial = new PhongMaterial() {
      diffuseMap = image
    }

    for (row <- 0 until rows) {
      for (col <- 0 until cols) {
        val tile = new Box(tileSize, 1, tileSize) {
          material = floorMaterial
          translateX = col * tileSize - 500
          translateY = 5
          translateZ = row * tileSize - 500
        }
        tileGroup.children.add(tile)
      }
    }
    tileGroup
  }


  def spawnPickups(): Unit = {
    if (GameLogic.playerScore % 500 == 0) {
      val healthPack = new HealthPack(scala.util.Random.nextDouble() * 1000, -4, scala.util.Random.nextDouble() * 1000)
      healthPack.spawn()

    }
    if (playerScore % 500 == 0) {
      val ammoPack = new AmmoPack(scala.util.Random.nextDouble() * 1000, -4, scala.util.Random.nextDouble() * 1000)
      ammoPack.spawn()
    }
  }

  def spawnEnemies(): Unit = {
    if (playerScore % 2500 == 0) {
      val deer = new Deer(scala.util.Random.nextDouble() * -400, scala.util.Random.nextDouble() * 100)
      deer.spawn()
      playerScore += 1
    }
    if (playerScore % 150 == 0) {
      val revenant = new Revenant(scala.util.Random.nextDouble() * -400, scala.util.Random.nextDouble() * 100)
      revenant.spawn()
      playerScore += 1
    }
    if (playerScore % 200 == 0) {
      val demon = new Demon(scala.util.Random.nextDouble() * -400, scala.util.Random.nextDouble() * 100)
      demon.spawn()
      playerScore += 1
    }
  }
}
