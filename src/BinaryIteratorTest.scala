object BinaryIteratorTest {
	def inputStreamToFile(is: java.io.InputStream, f: java.io.File) : java.io.File = {
		val out = new java.io.FileOutputStream(f)
		println("Writing " + f)
		val in = new BinaryIterator(is)
		try{ in.foreach(out.write(_)) }
		finally { out.close }
		return f
	}
	def main(args : Array[String]) : Unit = {
		inputStreamToFile(new java.io.FileInputStream("test.file"), new java.io.File("test.target"))
	}
}
