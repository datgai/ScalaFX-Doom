package MOOD.utils

import javafx.scene.media.{Media, MediaPlayer}
import java.io.File

object Sound {
  private val pistolSound = new Media(new File("src/main/resources/sounds/pistolShoot.mp3").toURI.toString)
  private val pistolSoundPlayer = new MediaPlayer(pistolSound)
  pistolSoundPlayer.setVolume(0.3)

  private val shotgunSound = new Media(new File("src/main/resources/sounds/shotgunShoot.mp3").toURI.toString)
  private val shotgunSoundPlayer = new MediaPlayer(shotgunSound)
  shotgunSoundPlayer.setVolume(0.3)

  private val deathSound = new Media(new File("src/main/resources/sounds/playerHurt.mp3").toURI.toString)
  private val deathSoundPlayer = new MediaPlayer(deathSound)
  deathSoundPlayer.setVolume(0.1)

  private val gameMusic = new Media(new File("src/main/resources/sounds/music.mp3").toURI.toString)
  private val gameMusicPlayer = new MediaPlayer(gameMusic)
  gameMusicPlayer.setVolume(0.1)


  private def playSound(soundPlayer:MediaPlayer): Unit ={
    soundPlayer.stop()
    soundPlayer.play()
  }

  def pistolShoot(): Unit = {
    playSound(pistolSoundPlayer)
  }

  def shotgunShoot(): Unit = {
    playSound(shotgunSoundPlayer)
  }

  def playerDeath(): Unit = {
    playSound(deathSoundPlayer)
  }

  def BGM(): Unit = {
    playSound(gameMusicPlayer)
  }

}
