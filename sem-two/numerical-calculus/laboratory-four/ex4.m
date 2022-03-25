  function z = AitkenAlgorithm(x, y, a)
  
    er = 1e-3; 
    [z, I] = sort(abs(x - a));
    x = x(I);
    y = y(I);
    n = length(x);
    ak = zeros(n, n);
    ak(:, 1) = y';
    
    for i = 1 : n
    
        for j = 1 : i - 1
           ak(i, j + 1) = (1 / (x(i) - x(j))) * (ak(j,j) * (x(i) - a) - ak(i, j) * (x(j) - a));
        endfor
        
        if i > 1 && abs(ak(i - 1, i - 1) - ak(i, i)) <= er
              z = ak(i, i);
              disp(i);        
              return
        endif
              
    endfor
       
  endfunction

  
  x = [64 81 100 121 144 169];
  y = [8 9 10 11 12 13];

  a = 115;

  ans = AitkenAlgorithm(x, y , a)

  err = abs(ans-sqrt(a))