class ONE
--
-- Simple program for Baby Gustave
--
--
creation make

method

   make is
      do
         io.put_integer(15//7)
	 io.put_string("%NConfirmed Working:%N")
         io.put_integer(6-5) -- works
         io.put_integer(6+5) -- works
         io.put_integer(6*5) -- works
         io.put_integer((32)) -- works
         io.put_integer(1 = 1) -- works
         io.put_integer(1 = 0) -- works
         io.put_integer(1 /= 1) -- works
         io.put_integer(1 /= 0) -- works
         io.put_integer(2 >= 1) -- works
         io.put_integer(1 >= 1) -- works
         io.put_integer(0 >= 1) -- works
         io.put_integer(2 <= 1) -- works
         io.put_integer(1 <= 1) -- works
         io.put_integer(0 <= 1) -- works
         io.put_integer(2 > 1) -- works
         io.put_integer(1 > 1) -- works
         io.put_integer(0 > 1) -- works
         io.put_integer(2 < 1) -- works
         io.put_integer(1 < 1) -- works
         io.put_integer(0 < 1) -- works
         io.put_integer(not 1) -- works
         io.put_integer(not 0) -- works
         io.put_integer(1 and 1) -- works
         io.put_integer(1 and 0) -- works
         io.put_integer(0 and 0) -- works
         io.put_integer(1 or 1) -- works
         io.put_integer(1 or 0) -- works
         io.put_integer(0 or 0) -- works
         io.put_integer(true) -- works
         io.put_integer(false) -- works
      end

end -- ONE
