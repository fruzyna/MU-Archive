class FIBARRAY

-- Use an array to compute the first 20 Fibonacci numbers.

-- written by mike slattery - feb, 2004

creation make

attribute
  i:INTEGER
  fib:ARRAY[INTEGER]

method
  make is
    do
	-- Initialize the sequence
	!!fib.make(0, 19)
	fib.put(1, 0)
	fib.put(1, 1)

	-- Fill in the rest
	from
	  i := 2
	until
	  i > fib.upper
	loop
	  fib.put(fib.item(i-1) + fib.item(i-2), i)
	  i := i + 1
	end

	-- Print the result
	from
	  i := 0
	until
	  i > fib.upper
	loop
	  io.put_integer(i)
	  io.put_string(", ")
	  io.put_integer(fib @ i)
	  io.put_string("%N")
	  i := i + 1
	end
  end
end
