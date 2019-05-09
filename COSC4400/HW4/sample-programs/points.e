class POINTS
-- Demo POINT class
creation make
   
method
   
   make is
      local
	 p1: POINT
         p2: POINT
      do
	 !! p1
           p1.set(0,0)
	 !! p2
           p2.set(5,-1)
         io.put_string("Before translation%N")
         p1.show
         p2.show
	 p1.translate(1, 2)
	 p2.translate(3, 4)
         io.put_string("After translation%N")
         p1.show
         p2.show
      end
  
end

class POINT
-- Description of POINTs objects.
   
attribute
   
   x: INTEGER
   y: INTEGER
   
method

   set(a:INTEGER; b:INTEGER) is
      do
	 x := a
	 y := b
      end

   translate(dx: INTEGER; dy: INTEGER) is
	 -- To translate the `Current' POINT.
      do
	 x := x + dx
	 y := y + dy
      end
 
   show is
      do
        io.put_string("<")
        io.put_integer(x)
        io.put_string(", ")
        io.put_integer(y)
        io.put_string(">%N")
      end
end

