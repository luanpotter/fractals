function M = mand(c) # returns whether the complex c belongs to the mand set
  ITER = 300; # Number of consecutive iterations that are necessary to determine that a constant growth means explosion

  z = c; # current point
  for t = 0:ITER
    if abs(z) > 2
      M = 0;
      return
    endif
    z = z*z + c; # next in the sequence
  endfor
  M = 1; # didn't explode in ITER iterations
endfunction
