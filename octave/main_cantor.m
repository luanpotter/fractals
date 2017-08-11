function main_cantor()
  output_precision(15)
  H = 10

  function nb = next_block_list(block_list)
    nb = [];
    for block = (block_list*3)
      nb = [nb (block - 4) (block + 2)];
    endfor
  endfunction

  function picture = generate(size, it)
    picture = zeros(H, size);
    block_list = [2];
    blocks = 1;
    for i = 1:it
      blocks = blocks * 3;
      blockSize = size/blocks;
      for block = block_list
        picture(1:H, (block - 1)*blockSize:block*blockSize) = 1;
      endfor
      block_list = next_block_list(block_list);
    endfor
  endfunction

  picture = generate(3^6, 5);
  dim1d(picture)
  imwrite(picture, 'results/cantor_2d.png');
endfunction

