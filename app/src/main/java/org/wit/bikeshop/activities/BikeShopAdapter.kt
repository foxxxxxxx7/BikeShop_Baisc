import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_bikeshop.view.*
import kotlinx.android.synthetic.main.card_bikeshop.view.*
import kotlinx.android.synthetic.main.card_bikeshop.view.bikeTitle
import kotlinx.android.synthetic.main.card_bikeshop.view.bikeSize
import kotlinx.android.synthetic.main.card_bikeshop.view.bikeStyle
import kotlinx.android.synthetic.main.card_bikeshop.view.bikeGender
import kotlinx.android.synthetic.main.card_bikeshop.view.bikePrice
import kotlinx.android.synthetic.main.card_bikeshop.view.bikeCondition
import kotlinx.android.synthetic.main.card_bikeshop.view.bikeComment

import org.wit.bikeshop.R
import org.wit.bikeshop.helpers.readImageFromPath
import org.wit.bikeshop.models.BikeShopModel


interface BikeListener {
    fun onBikeClick(bike: BikeShopModel)
}


class BikeShopAdapter constructor(private var bikes: List<BikeShopModel>,
                                   private val listener: BikeListener) : RecyclerView.Adapter<BikeShopAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_bikeshop, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val bike = bikes[holder.adapterPosition]
        holder.bind(bike, listener)
    }

    override fun getItemCount(): Int = bikes.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(bike: BikeShopModel,  listener : BikeListener) {
            itemView.bikeTitle.text = bike.title
            itemView.bikeSize.text = bike.size
            itemView.bikeStyle.text = bike.style
            itemView.bikeGender.text = bike.gender
            itemView.bikePrice.text = bike.price
            itemView.bikeCondition.text = bike.condition
            itemView.bikeComment.text = bike.comment
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, bike.image))
            itemView.setOnClickListener { listener.onBikeClick(bike) }
        }
    }
}