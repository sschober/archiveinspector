class ByteResource(is: java.io.InputStream) {
	def transferToFile(f: java.io.File) : java.io.File = {
		val out = new java.io.FileOutputStream(f)
		//println("Writing " + f)
		val in = new BinaryIterator(is)
		try{ in.foreach(out.write(_)) }
		finally { out.close }
		return f
	}
}