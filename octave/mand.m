function mand()

  ITER = 200
  details = 128

  function M = mandf(c)
    z = c;
    max = 0;
    lastMax = 0;
    niter = 100*ITER - 1;
    for t = 0:niter
      a = abs(z);
      if (isinf(a))
        M = 0;
	return
      endif
      if (a > max)
        max = a;
	lastMax = t;
      elseif (t - lastMax > ITER)
        M = 1;
	return
      endif
      z = z*z + c;
    endfor
    M = 0;
  endfunction

  xc = double(0)
  yc = double(0)
  size = double(5)

  imgu = i;

  picture = ones(details, details);
  for i = 0:(details - 1)
    for j = 0:(details - 1)
      x0 = xc - size / 2 + size * i / details;
      y0 = yc - size / 2 + size * j / details;
      if mandf(x0 + imgu*y0) == 1
        picture(details - j, i + 1) = 0;
      endif
    endfor
  endfor

  imwrite(picture, 'test.png')
endfunction
