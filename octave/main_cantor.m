function main_cantor()

  function picture = generate(size, it)
    picture = zeros(1, size);
    blocks = 1;
    for i = 1:it
      blocks = blocks * 3;
      blockSize = size/blocks;
      for pair = 2:2:blocks
        picture(1, ((pair - 1)*blockSize+1):pair*blockSize) = 1;
      endfor
    endfor
  endfunction

  picture = generate(3^6, 10);
  imwrite(picture, 'results/cantor.png');
endfunction

