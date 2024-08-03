package MOOD.entities

import MOOD.utils.Spawnable
import scalafx.scene.image.Image
import scalafx.scene.transform.Translate

abstract class Pickup(size:Double = 8, spawnX:Double, spawnY:Double, spawnZ:Double) extends Entity(w=size, h = size, d = size, spawnX, spawnY, spawnZ) with Spawnable {
  private var bobPhase: Double = 0
  override val spriteImage = new Image("sprites/Heal-Base.png")
  sprite.image = spriteImage

  override def update(targetX:Double,targetZ:Double): Unit = {
    super.update(targetX, targetZ)
    bobPhase += 0.05
    this.Y += Math.sin(bobPhase) * 0.05
    collisionBox.transforms = List(new Translate(this.X - size/2, this.Y - size/2, this.Z + size/4))
    sprite.transforms = List(new Translate(this.X - size/2, this.Y - size/2, this.Z + size/4))
  }
}
