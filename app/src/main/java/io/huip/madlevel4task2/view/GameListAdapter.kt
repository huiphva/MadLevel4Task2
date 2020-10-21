package io.huip.madlevel4task2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.huip.madlevel4task2.model.Game
import io.huip.madlevel4task2.model.Move
import io.huip.madlevel4task2.R
import kotlinx.android.synthetic.main.item_game.view.*

class GameListAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameListAdapter.ViewHolder>()  {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            itemView.tvResult.text = game.result.name
            itemView.tvDate.text = game.date.toString()

            val compId: Int = when (game.computerMove) {
                Move.ROCK -> R.drawable.rock
                Move.PAPER -> R.drawable.paper
                Move.SCISSORS -> R.drawable.scissors
            }

            val playerId: Int = when (game.playerMove) {
                Move.ROCK -> R.drawable.rock
                Move.PAPER -> R.drawable.paper
                Move.SCISSORS -> R.drawable.scissors
            }

            itemView.ivComputer.setImageResource(compId)
            itemView.ivPlayer.setImageResource(playerId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return this.games.size
    }

    override fun onBindViewHolder(holder: GameListAdapter.ViewHolder, position: Int) {
        holder.bind(games[position])
    }
}