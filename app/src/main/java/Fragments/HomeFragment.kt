package Fragments

import Adapter.PostAdapter
import Model.post
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramcloneapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

  private var postAdapter: PostAdapter? = null
    private var postList : MutableList<post>? =null
    private var followingList: MutableList<post>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_home, container, false)

        var recyclerView: RecyclerView? = null
        recyclerView = view.findViewById(R.id.recycler_view_home)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager


      postList = ArrayList()
      postAdapter = context?.let { PostAdapter(it,postList as ArrayList<post> ) }
        recyclerView.adapter = postAdapter

        checkFollowings()

      return view
    }

    private fun checkFollowings() {
        followingList = ArrayList()


        val followingRef =  FirebaseDatabase.getInstance().reference
                .child("Follow").child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("Following")

        followingRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists()) {
                    (followingList as ArrayList<*>).clear()

                    for (snapshot in p0.children) {
                        snapshot.key?.let { (followingList as ArrayList<String>).add(it) }
                    }
                    retrievePost()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        } )
    }

    private fun retrievePost() {

        val postsRef = FirebaseDatabase.getInstance().reference.child("Posts")

        postsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                postList?.clear()

                for (snapshot in p0.children) {
                    val post = snapshot.getValue(post::class.java)

                    for (id in followingList as ArrayList<String>) {
                        if (post!!.getpublisher().equals(id)) {
                            postList!!.add(post)
                        }

                        postAdapter!!.notifyDataSetChanged()
                    }


                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }


//    private fun retrievePost() {
//
//        val postsRef = FirebaseDatabase.getInstance().reference.child("Posts")
//
//        postsRef.addValueEventListener(object: ValueEventListener
//        {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//         postList?.clear()
//
//                for (snapshot in snapshot.children)
//
//                {
//                    val post = snapshot.getValue(post::class.java)
//
//                    for (id in followingList!!)
//                    {
//                        if (post!!.getpublisher() == id.toString() )
//                        {
//                         postList?.add(post)
//                        }
//                    }
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        } )


}



