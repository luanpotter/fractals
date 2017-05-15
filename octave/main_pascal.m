function main_pascal()

  function pt = next_triangle(prev_row, row, modulus)
    pt(1) = 1;
    for c = 2:row-1
      pt(c) = mod(prev_row(c - 1) + prev_row(c), modulus);
    end
    pt(row) = 1;
  end

  function picture = generate(size, k)
    picture = zeros(2*size, 2*size);
    values = [];
    prev_row = [];
    for row = 1:size
      rowPx = (2*(row - 1)+1):(2*(row - 1)+2);
      values = next_triangle(prev_row, row, k);
      prev_row = values;
      pad = size - columns(values);
      picture(rowPx,1:pad) = 1;
      for i=1:columns(values)
        pos = pad + 2*(i - 1) + 1;
        picture(rowPx,pos:pos+1) = !values(i);
      endfor
      picture(rowPx,end-pad:end) = 1;
    endfor
  endfunction

  k = 2 # prime
  expectedDim = 1 + log((k + 1)/2)/log(k)

  for st = 2:12
    picture = generate(2^st, k);
    d(st - 1) = dim(picture);
    imwrite(picture, sprintf('results/pascal-%d.png', st));
  endfor
  d
endfunction

