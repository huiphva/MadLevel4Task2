package io.huip.madlevel4task2.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.huip.madlevel4task2.database.GameRepository
import io.huip.madlevel4task2.R
import kotlinx.android.synthetic.main.fragment_game_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameListFragment : Fragment() {
    private var gameListAdapter =
        GameListAdapter(games)
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true) // DO NOT TOUCH
        return inflater.inflate(R.layout.fragment_game_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())

        rvGames.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvGames.adapter = gameListAdapter
        rvGames.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        getGamesFromDatabase()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.btnDelete).isVisible = true
    }

    /**
     * Fires the deleteGames() function when menu item is pressed.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btnDelete -> {
                deleteGames()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Retrives games from the database and populates the rv.
     */
    private fun getGamesFromDatabase() {
        mainScope.launch {
            val gameList = withContext(Dispatchers.IO) {
                gameRepository.getGames()
            }

            games.clear()
            games.addAll(gameList)
            gameListAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Empties the database
     */
    private fun deleteGames() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteGames()
            }
            getGamesFromDatabase()
        }
    }

}