package MOOD.entities

import MOOD.utils.GameLogic.enemyList
import MOOD.utils.GameScene.{levelFloor, worldGroup}
import scalafx.scene.Group
import scalafx.scene.paint.{Color, PhongMaterial}
import scalafx.scene.shape.Box
import scalafx.scene.transform.Translate

class Projectile(size:Double = 0.5, spawnX: Double, spawnY: Double, spawnZ: Double, directionX: Double, directionZ: Double, speed : Double = 0.5) extends Group{
  protected var X: Double = spawnX
  protected var Y: Double = spawnY
  protected var Z: Double = spawnZ
  protected val collisionMaterial: PhongMaterial = new PhongMaterial {
    diffuseColor = Color.Orange.opacity(0.8)
  }

  val lifespan: Double = 3.0
  val creationTime: Long = System.nanoTime()

  protected val collisionBox: Box = new Box {
    width = size
    height = size
    depth = size
    material = collisionMaterial
//    visible = collisionVisibility
    transforms = List(new Translate(X, Y, Z))
  }

  children.addAll(collisionBox)

  def checkProjectileCollision(): Unit = {
    for (enemy <- enemyList) {
      if (collisionBox.getBoundsInParent.intersects(enemy.getBoundsInParent)) {
        worldGroup.children.remove(this)
      }
    }
    if (collisionBox.getBoundsInParent.intersects(levelFloor.getBoundsInParent)){
      worldGroup.children.remove(this)
    }
  }

  def update(currentTime: Long): Boolean = {
    X += directionX * speed
    Z += directionZ * speed

    collisionBox.transforms = List(new Translate(X, Y, Z))

    val elapsedTime = (currentTime - creationTime) / 1e9
    val alive = elapsedTime < lifespan
    if (!alive) {
      worldGroup.children.remove(this)
    }

    checkProjectileCollision()

    alive
  }

}
