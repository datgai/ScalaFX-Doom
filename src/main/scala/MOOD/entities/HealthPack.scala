package MOOD.entities

import scalafx.scene.image.Image

class HealthPack(spawnX:Double, spawnY:Double, spawnZ:Double) extends Pickup(size = 8, spawnX:Double, spawnY:Double, spawnZ:Double) {
  override val spriteImage = new Image("sprites/Heal-Base.png")
  sprite.image = spriteImage
}
