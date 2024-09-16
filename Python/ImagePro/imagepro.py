# author: Anchita Dash
# CSE30
# PA2 ImagePro
# implementation of resize, crop, blur, and convolve functions

import matplotlib.pyplot as plt
import numpy as np
from PIL import Image

def resize(img, scale):
    height = int(len(img) * scale)       # finds the image height 
    width = int(len(img[0]) * scale)     # finds the image width
    img2 = np.zeros([height, width, 3])  # makes an image array with zeros

    for row in range (height):
        for col in range(width):
            r, c = int(row/scale),int(col/scale)
            img2 [row, col] = img[r, c]  # updates the values in the image array

    return img2


def crop(img, region):
    x1, y1, x2, y2 = region
    height = y2 - y1 
    width = x2 - x1
    img2 = np.zeros([height, width, 3])

    if(x1 >= 0 and y1 >= 0 and  x2 < len(img) and y2 < len(img[0])):
        good_img = True
    else:
        good_img = False
    

    if(good_img == True):
        for row in range (height):
            for col in range(width):
                    r, c = row+y1,col+x1
                    if(r < len(img) and c < len(img[0])):
                        img2[row, col] = img[r, c]
    return img2

def blur(img, factor):
    #print(img[:5,:5])
    height = int(len(img)) 
    width = int(len(img[0]))
    img2 = np.zeros([height, width])
    
    def find_average(img,row,col):
        s = 0
        for i in range(factor):
            for j in range(factor):
                    r,c = (row+i-(factor//2)),(col+j-(factor//2))
                    s += img[r, c]
        new_pixel_value = int(s/factor**2)
        return new_pixel_value
    
    

    p = factor//2
    padded_width = width+2*p
    padded_height = height+2*p
    padded_img = np.zeros((img.shape[0] + p*2, img.shape[1] + p*2))
    padded_img[ p : -p, p : -p ] = img

    #print(padded_img[:7,:7])
   
    for i in range(p,padded_height-p):
        for j in range(p,padded_width-p):
            r,c = i-(p),j-(p)
            pixel_val = find_average(padded_img,i,j)
            img2[r,c] = pixel_val

    #print(img2[:7,:7])
    return img2


def convolve(image, kernel, padding=0, strides=1):

    kernel = np.flipud(np.fliplr(kernel)) # flips the kernel
    height = int(1+(image.shape[0]-kernel.shape[0]+2*padding)/(strides))
    width = int(1+(image.shape[1]-kernel.shape[1]+2*padding)/(strides))
    img2 = np.zeros([height, width])

    for row in range(height):
        for col in range(width):
            img2[row, col] = (kernel * image[row: row + kernel.shape[0],  col: col + kernel.shape[1]]).sum()
    
    return img2

if __name__ == '__main__':
    
    # read image
    fname = 'plant.jpg'
    images = []
    img = plt.imread(fname)
    
    # resize image
    scale = 0.4
    img2 = resize(img, scale)
    print(img.shape, img2.shape)

    # resize image
    scale = 2.5
    img3 = resize(img, scale)
    print(img.shape, img3.shape)

    # crop image
    region = (0, 50, 116, 150)
    img4 = crop(img, region)
    print(img.shape, img4.shape)

    # blur image
    factor = 5
    red = blur(img[:, :, 0], factor)
    green = blur(img[:, :, 1], factor)
    blue = blur(img[:, :, 2], factor)
    img5 = np.stack((red, green, blue), axis=2)
    print(img.shape, img5.shape)

    # convolve image
    kernel = np.array([[-1, -1, -1], [-1, 8, -1], [-1, -1, -1]])
    img6 = convolve(img[:, :, 1], kernel)
    print(img.shape, img6.shape)

    # show images
    fig = plt.figure()
    
    fig.add_subplot(2,3,1)
    plt.imshow(img)
    
    fig.add_subplot(2,3,2)
    plt.imshow(img2/255)

    fig.add_subplot(2,3,3)
    plt.imshow(img3/255)

    fig.add_subplot(2,3,4)
    plt.imshow(img4/255)

    fig.add_subplot(2,3,5)
    plt.imshow(img5/255)

    fig.add_subplot(2,3,6)
    plt.imshow(img6/255, cmap='Greys')

    plt.show()
