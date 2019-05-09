class DIAMOND2

-- Draw a diamond using character graphics

--  written by mike slattery - feb 2004

creation make

attribute
  row: INTEGER
  column: INTEGER
  absr: INTEGER -- holds absolute value of row
  absc: INTEGER -- holds absolute value of column
  size: INTEGER
  half: INTEGER

method
  make is
    do
      -- Set size here.
      -- If we had input, we would just read this in.
      size := 12

      half := size // 2
      from
        row := 0
      until
        row > size
      loop
	if row < half then
	  absr := half - row
	else
	  absr := row - half
	end
        from
	  column := 0
	until
	  column > size
	loop
	  if column < half then
	    absc := half - column
	  else
	    absc := column - half
	  end
	  if (absr+absc) = half then
	    io.put_string("*")
	  else
	    if absr+absc < half//2 then
	      io.put_string("+")
	    else
	      io.put_string(" ")
	    end
	  end
	  column := column + 1
	end
        io.put_string("%N")
	row := row + 1
      end
    end
end -- DIAMOND2
