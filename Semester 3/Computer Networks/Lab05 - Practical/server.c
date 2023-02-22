#include <stdio.h>
#include <netinet/ip.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>

int M[3][3];

void serve_client(int client_fd){
	while(1) {
		char message[101];
		for(int i = 0; i < 101; i++)
			message[i] = '\0';
		read(client_fd, message, 101);
		printf("Client said %s\n", message);
		
		int x = (message[0] - '0');
		int y = (message[1] - '0');
		
		printf("%d %d\n", x, y);
		if (M[x][y] == 1) {
			printf("Client guessed it right!\n");
			write(client_fd, "Right!", 6);
			return;
		}
		else {
			printf("Wrong! Try again!\n");
			write(client_fd, "Wrong!", 6);
		}
	}
}

int main(){
	M[1][2] = 1;
	
	uint16_t port = 1234;
	char address[256] = "172.30.118.45";
	int sock_fd = socket(AF_INET, SOCK_STREAM, 0);
	int backlog = 7;

	if(sock_fd == -1){
		perror("Socket error");
		return -1;
	}
	printf("Socket created successfully\n");
	struct sockaddr_in socket_address;
	socket_address.sin_family = AF_INET;
	socket_address.sin_port = htons(port);
	inet_aton(address, &socket_address.sin_addr);
	socklen_t socket_address_len = sizeof(socket_address);

	int res = bind(sock_fd, (const struct sockaddr*)&socket_address, socket_address_len);
	if(res == -1){
		perror("Bind error");
		return -1;
	}
	printf("Binded to ip %s on port %hu\n", address, port);
	res = listen(sock_fd, backlog);
	if(res == -1){
		perror("Listen error");
		return -1;
	}
	printf("Listen succeeded\n");
	while(1){
		struct sockaddr_in client_address;
		socklen_t client_address_len;
		int client_fd = accept(sock_fd, (struct sockaddr*)&client_address, &client_address_len);

		if(client_fd == -1){
			perror("Accept error");
			continue;
		}

		printf("Client with ip %s and port %d connected successfully\n", inet_ntoa(client_address.sin_addr), ntohs(client_address.sin_port));

		if (fork() == 0) {
			serve_client(client_fd);
			exit(0);
		}
	}
	return 0;
}
