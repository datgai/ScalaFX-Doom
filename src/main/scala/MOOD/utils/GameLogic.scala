package MOOD.utils

import GameScene.{player, playerCamera, worldGroup}
import Guns.Gun
import MOOD.entities.{Enemy, Pickup, Projectile}
import MOOD.main.Main
import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.scene.image.Image
import scalafx.scene.transform.{Rotate, Translate}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object GameLogic {
  val pressedKeys: mutable.Set[String] = mutable.Set[String]()
  val enemyList: ListBuffer[Enemy] = ListBuffer[Enemy]()
  val pickupList: ListBuffer[Pickup] = ListBuffer[Pickup]()
  val projectileList: ListBuffer[Projectile] = ListBuffer[Projectile]()

  private val timer: AnimationTimer = AnimationTimer { currentTime =>
    handleKeys()
    updateGameState(currentTime)
    updateCameraPosition()
  }

  // Control variables
  var cameraX: Double = 0
  var cameraY: Double = 0
  var cameraZ: Double = -20
  var cameraRotateX: Double = 0
  var worldRotateY: Double = 0
  var mousePosX: Double = 0
  var mousePosY: Double = 0
  var bobPhase: Double = 0
  var isMoving: Boolean = false
  var playerHealth: Int = 100
  var playerAmmo: Int = 50
  var playerScore: Int = 0

  var currentGun : Gun = Guns.PISTOL
  var lastShotTime: Long = 0
  var shootCooldown: Long = 1000

  def getHealth: String = s"$playerHealth%"
  def getAmmo: String = playerAmmo.toString
  def getScore: String = playerScore.toString

  // Game state updates
  private def updateGameState(currentTime: Long): Unit = {
    playerScore += 1
    GameScene.spawnPickups()
    GameScene.spawnEnemies()

    enemyList.foreach(_.update(cameraX, cameraZ))
    pickupList.foreach(_.update(cameraX, cameraZ))

    projectileList --= projectileList.filterNot(_.update(currentTime))
    player.update()
  }


  private def handleKeys(): Unit = {
    val movementSpeed = 2.0
    if (pressedKeys("W")) moveCamera(0, -movementSpeed)
    if (pressedKeys("A")) moveCamera(-movementSpeed, 0)
    if (pressedKeys("S")) moveCamera(0, movementSpeed)
    if (pressedKeys("D")) moveCamera(movementSpeed, 0)
    if (pressedKeys("Space")) shoot()
    if (pressedKeys("1")) switchWeapon(Guns.PISTOL)
    if (pressedKeys("2")) switchWeapon(Guns.SHOTGUN)
    if (pressedKeys("3")) switchWeapon(Guns.CHAINGUN)
    updateCameraPosition()
  }

  private def switchWeapon(gun:Gun): Unit = {
    if (gun == Guns.PISTOL){
      shootCooldown = 1000
      currentGun = Guns.PISTOL
      GameHUD.gunValue.image = new Image("sprites/pistol.png")
      Main.playerGunOverlay.image = new Image ("sprites/Pistol-Overlay.png")
    }
    if (gun == Guns.SHOTGUN){
      shootCooldown = 1000
      currentGun = Guns.SHOTGUN
      GameHUD.gunValue.image = new Image("sprites/shotgun.png")
      Main.playerGunOverlay.image = new Image("sprites/Shotgun-Overlay.png")
    }
    if (gun == Guns.CHAINGUN){
      shootCooldown = 250
      currentGun = Guns.CHAINGUN
      GameHUD.gunValue.image = new Image("sprites/chaingun.png")
      Main.playerGunOverlay.image = new Image("sprites/Chaingun-Overlay.png")
    }
  }

  private def moveCamera(forward: Double, right: Double): Unit = {
    isMoving = true
    val radians = Math.toRadians(worldRotateY)
    val forwardX = Math.cos(radians) * forward
    val forwardZ = Math.sin(radians) * forward
    val rightX = Math.sin(radians) * right
    val rightZ = -Math.cos(radians) * right

    cameraX += forwardX + rightX
    cameraZ += forwardZ + rightZ

    if (isMoving) {
      bobPhase += 0.15
      cameraY = -2 + Math.sin(bobPhase) * 1 // Use sine wave for headbob, inspiration years of experience playing Minecraft
    } else {
      bobPhase = 0
      cameraY = 0
    }
  }

  def updateCameraPosition(): Unit = {
    playerCamera.transforms = List(
      new Translate(cameraX, cameraY, cameraZ),
      new Rotate(cameraRotateX, Rotate.XAxis)
    )
    worldGroup.transforms = List(
      new Rotate(worldRotateY, cameraX, cameraY, cameraZ, Rotate.YAxis)
    )
  }

  private def shoot(): Unit = {
    val currentTime = System.currentTimeMillis()

    if (currentTime - lastShotTime >= shootCooldown && playerAmmo > 0) {

      val radians = Math.toRadians(worldRotateY)
      val spreadAngle = Math.toRadians(1.5)

      var numProjectiles = 1

      if (currentGun == Guns.PISTOL){
        Sound.pistolShoot()
        numProjectiles = 1
      }

      if (currentGun == Guns.CHAINGUN){
        Sound.pistolShoot()
        numProjectiles = 1
      }

      if (currentGun == Guns.SHOTGUN) {
        Sound.shotgunShoot()
        numProjectiles = 3
      }

      lastShotTime = currentTime
      playerAmmo -= numProjectiles

      val projectiles = (0 until numProjectiles).map { i =>
        val angleOffset = spreadAngle * (i - (numProjectiles - 1) / 2.0)
        val directionX = Math.sin(radians - 3.2 + angleOffset)
        val directionZ = Math.cos(radians + angleOffset)
        new Projectile(0.5, cameraX, cameraY, cameraZ, directionX, directionZ)
      }

      projectileList ++= projectiles
      for (projectile <- projectiles) {
        worldGroup.children += projectile
      }
    }
  }
  timer.start()
}
