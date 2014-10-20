#this is overkill and too much.
#palindromes q.4
def factorize(num):
    factorization = [[1,1]]
    i = 2
    while i <= int(num**0.5):
        if num%i == 0:
            if i != factorization[-1][0]:
                factorization.append([i,1])
            else:
                factorization[-1][1] = factorization[-1][1]+1
            num = num//i
            i = 2
            continue
        i = i + 1

        
    if num != factorization[-1][0]:
        factorization.append([num,1])
    else:
        factorization[-1][1] = factorization[-1][1]+1
    factorization.remove([1,1])
    return factorization


def isPalindrome(num):
    #assuming 6 digit number:
    if num%11 != 0:
        return False
    if (num//100000 == num%10) and ( (num//10000)%10 == (num//10)%10 ) and ( (num//1000)%10 == (num//100)%10):
        return True
    else:
        return False


num = int(input(''))

i = 999*999
while i > 900000:
    if isPalindrome(i):
        fctz = factorize(i//11)
        if fctz[-1][0] < 999:
            print(fctz)
            if fctz[-1][0] * min(11,fctz[0][0]) < 999:
                print('above')

    i = i-1

      