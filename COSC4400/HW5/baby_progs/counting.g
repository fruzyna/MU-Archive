class COUNTING 
--  count by twos the hard way
--  written by mike slattery - feb 2004

creation make

attribute

	half: INTEGER
	ones: INTEGER
	tens: INTEGER

method
    make is
      
      do
       from
         tens := 0
       until
	 tens > 2
       loop
	   from
	     half := 0
	   until
	     half > 4
	   loop
	     ones := 2*half
	     io.put_integer(tens*10 + ones)
	     io.put_string("%N")
	     half := half + 1
	   end
	   tens := tens + 1
       end
     end

end -- COUNTING

