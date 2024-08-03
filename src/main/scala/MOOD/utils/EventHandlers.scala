package MOOD.utils

import GameLogic.{cameraRotateX, mousePosX, mousePosY, pressedKeys, updateCameraPosition, worldRotateY}
import MOOD.utils.GameScene.gameScene
import scalafx.scene.input.{KeyEvent, MouseEvent}
import scalafx.Includes._

object EventHandlers {
  def initialize(): Unit = {
    // Handle mouse events
    gameScene.onMousePressed = (me: MouseEvent) => {
      mousePosX = me.sceneX
      mousePosY = me.sceneY
    }

    gameScene.onMouseDragged = (me: MouseEvent) => {
      val dx = mousePosX - me.sceneX
      val dy = mousePosY - me.sceneY
      if (me.primaryButtonDown) {
        cameraRotateX += dy * 0.5
        cameraRotateX = cameraRotateX min 90 max -90 // Cap x
        worldRotateY += dx * 0.2
      }
      mousePosX = me.sceneX
      mousePosY = me.sceneY
      updateCameraPosition()
    }

    gameScene.onKeyPressed = (event: KeyEvent) => {
      pressedKeys += event.code.getName
    }

    gameScene.onKeyReleased = (event: KeyEvent) => {
      pressedKeys -= event.code.getName
    }
  }
}
