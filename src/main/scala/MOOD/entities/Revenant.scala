package MOOD.entities

import scalafx.scene.image.Image
import java.io.File

class Revenant(spawnX:Double, spawnZ:Double) extends Enemy(size = 20, spawnX, -5, spawnZ, speed = 2){
  override val MaxHP : Double = 100
  override val spriteImage: Image = new Image(new File ("src/main/resources/sprites/Revenant.gif").toURI.toString)
  override val deathImage: Image = new Image(new File ("src/main/resources/sprites/Revenant-Death.gif").toURI.toString)
  this.sprite.image = spriteImage

}

