function D = dim(frac) # calculates the dimension of the frac with the box count method
  n = log2(columns(frac));
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
  square_size = columns(fractal) / r;
  ci = 0;
  cj = 0;
  while ci < columns(fractal)
   while cj < columns(fractal)
      if countSubSquare(fractal, ci, cj, square_size) == 1
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
