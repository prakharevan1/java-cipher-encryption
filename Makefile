run: Cipher.class
	java Cipher

Cipher.class: Cipher.java
	javac Cipher.java

clean:
	/bin/rm Cipher.class
