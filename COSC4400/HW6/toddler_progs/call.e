class CALL
--
-- The simplest program involving a method call
-- that I can think of.
--
creation make

method
  make is
    do
      io.put_integer(1)
      two
      io.put_integer(3)
    end

  two is
    do
      io.put_integer(2)
    end
end
