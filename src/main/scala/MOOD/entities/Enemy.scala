package MOOD.entities

import scalafx.scene.image.Image
import scalafx.scene.transform.Translate
import MOOD.utils.GameLogic.{enemyList, playerScore, projectileList}
import MOOD.utils.GameScene.worldGroup
import MOOD.entities.Projectile
import MOOD.utils.Spawnable

abstract class Enemy(size:Double = 20,spawnX:Double,spawnY:Double,spawnZ:Double,speed: Double = 1) extends Entity(w=size, h = size, d = size, spawnX, spawnY, spawnZ) with Spawnable {
  protected val MaxHP : Double = 100
  protected var HP : Double = MaxHP
  protected val deathImage = new Image("sprites/deer.png")
  protected val deathAnimationTime : Long = 500
  protected var deathTime : Long = 0
  protected var isDead : Boolean = false

  private def checkEnemyCollision(): Unit = {
    for (projectile : Projectile <- projectileList) {
      if (collisionBox.getBoundsInParent.intersects(projectile.getBoundsInParent)) {
        worldGroup.children.remove(projectile)
        projectileList -= projectile
        HP -= 30
        checkAlive()
      }
    }
  }

  private def checkAlive(): Unit = {
    if (HP <= 0 && !isDead) {
      playerScore += 100
      this.sprite.image = deathImage
      deathTime = System.currentTimeMillis()
      this.isDead = true
    }
  }

  def checkDead():Boolean={
    isDead
  }

  override def update(targetX:Double,targetZ:Double): Unit = {
    //deathAnimation
    if (isDead && (System.currentTimeMillis() - deathTime >= deathAnimationTime)){
      enemyList -= this
      worldGroup.children.remove(this)
    }

    super.update(targetX, targetZ)

    checkEnemyCollision()

    // Calculate direction to player
    val dx = targetX - this.X
    val dz = targetZ - this.Z

    val distance = Math.sqrt(dx * dx + dz * dz)
    val normalizedDx = dx / distance
    val normalizedDz = dz / distance

    if (distance > speed) {
      this.X += normalizedDx * speed
      this.Z += normalizedDz * speed
    } else {
      this.X = targetX
      this.Z = targetZ
    }

    collisionBox.transforms = List(new Translate(this.X, this.Y, this.Z))
    sprite.transforms = List(new Translate(this.X - size/2, this.Y - size/2, this.Z + size/4))
  }
}

