        PROGRAM VECANG
C       Liam Fruzyna COSC 3410 Assignment 4
C       Calculates the angle between 2 vectors
C       Input is the number of values in the vector
C       Negative if you want the result in degrees, positive for
C       radians, 0 to quit
C       Then the vectors with values separated by spaces

        COMMON /ONE/ J 
        REAL VECA(3)
        REAL VECB(3)

1       READ *, I
        IF(I.EQ.0) STOP
        J = IABSO(I)

        CALL GETVEC(I)

        GOTO 1 
10      END
        
        FUNCTION ANG(V,W)
        COMMON /ONE/ I
        REAL V(I), W(I)
        ANG = 0.0
        ANG = ACOS(DOT(V,W) / (ALEN(V) * ALEN(W)))
        RETURN
        END
        
        FUNCTION DOT(V,W)
        COMMON /ONE/ I
        REAL V(I), W(I)
        DOT = 0.0
        DO 30 J=1,IABSO(I)
        DOT = DOT + V(J)*W(J)
30      CONTINUE
        RETURN
        END
        
        FUNCTION ALEN(V)
        COMMON /ONE/ I
        REAL V(I)
        ALEN = 0.0
        DO 40 J=1,I
        ALEN = ALEN + V(J)**2
40      CONTINUE
        ALEN = SQRT(ALEN)
        RETURN
        END

        FUNCTION IABSO(I)
        IABSO = I
        IF(I.LT.0) IABSO = -I
        RETURN
        END

        SUBROUTINE GETVEC(I)
        COMMON /ONE/ J
        REAL VECA(J)
        REAL VECB(J)

        READ *, VECA
        READ *, VECB

        ANGL = ANG(VECA, VECB)
        IF(I.LT.0) ANGL = ANGL * 57.295779513
        PRINT *, ANGL
        END
