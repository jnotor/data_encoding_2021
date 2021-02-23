def minimax():
    G = min(6, 5)
    H = min(2, 6)
    C = max(G, H)
    I = min(4, 7)
    J = min(2, 2)
    D = max(I, J)
    A = min(C, D)

    K = min(5, 6)
    L = min(1, 5)
    E = max(K, L)
    M = min(3, 9)
    N = min(2, 6)
    F = max(M, N)
    B = min(E, F)

    print(max(A, B))

minimax()