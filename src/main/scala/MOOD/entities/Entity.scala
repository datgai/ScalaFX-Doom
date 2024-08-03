package MOOD.entities

import scalafx.scene.Group
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.paint.{Color, PhongMaterial}
import scalafx.scene.shape.Box
import scalafx.scene.transform.{Rotate, Translate}

import scala.math.{atan2, toDegrees}

abstract class Entity( w:Double = 5,  h:Double = 5,  d:Double = 5, spawnX: Double, spawnY: Double, spawnZ: Double) extends Group {
  protected var X: Double = spawnX
  protected var Y: Double = spawnY
  protected var Z: Double = spawnZ
  protected val collisionMaterial: PhongMaterial = new PhongMaterial {
    diffuseColor = Color.White.opacity(0.5) // Semi-transparent for visualization
  }
  protected val collisionVisibility : Boolean = false
  protected val spriteImage = new Image("sprites/deer.png")

  // Initialize the material and the collision box
  protected val collisionBox: Box = new Box {
    width = w
    height = h
    depth = d
    material = collisionMaterial
    visible = collisionVisibility
    transforms = List(new Translate(X, Y, Z))
  }

  // Initialize the sprite
  protected val sprite: ImageView = new ImageView(spriteImage) {
    fitWidth = w
    fitHeight = h
    preserveRatio = true
    smooth = true
    // Position the sprite at the center of the box
    transforms = List(new Translate(X - w/2, Y - h/2, Z + d/4))
  }

  // Add the box and the sprite to the group
  children.addAll(collisionBox, sprite)

  def update(targetX:Double,targetZ:Double): Unit = {
    // Calculate direction to player
    val dx = targetX - X
    val dz = targetZ - Z

    // Set the rotation to face the player
    val angle = atan2(dx, dz)
    this.transforms = List(new Rotate(toDegrees(angle),X,Y,Z, Rotate.YAxis))

  }
}