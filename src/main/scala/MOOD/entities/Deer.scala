package MOOD.entities

import scalafx.scene.image.Image

class Deer(spawnX:Double, spawnZ:Double) extends Enemy(size = 30, spawnX, -10, spawnZ, speed = 0.5){
  override val MaxHP : Double = 10000
  override val spriteImage: Image = new Image("sprites/deer.png")
  this.sprite.image = spriteImage

}

