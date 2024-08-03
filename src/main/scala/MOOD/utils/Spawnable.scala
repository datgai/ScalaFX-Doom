package MOOD.utils

import GameLogic.{enemyList, pickupList}
import GameScene.worldGroup
import MOOD.entities.{Enemy, Pickup}

trait Spawnable {
  def spawn(): Unit = {
    this match {
      case pickup: Pickup =>
        pickupList += pickup
        worldGroup.children.add(pickup)
      case enemy: Enemy =>
        enemyList += enemy
        worldGroup.children.add(enemy)
      case _ =>
    }

  }
}
