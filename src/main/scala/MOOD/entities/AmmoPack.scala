package MOOD.entities

import scalafx.scene.image.Image

class AmmoPack(spawnX:Double, spawnY:Double, spawnZ:Double) extends Pickup(size = 8, spawnX:Double, spawnY:Double, spawnZ:Double) {
  override val spriteImage = new Image("sprites/Ammo-Base.png")
  sprite.image = spriteImage
}
