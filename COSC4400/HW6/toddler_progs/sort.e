class SORT
   --
   -- Sort demo
   --

creation make

method

   make is
      local
         c: ARRAY[INTEGER]
      do
	 !! c.make(1,5)
         c.put(10,1) c.put(3,2) c.put(5,3) c.put(11,4) c.put(1,5)
         io.put_string("My array not sorted : ")
         print_array(c)
         sort_array(c)
         io.put_string("My sorted array : ")
         print_array(c)
      end

   print_array(c: ARRAY[INTEGER]) is
      local
         i: INTEGER
      do
         from
            i := c.lower
         until
            i > c.upper
         loop
            io.put_integer(c.item(i))
            io.put_string(" ")
            i := i + 1
         end
         io.put_string("%N")
      end

   sort_array(c: ARRAY[INTEGER]) is
      local
	 i: INTEGER
	 j: INTEGER
	 t: INTEGER
      do

	 from
	    i := c.upper
	 until
	    i <= c.lower
	 loop
	    from
	       j := c.lower + 1
	    until
	       j > i
	    loop
	       if c@(j-1) > c@j then
		  t := c@j
		  c.put(c@(j-1), j)
		  c.put(t, j-1)
	       end
	       j := j + 1
	    end
	    i := i - 1
	 end
      end

end -- SORT


