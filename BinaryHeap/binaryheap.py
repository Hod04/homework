from math import floor
from random import randint

class BinaryHeap:
"""
We initialize a list that consists of n elements.
Then we use the bubble down function in order to achieve
the heap property
"""
    def __init__(self, n):
        self.binHeap = [randint(1, 100) for i in range (0, n)]
        self.size = n
        for i in range(self.size//2, -1, -1):
            self.bubble_down(i)

"""
bubble up changes between an element and its parent in case
the first is smaller than the second and so on until the heap
property is fulfilled.
"""
    def bubble_up(self, i):
        while i > 0 and self.binHeap[parent(i)] > self.binHeap[i]:
            self.binHeap[parent(i)], self.binHeap[i] = self.binHeap[i], self.binHeap[parent(i)]
            i = parent(i)

"""
we push the input element to the list and then apply
bubble up until it's bigger than or equal to its parent
"""
    def insert(self, x):
        self.binHeap.append(x)
        self.size+=1
        self.bubble_up(self.size-1)

# help function that prints out all the binary heap's elements. #

    def get(self):
        i = 0
        returno = list()
        while i<self.size:
            returno.append(self.binHeap[i])
            i+=1
        return returno
"""
bubble down switches between an element and its biggest
child in and so on until the heap property is fulfilled.
"""
    def bubble_down(self, i):
        while (left(i)<self.size and self.binHeap[left(i)]<self.binHeap[i]) or (right(i)<self.size and self.binHeap[right(i)]<self.binHeap[i]):
            nextI = left(i)
            if right(i)<self.size and self.binHeap[right(i)]<self.binHeap[left(i)]:
                nextI+=1
            self.binHeap[i], self.binHeap[nextI] = self.binHeap[nextI], self.binHeap[i]
            i = nextI
"""
In case of a valid input index, we switch between the
element that is about to be deleted and the rightest
element in the binary heap. Then we call bubble down
until the heap's property is fulfilled.
"""
    def delete(self, i):
        try:
            self.binHeap[i], self.binHeap[self.size-1] = self.binHeap[self.size-1], self.binHeap[i]
            del self.binHeap[self.size-1]
            self.size-=1
            self.bubble_down(i)
        except IndexError:
            print("could not delete the element: binHeap[" + str(i) + "], since the binary heap has only " + str(self.size) + " elements.")

"""
In case of a valid input and k is less than the old key in i,
we switch on that particular index the key with the input key
and call bubble up until the heap's property is fulfilled.
In case that k is equal to the old key in i, the heap stays unchanged.
Other inputs are illegal.
"""
    def decreaseKey(self, i, k):
        try:
            if self.binHeap[i] == k:
                return
            elif self.binHeap[i] < k:
                print("the pre-condition k is less than or equals to the old key at i is not matched.")
            else:
                self.binHeap[i] = k
                self.bubble_up(i)
        except IndexError:
            print("could not decrease the key of the element: binHeap[" + str(i) + "], since the binary heap has only " + str(self.size) + " elements.")


def parent(i):
    return floor((i+1)/2)-1

def left(i):
    return 2*i+1

def right(i):
    return 2*i+2

# test:
b = BinaryHeap(10)
b.insert(1)
b.insert(10)
b.insert(2)
print(b.get())
b.decreaseKey(4, 0)
print(b.get())
