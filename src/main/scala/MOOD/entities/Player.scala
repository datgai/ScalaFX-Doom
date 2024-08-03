package MOOD.entities

import MOOD.main.Main
import MOOD.main.Main.stage
import MOOD.utils.{GameLogic, Sound}
import MOOD.utils.GameLogic.{cameraX, cameraY, cameraZ, enemyList, pickupList, playerHealth, playerScore}
import MOOD.utils.GameScene.{player, worldGroup}
import scalafx.Includes._
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.shape.Box
import scalafx.scene.transform.Translate

class Player extends Box{
  width = 25
  height = 25
  depth = 25

  var gameOver : Boolean = true

  private def checkPlayerCollision(): Unit = {
    for (enemy: Enemy <- enemyList) {
      if (player.getBoundsInParent.intersects(enemy.getBoundsInParent)) {
        if (!enemy.checkDead()) {
          GameLogic.playerHealth -= 20
          if (GameLogic.playerHealth < 0) {
            GameLogic.playerHealth = 0
          }
        }
      }
    }
    for (pickup:Pickup <- pickupList) {
      if (player.getBoundsInParent.intersects(pickup.getBoundsInParent)) {
        if(pickup.isInstanceOf[HealthPack]){
          GameLogic.playerHealth += 50
          if (GameLogic.playerHealth > 100) {
            GameLogic.playerHealth = 100
          }
        }
        if (pickup.isInstanceOf[AmmoPack]){
          GameLogic.playerAmmo += 25
          if (GameLogic.playerAmmo > 100) {
            GameLogic.playerAmmo = 100
          }
        }
        pickupList -= pickup
        worldGroup.children -= pickup
      }
    }
  }

  private def checkGameOver(): Unit = {
    if (playerHealth <= 0 & !gameOver){
      Sound.playerDeath()

      val alert = new Alert(AlertType.Information) {
        initOwner(stage)
        title = "Score"
        headerText = "YOU DIED!"
        contentText = "Score : " + playerScore
      }

      alert.show()

      Main.showOver()
      gameOver = true
      for (enemy <- enemyList) {
        enemyList-=enemy
        worldGroup.children -= enemy
      }
      for (pickup <- pickupList) {
        pickupList-=pickup
        worldGroup.children -= pickup
      }
    }
  }

  def update(): Unit = {
    checkGameOver()
    checkPlayerCollision()
    this.transforms = List(new Translate(cameraX, cameraY -25/2, cameraZ))
    }
}
