class TEST_GCD
--
--

creation make

method

   make is
      do
	 io.put_string("gcd(3,4) should be 1 and is ")
            io.put_integer(gcd(3, 4))
	 io.put_string("%N")
	 io.put_string("gcd(4,4) should be 4 and is ")
            io.put_integer(gcd(4, 4))
	 io.put_string("%N")
	 io.put_string("gcd(8,4) should be 4 and is ")
            io.put_integer(gcd(8, 4))
	 io.put_string("%N")
	 io.put_string("gcd(9,8) should be 1 and is ")
            io.put_integer(gcd(9, 8))
	 io.put_string("%N")
	 io.put_string("gcd(9,12) should be 3 and is ")
            io.put_integer(gcd(9, 12))
	 io.put_string("%N")
      end

   gcd(value_1: INTEGER; value_2: INTEGER): INTEGER is
         -- Great Common Divisor of `value_1' and `value_2'.
      require
         value_1 > 0 and value_2 > 0
      local
         value: INTEGER
      do
         from
            Result := value_1
            value := value_2
         until
            Result = value
         loop
            if Result > value then
               Result := Result - value
            else
               value := value - Result
            end
         end
      ensure
         -- The old condition caused an infinite recursion
         -- Result = gcd(value_2, value_1)
		-- Instead, we'll check that the Result evenly
		-- divides each number.
		value_1 = Result*(value_1//Result) and
			value_2 = Result*(value_2//Result)
      end

end -- TEST_GCD
