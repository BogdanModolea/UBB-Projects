import socket

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server_address = ('172.30.118.45', 1234)
sock.connect(server_address)

while True:
    message = str(input("Enter coords:"))
    b = bytes(message, 'utf-8')
    sock.sendall(b)
    data = sock.recv(101)
    print(data.decode("utf-8"))
    if 'Right!' in str(data.decode("utf-8")):
        break


sock.close()