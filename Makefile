run: Main.class
	java Main

Main.class: Main.java
	javac Main.java

clean:
	/bin/rm Cipher.class Main.class
