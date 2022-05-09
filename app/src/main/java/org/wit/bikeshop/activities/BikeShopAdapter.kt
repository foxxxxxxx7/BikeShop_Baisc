import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_bike.view.*
import kotlinx.android.synthetic.main.card_bikeshop.view.*
import kotlinx.android.synthetic.main.card_bikeshop.view.bikeTitle
import kotlinx.android.synthetic.main.card_bikeshop.view.bikeGender
import kotlinx.android.synthetic.main.card_bikeshop.view.bikeSize
import kotlinx.android.synthetic.main.card_bikeshop.view.bikeCondition
import kotlinx.android.synthetic.main.card_bikeshop.view.bikeStyle
/*import kotlinx.android.synthetic.main.card_bikeshop.view.bikePrice
import kotlinx.android.synthetic.main.card_bikeshop.view.bikeComment*/

import org.wit.bikeshop.R
import org.wit.bikeshop.helpers.readImageFromPath
import org.wit.bikeshop.models.BikeModel


/* This class is a listener that listens for clicks on a bike */
interface BikeListener {
    fun onBikeClick(bike: BikeModel)
}


/* This is the constructor for the BikeShopAdapter class. It takes in a list of bikes and a listener. */
class BikeShopAdapter constructor(
    private var bikes: List<BikeModel>,
    private val listener: BikeListener
) : RecyclerView.Adapter<BikeShopAdapter.MainHolder>() {

    /**
     * This function is called when the RecyclerView needs a new RecyclerView.ViewHolder of the given
     * type to represent an item
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an
     * adapter position.
     * @param viewType This is the type of view that will be created.
     * @return A MainHolder object.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(R.layout.card_bikeshop, parent, false)
        )
    }

    /**
     * The function takes a MainHolder and an Int as parameters, and returns Unit
     *
     * @param holder MainHolder - this is the view holder that will be used to display the data.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val bike = bikes[holder.adapterPosition]
        holder.bind(bike, listener)
    }

    /**
     * Return the size of the bikes list.
     */
    override fun getItemCount(): Int = bikes.size

    /* The MainHolder class is a subclass of RecyclerView.ViewHolder, and it has a bind method that
    takes a BikeShopModel and a BikeListener as parameters. */
    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(bike: BikeModel, listener: BikeListener) {
            itemView.bikeTitle.text = bike.title
            itemView.bikeGender.text = bike.gender
            itemView.bikeSize.text = bike.size
            itemView.bikeStyle.text = bike.style
            itemView.bikeCondition.text = bike.condition
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, bike.image))
            itemView.setOnClickListener { listener.onBikeClick(bike) }
        }
    }
}