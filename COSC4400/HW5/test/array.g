class ONE
--
-- Simple program for Baby Gustave
--
--
creation make

attribute
  fib:ARRAY[INTEGER]
method
  make is
    do
	!!fib.make(0, 5)
	io.put_integer(fib.lower)
	io.put_integer(fib.upper)
	fib.put(1, 0)
	fib.put(2, 1)
	fib.put(3, 2)
	fib.put(4, 3)
	fib.put(5, 4)
	io.put_integer(fib@0)
	io.put_integer(fib@1)
	io.put_integer(fib@2)
	io.put_integer(fib@3)
	io.put_integer(fib@4)
      end

end -- ONE
