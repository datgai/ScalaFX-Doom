package MOOD.utils

import GameLogic.{cameraX, cameraY, cameraZ}
import scalafx.scene.PerspectiveCamera
import scalafx.scene.transform.Translate

object PlayerCamera extends PerspectiveCamera(true){
  nearClip = 0.1
  farClip = 10000.0
  transforms = List(new Translate(cameraX, cameraY, cameraZ))
}
