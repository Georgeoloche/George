package Adapter

import Model.User
import Model.post
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramcloneapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_account_settings.*

class PostAdapter
    (private val mContect: Context,
     private  val mPost: List<post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>()

{
    private var firebaseUser:FirebaseUser? = null

    inner class ViewHolder(@NonNull itemView:View) : RecyclerView.ViewHolder(itemView)
    {
        var profileImage : CircleImageView
        var postImage: ImageView
        var likeButton : ImageView
        var commentButton : ImageView
        var saveButton : ImageView
        var userName: TextView
        var likes: TextView
        var publisher: TextView
        var description: TextView
        var comments: TextView

        init {
            profileImage =itemView.findViewById(R.id.user_profile_image_post)
            postImage =itemView.findViewById(R.id.post_image_home)
            likeButton =itemView.findViewById(R.id.user_profile_image_post)
            commentButton =itemView.findViewById(R.id.user_profile_image_post)
            saveButton =itemView.findViewById(R.id.user_profile_image_post)
            userName =itemView.findViewById(R.id.user_profile_image_post)
            likes =itemView.findViewById(R.id.user_profile_image_post)
            publisher =itemView.findViewById(R.id.user_profile_image_post)
            description=itemView.findViewById(R.id.user_profile_image_post)
            comments =itemView.findViewById(R.id.user_profile_image_post)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
     val view = LayoutInflater.from(mContect).inflate(R.layout.post_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        firebaseUser = FirebaseAuth.getInstance().currentUser

        val post = mPost[position]

        Picasso.get().load(post.getpostimage()).into(holder.postImage)

        publisherInfo(holder.profileImage , holder.userName , holder.publisher, post.getpublisher())
    }

    private fun publisherInfo(profileImage: CircleImageView, userName: TextView, publisher: TextView, publisherID: String)
    {
      val usersRef = FirebaseDatabase.getInstance().reference.child("users").child(publisherID)
        usersRef.addValueEventListener(object: ValueEventListener
        {

            override fun onDataChange(snapshot: DataSnapshot)
            {
              if (snapshot.exists())
              {
                  val user = snapshot.getValue<User>(User::class.java)

                  Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile).into(profileImage)
                  userName.text = user!!.getusername()
                 publisher.text = user!!.getfullname()

              }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        } )
    }

    override fun getItemCount(): Int {
     return mPost.size
    }
}