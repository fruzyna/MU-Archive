class PRIMES

-- Print the prime numbers less than 100.
--
-- written by mike slattery - feb, 2004

creation
  make

attribute
	a: INTEGER
	d: INTEGER
	flag: BOOLEAN
	limit: INTEGER
	n: INTEGER
	
method
  make is
    do
        from
	  n := 2
	until
	  n > 100
	loop
		flag := false
		from
		  d := 2
		  limit := n//2
		until
		  d > limit or flag
		loop
			a := n//d
			if n = a*d then flag := true end
			d := d + 1
		end
		if NOT flag then
			io.put_integer(n)
			io.put_string("%N")
		end
	  n := n + 1
	end
  end
end -- PRIMES
