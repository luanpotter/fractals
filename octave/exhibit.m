function exhibit()
  a = 3; # ok, a number
  printf("%d\n", a(1)(1)(1)) # whaat? I can access it like a matrix?
  a(1) = 7; # and set, too
  printf("%d\n", a(1)(1)(1)) # seems reasonable
  a(2) = 6; # I can expand it?
  printf(":%d:\n", a) # this is weird, it automatically put a for here?
  # a(1)(1) = 10; # but this will error
endfunction
