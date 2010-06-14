class BinaryIterator( is:java.io.InputStream) extends Iterator[Array[Byte]]{
	var bytes = new Array[Byte](8092)
	
	def hasNext:Boolean = {
		val n = is.read(bytes)
		if(n == -1) return false
		// trim down the array to the actual count of bytes
		bytes = bytes.slice(0, n)
 		return true
	}
	
	// Braindead impl. as this next method assumes, the user calls hasNext
	// before each call to next. If the users fails to do so, duplicate 
	// stuff is returned.
	def next:Array[Byte]= {
		bytes
	}
}