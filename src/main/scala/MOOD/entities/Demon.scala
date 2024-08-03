package MOOD.entities

import scalafx.scene.image.Image
import java.io.File

class Demon(spawnX:Double, spawnZ:Double) extends Enemy(size = 30, spawnX, -10, spawnZ, speed = 1){
  override val MaxHP : Double = 300
  override val spriteImage: Image = new Image(new File ("src/main/resources/sprites/Demon.gif").toURI.toString)
  override val deathImage: Image = new Image(new File ("src/main/resources/sprites/Demon-Death.gif").toURI.toString)
  this.sprite.image = spriteImage

}

