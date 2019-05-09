class DIAMOND

-- A silly program to draw pictures with integers.
--  Sort of a diamond-shaped pattern of 8's

--  written by mike slattery - feb 2004

creation make

attribute
  left: INTEGER
  right: INTEGER

method
  make is
    do
      -- top half
      from
        right := 11111111
        left := 0
      until
        right = 0
      loop
        left := left//10 + 80000000
        right := right // 10
        io.put_integer(left+right)
        io.put_string("%N")
      end
      -- bottom half
      from
        -- (keep current situation)
      until
        left <= 8
      loop
        left := left // 10
        right := right // 10 + 10000000
        io.put_integer(left+right)
        io.put_string("%N")
      end
    end
end -- DIAMOND
