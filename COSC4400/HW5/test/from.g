class ONE
--
-- Simple program for Baby Gustave
--
--
creation make

attribute
	i: INTEGER
method

   make is
      do
	io.put_integer(9)
        from
		i := 1
	until
		i > 10
	loop
		io.put_integer(i)
		i := i + 1
	end
      end

end -- ONE
