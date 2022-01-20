package Model

class post {
    private var postid: String = ""
    private var postimage: String = ""
    private var publisher: String = ""
    private var description: String = ""



     constructor()


    constructor(postid: String, postimage: String, publisher: String, description: String) {
        this.postid = postid
        this.postimage = postimage
        this.publisher = publisher
        this.description = description
    }

    fun getpostid(): String{
        return postid
    }


    fun getpostimage(): String{
        return postimage
    }

    fun getpublisher(): String{
        return publisher
    }

    fun getdescription(): String{
        return description
    }


   fun setpostid(postid: String)
   {
       this.postid = postid
   }

    fun setpostimage(postimage: String) {
        this.postimage = postimage
    }

    fun setpublisher(publisher: String)
    {
        this.publisher = publisher
    }

    fun setdescription(description: String)
    {
        this.description = description
    }
}