function mand()

  ITER = 200 # Number of consecutive iterations that are necessary to determine that a constant growth means explosion
  details = 128 # Size of the result image, in pixels (always square, details x details) ; beware : should be a power of 2

  function M = mandf(c) # returns whether the complex c belongs to the mand set
    z = c; # current point
    max = 0; # current max z
    lastMax = 0; # previous max z
    niter = 100*ITER - 1; # total, number of iterations, if no growth is detected in this range, assume limited
    for t = 0:niter
      a = abs(z);
      if (isinf(a)) # exploded! for sure
        M = 0;
	return
      endif
      if (a > max)
        max = a;
	lastMax = t;
      elseif (t - lastMax > ITER) # it's growing non stop for ITER iterations, must explode
        M = 1;
	return
      endif
      z = z*z + c; # next in the sequence
    endfor
    M = 0; # didn't explode in niter iterations
  endfunction

  function D = dim(frac) # calculates the dimension of the frac with the box count method
    n = log2(details);
    xs = zeros(n);
    ys = zeros(n);
    for i=1:n
      r = pow2(i - 1);
      squares = countSquares(frac, r);
      xs(i) = log(r);
      ys(i) = log(squares);
    endfor
    mmq = (ys\xs)
    D = mmq(1);
  endfunction

  function total = countSquares(fractal, r)
    total = 0;
    square_size = details / r;
    ci = 0;
    cj = 0;
    while ci < details
     while cj < details
        if countSubSquare(picture, ci, cj, square_size) == 1
          total += 1;
        endif
        ci += square_size;
        cj += square_size;
      endwhile
    endwhile
  endfunction

  function B = countSubSquare(fractal, i, j, s)
    for dx = 1:(s - 1)
      for dy = 1:(s - 1)
        if fractal(i + dx, j + dy) == 1
          B = 1;
          return
        endif
      endfor
    endfor
    B = 0;
  endfunction

  xc = double(0) # x,y coord of the center of the mand set (the center of the picture will be this point of the mand set
  yc = double(0)
  frac_size = double(5) # size of the mand we will build; the border of the image will have coord 5 units in the mand set
  # e.g., if (x,y) = (0,0) and frac_size = 1, we are going to print the mand set, from x = -.5 to +.5, y = -.5 to +.5

  imgu = i; # because I used i in the for and got all mixed up -.-

  picture = ones(details, details); # fill with ones! (we well zero if the dot does not belong)
  for i = 0:(details - 1)
    for j = 0:(details - 1)
      x0 = xc - frac_size / 2 + frac_size * i / details;
      y0 = yc - frac_size / 2 + frac_size * j / details;
      if mandf(x0 + imgu*y0) == 1
        picture(details - j, i + 1) = 0;
      endif
    endfor
  endfor

  d = dim(picture)

  imwrite(picture, 'fractal.png');
endfunction
