function main_pascal()

  size = 32

  function pt = pascal_triangle(n)
    pt(1, 1) = 1;
    for r = 2:n
      pt(r, 1) = 1;
      for c = 2:r-1
          pt(r, c) = pt(r-1, c-1) + pt(r-1, c);
      end
      pt(r, r) = 1;
    end
  end

  picture = zeros(2*size, 2*size);
  triangle = pascal_triangle(size);
  for row = 1:size
    rowPx = (2*(row - 1)+1):(2*(row - 1)+2);

    els = triangle(row,1:row);
    values = mod(els, 2);

    pad = size - columns(els);

    picture(rowPx,1:pad) = 1;
    for i=1:columns(els)
      pos = pad + 2*(i - 1) + 1;
      picture(rowPx,pos:pos+1) = !values(i);
    endfor
    picture(rowPx,end-pad:end) = 1;
  endfor

  # d = dim(picture)

  imwrite(picture, 'results/fractal.png');
endfunction
