object ArchiveInspector {
	implicit def enumerationToRichEnumeration[T](enumeration:java.util.Enumeration[T]):RichEnumeration[T] = {
			new RichEnumeration(enumeration)
	}

	def createTempDir() : java.io.File = {
			val tmp = java.io.File.createTempFile("ArchiveInspector", System.nanoTime().toString());
			tmp.delete()
			tmp.mkdir()
			return tmp
	}
	
	def extractJarFile(jf:java.util.jar.JarFile, entry:java.util.jar.JarEntry) : java.util.jar.JarFile ={
			return new java.util.jar.JarFile(
					new ByteResource(jf.getInputStream(entry)).transferToFile(
							new java.io.File(createTempDir(),entry.getName())))
	}

	def inspect(jf : java.util.jar.JarFile, prefix:String) : Unit ={
			// this for loop is made possible by the 
			// implicit def at the beginning of this file 
			for(val e <- jf.entries()){
				
				// skip directory entries, print others
				if(!e.isDirectory) println(prefix + e)
				
				// if entry is a [ejw]ar-file extract it to some temp 
				// location and inspect it.
				if(		e.getName.endsWith("jar") || 
						e.getName.endsWith("war") || 
						e.getName.endsWith("ear" )){
					inspect(extractJarFile(jf,e),prefix+"\t")
				}
			}
	}

	def main(args : Array[String]) : Unit = {
			if(args.length == 0 ){
				println("No file given.")
				return
			}
			val file = new java.io.File(args(0))
			if(!file.canRead){
				println("Can't read file: " + file)
				return
			}
			inspect(new java.util.jar.JarFile(file), "")
	}
}