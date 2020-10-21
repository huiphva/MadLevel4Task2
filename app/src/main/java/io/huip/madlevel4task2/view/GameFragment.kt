package io.huip.madlevel4task2.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import io.huip.madlevel4task2.*
import io.huip.madlevel4task2.database.GameRepository
import io.huip.madlevel4task2.model.Game
import io.huip.madlevel4task2.model.Move
import io.huip.madlevel4task2.model.Result
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val games = arrayListOf<Game>()

class GameFragment : Fragment() {
    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true) // DO NOT TOUCH
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        btn_rock.setOnClickListener {
            playGame(Move.ROCK)
        }

        btn_paper.setOnClickListener {
            playGame(Move.PAPER)
        }

        btn_scissors.setOnClickListener {
            playGame(Move.SCISSORS)
        }
    }

    private fun initViews () {
        gameRepository = GameRepository(requireContext())
        mainScope.launch {
            tvStats.text = getString(R.string.stats, gameRepository.getWins(), gameRepository.getDraws(), gameRepository.getLosses())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.btnGameHistory).isVisible = true
    }


    /**
     * The game element.
     * This function pics a random value from the Move enum and assigns it to the computer.
     * It then matches this result against the move from the player.
     */
    private fun playGame (playerMove: Move) {
        val computerMove = Move.values().random()
        val result: Result

        // Rock > scissors > paper > rock.
        // Note to self: https://kotlinlang.org/docs/reference/control-flow.html
        when (playerMove) {
            Move.ROCK -> {
                result = when (computerMove) {
                    Move.ROCK -> Result.DRAW
                    Move.SCISSORS -> Result.WIN
                    else -> Result.LOSE
                }
            }
            Move.PAPER -> {
                result = when (computerMove) {
                    Move.ROCK -> Result.WIN
                    Move.SCISSORS -> Result.LOSE
                    else -> Result.DRAW
                }
            }
            Move.SCISSORS -> {
                result = when (computerMove) {
                    Move.ROCK -> Result.LOSE
                    Move.SCISSORS -> Result.DRAW
                    else -> Result.WIN
                }
            }
        }
        update(result, playerMove, computerMove)
    }

    /**
     * Updates the game screen and database.
     */
    private fun update (result: Result, playerMove: Move, computerMove: Move) {
        ivPlayer.setImageResource(getImage(playerMove))
        ivComputer.setImageResource(getImage(computerMove))

        when (result) {
            Result.DRAW -> tvResult.text = getString(
                R.string.draw
            )
            Result.LOSE -> tvResult.text = getString(
                R.string.lose
            )
            Result.WIN -> tvResult.text = getString(
                R.string.win
            )
        }

        mainScope.launch {
            tvStats.text = getString(R.string.stats, gameRepository.getWins(), gameRepository.getDraws(), gameRepository.getLosses())

            val game = Game(
                result,
                playerMove,
                computerMove
            )

            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
    }

    /**
     * Matches move with image.
     */
    private fun getImage (selectionPlayer : Move) : Int {
        return when (selectionPlayer) {
            Move.ROCK -> R.drawable.rock
            Move.PAPER -> R.drawable.paper
            Move.SCISSORS -> R.drawable.scissors
        }
    }
}
