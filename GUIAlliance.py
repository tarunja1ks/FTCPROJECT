import sys
import pygame

import time


pygame.init()
res=(720,720)
screen=pygame.display.set_mode(res) #creating the gui
color=(0,0,0)
#color_dark=(50,50,50)
#color_light=(255,255,255)
color_dark=(255,255,255)
color_light=(255,255,255)
width=screen.get_width()
height=screen.get_height()
smallfont = pygame.font.SysFont('Corbel',35)
text = smallfont.render('quit' , True , color)
t=36
white=True
w1=[True]*t
pygame.display.flip()
path=[]
while True:
    w=[0,40]
    h=[0,40]
    #tile locations using pixels
    wi=[[0,120],[120,240],[240,360],[360,480],[480,600],[600,720],[0,120],[120,240],[240,360],[360,480],[480,600],[600,720],[0,120],[120,240],[240,360],[360,480],[480,600],[600,720],[0,120],[120,240],[240,360],[360,480],[480,600],[600,720],[0,120],[120,240],[240,360],[360,480],[480,600],[600,720],[0,120],[120,240],[240,360],[360,480],[480,600],[600,720]]
    hi=[[0,120],[0,120],[0,120],[0,120],[0,120],[0,120],[120,240],[120,240],[120,240],[120,240],[120,240],[120,240],[240,360],[240,360],[240,360],[240,360],[240,360],[240,360],[360,480],[360,480],[360,480],[360,480],[360,480],[360,480],[480,600],[480,600],[480,600],[480,600],[480,600],[480,600],[600,720],[600,720],[600,720],[600,720],[600,720],[600,720]]
    
    ci=[False]*t
  
    for ev in pygame.event.get():
        if ev.type == pygame.QUIT:
            
            pygame.quit()
    
        #checks if a mouse is clicked
        if ev.type == pygame.MOUSEBUTTONDOWN:# checking the clicks
            
            for i in range(t):
                w=wi[i]
                h=hi[i]
                if w[0] <= mouse[0] <= w[1] and h[0] <= mouse[1] <= h[1]:
                    ci[i]=True
                
    #print(click)
    # fills the screen with a color
    screen.fill((255,0,0))# making background color red
    mouse = pygame.mouse.get_pos()
    p=[]
    for i in range(t):#checking the position of the mouse compared to each tile location and checking if it is clicked
     w=wi[i]
     h=hi[i]
     white=w1[i]
     box=pygame.Rect([w[0],h[0],w[1]-w[0],h[1]-h[0]])
     breaker=False
     if w[0] <= mouse[0] <= w[1] and h[0] <= mouse[1] <= h[1] and ci[i]:
        if(not(breaker)):
            p.append(str(int(i/6))+' '+str(i%6))
            breaker=True
            #time.sleep(100)
        pygame.draw.rect(screen,(90,90,30),box,3)
        
        
     else:
        pygame.draw.rect(screen,(255,255,255),box,3)
    if(list(set(p))!=[]):
        path.append(' '.join(list(set(p))))
        
    for i in range(t):
        w=wi[i]
        h=hi[i]
        font = pygame.font.Font('freesansbold.ttf', 32)

        text = font.render(str(int(i/6))+' '+str(i%6), True,(255,255,255),(255,0,0))

        textRect = text.get_rect()
      
        textRect.center = (int((w[0]+w[1])/2),int((h[0]+h[1])/2))
        screen.blit(text,textRect)
    font = pygame.font.Font('freesansbold.ttf', 14)# adding the clicked and joining the tiles that are clicked in one string
    string=""
    for element in path:
        string+=element+", "
    text = font.render(string, True,(255,255,255),(60,60,60))# putting the string in the middle
    file=open('path.txt','w')#writing to the file which bfs code will read
    file.write(','.join(path))
    file.close()
    textRect = text.get_rect()
    
    textRect.center = (int(360),int(360))
    screen.blit(text,textRect)
    
    pygame.display.update()# update gui(add the current changes)


