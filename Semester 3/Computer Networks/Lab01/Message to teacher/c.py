import socket
s=socket.socket(socket.AF_INET,socket.SOCK_DGRAM)

s.sendto("Bogdan",("172.30.116.199",5555))     
print s.recvfrom(10)
