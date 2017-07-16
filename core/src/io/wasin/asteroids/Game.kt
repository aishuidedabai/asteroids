package io.wasin.asteroids

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import io.wasin.asteroids.handlers.*
import io.wasin.asteroids.states.Mainmenu

class Game : ApplicationAdapter() {

    lateinit var sb: SpriteBatch
        private set
    lateinit var gsm: GameStateManager
        private set
    lateinit var playerSaveFileManager: PlayerSaveFileManager
        private set

    companion object {
        const val TITLE = "Asteroids"
        const val V_WIDTH = 640f
        const val V_HEIGHT = 480f

        var res: Content = Content()
            private set
    }

    override fun create() {

        Gdx.input.inputProcessor = BBInputProcessor()

        sb = SpriteBatch()

        gsm = GameStateManager(this)

        // create player's savefile manager with pre-set of savefile's path
        playerSaveFileManager = PlayerSaveFileManager(Settings.PLAYER_SAVEFILE_RELATIVE_PATH)
        playerSaveFileManager.sync(Settings.TOTAL_HIGH_SCORES_RECORD)

        // load any resource here...
        res.loadSound("sounds/explode.ogg", "explode")
        res.loadSound("sounds/extralife.ogg", "extralife")
        res.loadSound("sounds/largesaucer.ogg", "largesaucer")
        res.loadSound("sounds/pulsehigh.wav", "pulsehigh")
        res.loadSound("sounds/pulselow.wav", "pulselow")
        res.loadSound("sounds/saucershoot.ogg", "saucershoot")
        res.loadSound("sounds/shoot.ogg", "shoot")
        res.loadSound("sounds/smallsaucer.ogg", "smallsaucer")
        res.loadSound("sounds/thruster.ogg", "thruster")

        // sync controllers
        BBInput.syncControllers(Gdx.input.inputProcessor as ControllerListener)

        // go to main menu state
        gsm.setState(Mainmenu(gsm))
    }

    override fun render() {
        Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.framesPerSecond)
        gsm.update(Gdx.graphics.deltaTime)
        gsm.render()
        BBInput.update()
    }

    override fun dispose() {
        sb.dispose()
        res.dispose()
        gsm.dispose()
    }

    override fun resize(width: Int, height: Int) {
        gsm.resize(width, height)
    }
}
