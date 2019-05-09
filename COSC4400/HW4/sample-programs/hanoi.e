class HANOI
   --
   -- The classic Tower of Hanoi game.
   --
   -- Converted from SmartEiffel tutorial to Gustave
   --    by mike slattery, jan 2004
   --

creation make

attribute

   nb: INTEGER

   tower1: TOWER
   tower2: TOWER
   tower3: TOWER

method

   make is
      do
	 -- we would read nb if we had input
	 nb := 4
	 !!tower1
	 tower1.make_full(nb)
         !!tower2
         tower2.make_empty(nb)
         !!tower3
         tower3.make_empty(nb)
         io.put_string("Situation at the beginning:%N")
         move(nb,tower1,tower2,tower3)
         io.put_string("Situation at the end:%N")
         show_towers
      end

   move(how_many: INTEGER; source: TOWER;
   		intermediate: TOWER; destination: TOWER) is
	-- Move how_many discs from tower source to tower
	-- destination using intermediate to store discs
	-- along the way.  intermediate is assumed to
	-- hold larger discs than the ones being
        -- moved on source.
      require
         how_many = 0 or intermediate.is_empty or 
              intermediate.top_disc > source.disc_from_top(how_many)
      local
         discus: INTEGER
      do
         if (how_many > 0) then
            move(how_many-1,source,destination,intermediate)
            show_towers
            discus := source.remove_discus
            destination.add_discus(discus)
            move(how_many-1,intermediate,source,destination)
         end
      end

  show_towers is
      local
         i: INTEGER
      do
         io.put_string("%N")
         from
            i := nb
         until
            i = 0
         loop
            io.put_string(" ")
            tower1.show_a_discus(i)
            io.put_string(" ")
            tower2.show_a_discus(i)
            io.put_string(" ")
            tower3.show_a_discus(i)
            io.put_string("%N")
            i := i - 1
         end
         from
            i := (((2 * (nb + 1)) + 1) * 3) - 2
         until
            i = 0
         loop
            io.put_string("-")
            i := i - 1
         end
         io.put_string("%N")
      end

end -- HANOI


class TOWER
   --
   -- Store data for one tower in the
   -- Tower of Hanoi game
   --
   -- Converted from SmartEiffel tutorial to Gustave
   --    by mike slattery, jan 2004
   --

attribute

   t:ARRAY[INTEGER]

   top:INTEGER

method

   make_full(n:INTEGER) is
      require
         n >= 1
      local
         i:INTEGER
      do
         !!t.make(1,n)
         from
            i := n
         until
            i = 0
         loop
            t.put(n-i+1,i)
            i := i - 1
         end
         top := n
      ensure
         nb = n
         and top = nb
         and t@top = 1
      end

   make_empty(n:INTEGER) is
      require
         n >= 1
      do
         !!t.make(1,n)
         top := 1
      ensure
         nb = n
         and top = 1
      end

   nb: INTEGER is
      do
         Result := t.upper
      end

   disc_from_top(d: INTEGER): INTEGER is
      -- return the value of the d-th disc when
      -- counting from the top of the tower
      require
         d >= 0
         and d <= top
      do
         if d = 0 then
            Result := 0
         else
            Result := t@(top-d+1)
         end
      end

   top_disc: INTEGER is
      do
         Result := disc_from_top(1)
      end

   is_empty: BOOLEAN is
      do
         Result := (top_disc = 0)
      end

   show_a_discus(d: INTEGER) is
   -- print an image of the disc in slot d of this tower
      require
         1 <= d
         and d <= nb
      local
         size_of_space : INTEGER
         radius_of_disc : INTEGER
         i : INTEGER
      do
         radius_of_disc := t@d
         size_of_space := nb - radius_of_disc
         from
            i := size_of_space
         until
            i = 0
         loop
            io.put_string(" ")
            i := i - 1
         end
         from
            i := radius_of_disc
         until
            i = 0
         loop
            io.put_string("=")
            i := i - 1
         end
         io.put_string("|")
         from
            i := radius_of_disc
         until
            i = 0
         loop
            io.put_string("=")
            i := i - 1
         end
         from
            i := size_of_space
         until
            i = 0
         loop
            io.put_string(" ")
            i := i - 1
         end
      end

   remove_discus: INTEGER is
      do
         Result := top_disc
         t.put(0,top)
         if top > 1 then
            top := top - 1
         end
      ensure
         top >= 1
      end

   add_discus(d: INTEGER) is
      do
         if is_empty then
            t.put(d,top)
         else
           if top_disc > d then
              top := top + 1
              t.put(d,top)
           else
              -- This case should never happen
              io.put_string("ERROR: Attempt to put disc ")
              io.put_integer(d)
              io.put_string(" on top of disc ")
              io.put_integer(t@top)
              io.put_string(".%N")
           end
         end
      ensure
         top <= nb
      end

end -- TOWER


