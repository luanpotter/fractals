function main()

  details = 64 # Size of the result image, in pixels (always square, details x details) ; beware : should be a power of 2

  xc = double(0) # x,y coord of the center of the mand set (the center of the picture will be this point of the mand set
  yc = double(0)
  frac_size = double(5) # size of the mand we will build; the border of the image will have coord 5 units in the mand set
  # e.g., if (x,y) = (0,0) and frac_size = 1, we are going to print the mand set, from x = -.5 to +.5, y = -.5 to +.5

  imgu = i; # because I used i in the for and got all mixed up -.-

  picture = zeros(details, details); # fill with ones! (we well zero if the dot does not belong)
  for i = 0:(details - 1)
    for j = 0:(details - 1)
      x0 = xc - frac_size / 2 + frac_size * i / details;
      y0 = yc - frac_size / 2 + frac_size * j / details;
      picture(details - j, i + 1) = !mand(x0 + imgu*y0);
    endfor
  endfor

  d = dim(picture)

  imwrite(picture, 'results/fractal.png');
endfunction
