function main_pascal()

  size = 512
  k = 2 # prime
  expectedDim = 1 + log((k + 1)/2)/log(k)

  function pt = pascal_triangle_mod(n, modulus)
    pt(1, 1) = 1;
    for r = 2:n
      pt(r, 1) = 1;
      for c = 2:r-1
          pt(r, c) = mod(pt(r-1, c-1) + pt(r-1, c), modulus);
      end
      pt(r, r) = 1;
    end
  end

  picture = zeros(2*size, 2*size);
  triangle = pascal_triangle_mod(size, k);
  for row = 1:size
    rowPx = (2*(row - 1)+1):(2*(row - 1)+2);

    values = triangle(row,1:row);

    pad = size - columns(values);

    picture(rowPx,1:pad) = 1;
    for i=1:columns(values)
      pos = pad + 2*(i - 1) + 1;
      picture(rowPx,pos:pos+1) = !values(i);
    endfor
    picture(rowPx,end-pad:end) = 1;
  endfor

  d = dim(picture)

  imwrite(picture, 'results/fractal.png');
endfunction
